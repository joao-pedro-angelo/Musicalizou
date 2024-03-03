package com.music.review.app.controllers;

import com.music.review.app.domain.entities.musics.Music;
import com.music.review.app.domain.entities.musics.dtos.MusicCreateDTO;
import com.music.review.app.domain.entities.musics.dtos.MusicUpdateDTO;
import com.music.review.app.domain.entities.musics.enums.MusicGen;
import com.music.review.app.domain.repositories.MusicRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class MusicControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<MusicCreateDTO> musicCreateDTOJacksonTester;

    @Autowired
    private JacksonTester<MusicUpdateDTO> musicUpdateDTOJacksonTester;

    @Autowired
    private MusicRepository musicRepository;

    @Test
    @DisplayName("Deve devolver código 400 - Informações Inválidas")
    void createMusicInvalidData() throws Exception{
        var response = this.mockMvc.perform(post("/musics"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @Transactional
    @DisplayName("Deve devolver código 201 - Created")
    void createMusicValidData() throws Exception{
        MusicCreateDTO musicCreateDTO = new MusicCreateDTO("Balanço da Rede",
                MusicGen.SERTANEJO);

        var response = this.mockMvc.perform(post("/musics")
                .contentType("application/json")
                .content(this.musicCreateDTOJacksonTester.write(musicCreateDTO).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @Transactional
    @DisplayName("Tenta encontrar música não cadastrada - deve devolver código 404")
    void findMusicNotFound() throws Exception{
        var response = this.mockMvc.perform(get("/musics/name/notFound"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @Transactional
    @DisplayName("Tenta deletar música não cadastrada - deve devolver código 204 - No Content")
    void deleteMusicNotFound() throws Exception{
        var response = this.mockMvc.perform(delete("/musics/delete/2012"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @Transactional
    @DisplayName("Delete com sucesso - Código 204 - No Content")
    void deleteMusic() throws Exception{
        Music music = new Music();
        music.setNameMusic("Balanço da rede");
        music.setMusicGen(MusicGen.SERTANEJO);
        this.musicRepository.save(music);

        var response = this.mockMvc.perform(delete("/musics/delete/{id}", music.getId()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
        assertThat(this.musicRepository.findById(music.getId())).isEmpty();
    }

    @Test
    @Transactional
    @DisplayName("Deve devolver código http 404 - música não encontrada para atualizar")
    void updateMusicNotFound() throws Exception {
        // Crie um DTO de atualização de usuário com um ID de usuário inexistente
        MusicUpdateDTO musicUpdateDTO = new MusicUpdateDTO(100L, "novoemail@test.com", MusicGen.SERTANEJO);

        // Envie uma solicitação PUT para /users com o DTO de atualização de usuário e verifique a resposta
        var response = this.mockMvc.perform(put("/users")
                        .contentType("application/json")
                        .content(this.musicUpdateDTOJacksonTester.write(musicUpdateDTO).getJson()))
                        .andReturn().getResponse();

        // Verifique se o código de status HTTP é 404 (Not Found)
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @Transactional
    @DisplayName("Update bem sucedido")
    void updateMusic() throws Exception{
        Music music = new Music();
        music.setNameMusic("Existo");
        music.setMusicGen(MusicGen.OUTRO);
        this.musicRepository.save(music);

        MusicUpdateDTO musicUpdateDTO = new MusicUpdateDTO(music.getId(), "NaoExisto", MusicGen.SERTANEJO);

        var response = this.mockMvc.perform(put("/musics")
                        .contentType("application/json")
                        .content(this.musicUpdateDTOJacksonTester.write(musicUpdateDTO).getJson()))
                        .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        Music musicUpdate = this.musicRepository.findById(music.getId()).orElse(null);
        assertThat(musicUpdate).isNotNull();
        assertThat(musicUpdate.getNameMusic()).isEqualTo(musicUpdateDTO.nameMusic());
        assertThat(musicUpdate.getMusicGen()).isEqualTo(musicUpdateDTO.musicGen());
    }
}
