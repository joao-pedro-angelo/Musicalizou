package com.music.review.app.controllers;

import com.music.review.app.domain.entities.artists.dtos.ArtistCreateDTO;
import com.music.review.app.domain.repositories.ArtistRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class ArtistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArtistRepository artistRepository;

    @Autowired
    private JacksonTester<ArtistCreateDTO> artistCreateDTOJacksonTester;

    @Test
    @Transactional
    @DisplayName("Deve retornar 400 Bad Request ao criar um artista inválido")
    void createInvalidArtist() throws Exception {
        ArtistCreateDTO artistCreateDTO = new ArtistCreateDTO(null, "1990", "Brasil", "Biografia do artista");

        var response = mockMvc.perform(post("/artists")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(artistCreateDTOJacksonTester.write(artistCreateDTO).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @Transactional
    @DisplayName("Deve retornar 400 Bad Request ao criar um artista com dados inválidos")
    void createValidArtist() throws Exception {
        ArtistCreateDTO artistCreateDTO = new ArtistCreateDTO("oi",
                "2010", "Brasil", "UmaBio");

        var response = mockMvc.perform(post("/artists")
                        .contentType("application/json")
                        .content(artistCreateDTOJacksonTester.write(artistCreateDTO).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @Transactional
    @DisplayName("Deve retornar 404 Not Found ao buscar um artista inexistente")
    void getNonExistentArtist() throws Exception {
        var response = this.mockMvc.perform(get("/artists/name/naoexisto"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}
