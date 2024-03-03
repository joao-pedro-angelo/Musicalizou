package com.music.review.app.controllers;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


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
    @DisplayName("Tenta encontrar música não cadastrada")
    void findMusicNotFound() throws Exception{
        var response = this.mockMvc.perform(get("/musics/name/notFound"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}
