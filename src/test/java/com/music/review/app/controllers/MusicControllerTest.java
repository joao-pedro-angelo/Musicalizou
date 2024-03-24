package com.music.review.app.controllers;

import com.music.review.app.domain.entities.artists.Artist;
import com.music.review.app.domain.entities.musics.Music;
import com.music.review.app.domain.entities.musics.dtos.MusicCreateDTO;
import com.music.review.app.domain.entities.musics.dtos.MusicUpdateDTO;
import com.music.review.app.domain.entities.musics.enums.MusicGen;
import com.music.review.app.domain.repositories.ArtistRepository;
import com.music.review.app.domain.repositories.MusicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@WithMockUser
@Transactional
@ActiveProfiles("test")
class MusicControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<MusicCreateDTO> musicCreateDTOJacksonTester;

    @Autowired
    private JacksonTester<MusicUpdateDTO> musicUpdateDTOJacksonTester;

    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private ArtistRepository artistRepository;

    private Artist artist;
    private Music music;
    private String jsonExpected;

    @BeforeEach
    void setUp(){
        // Cria um artista para deixar pronto
        Artist artist = new Artist(null, "Nome", "2004", "País", "Bio");
        this.artist = this.artistRepository.save(artist);

        // Cria uma música
        Music music = new Music();
        music.setNameMusic("Nome da Musica");
        music.setMusicGen(MusicGen.ROCK);
        music.setArtist(this.artist);
        this.music = this.musicRepository.save(music);

        this.jsonExpected = "{\"id\":" + music.getId() +
                ",\"nameMusic\":\"Nome da Musica\",\"musicGen\":\"ROCK\",\"artist\":\"Nome\"}";
    }

    @Test
    @DisplayName("Deve criar uma música com dados válidos")
    void createMusicSucess() throws Exception {
        // Mock de DTO de criação de música válido
        MusicCreateDTO musicCreateDTO = new MusicCreateDTO
                ("Nome da música", MusicGen.OUTRO, this.artist.getName());

        // Requisição POST para criar uma música
        var response = this.mockMvc.perform(post("/v1/musics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.musicCreateDTOJacksonTester
                                .write(musicCreateDTO)
                                .getJson()))
                .andReturn().getResponse();

        // Certifica que o retorno é 201 Created
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("Criar música inválida - código 400 Bad Request")
    void createMusicFailed() throws Exception{
        var response = this.mockMvc.perform(post("/v1/musics"))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Tenta encontrar música com ID não salvo - código 404")
    void findMusicByIdFailed() throws Exception{
        this.mockMvc.perform(get("/v1/musics/id/800")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar a música encontrada pelo ID")
    void findMusicByIdSucess() throws Exception {
        var response = this.mockMvc.perform(get("/v1/musics/id/{id}",
                        this.music.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(response.getResponse().getContentAsString()).isEqualTo(this.jsonExpected);
    }

    @Test
    @DisplayName("Deve retornar a música encontrada pelo nome")
    void findMusicByNameSucess() throws Exception {
        var response = this.mockMvc.perform(get("/v1/musics/name/{name}",
                        this.music.getNameMusic())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(response.getResponse().getContentAsString()).isEqualTo(this.jsonExpected);
    }

    @Test
    @DisplayName("Tenta encontrar música não cadastrada pelo nome - código 404")
    void findMusicByNameFailed() throws Exception {
        this.mockMvc.perform(get("/v1/musics/name/MusicaInexistente")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar todas as músicas cadastradas - código 200")
    void findAllMusicsUniqueCase() throws Exception {
        var response = this.mockMvc.perform(get("/v1/musics")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(response.getResponse().getContentAsString()).contains("Nome da Musica");
    }

    @Test
    @DisplayName("Atualização de música existente - código 200")
    void updateMusicSucess() throws Exception {
        MusicUpdateDTO musicUpdateDTO = new MusicUpdateDTO(this.music.getId(),
                "Nome Atualizado", MusicGen.POP);

        var response = this.mockMvc.perform(put("/v1/musics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.musicUpdateDTOJacksonTester
                                .write(musicUpdateDTO)
                                .getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Tenta atualizar música não existente - código 404")
    void updateMusicFailed() throws Exception {
        MusicUpdateDTO musicUpdateDTO = new MusicUpdateDTO(1000L,
                "Nome Atualizado", MusicGen.POP);

        var response = this.mockMvc.perform(put("/v1/musics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.musicUpdateDTOJacksonTester
                                .write(musicUpdateDTO)
                                .getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("Exclusão de música existente - código 204")
    void deleteMusicSucess() throws Exception {
        var response = this.mockMvc.perform(delete("/v1/musics/{id}",
                        this.music.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("Tenta excluir música não existente - código 204")
    void deleteMusicFailed() throws Exception {
        var response = this.mockMvc.perform(delete("/v1/musics/1000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
