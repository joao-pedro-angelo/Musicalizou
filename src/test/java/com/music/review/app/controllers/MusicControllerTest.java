package com.music.review.app.controllers;

import com.music.review.app.domain.entities.artists.Artist;
import com.music.review.app.domain.entities.musics.dtos.MusicCreateDTO;
import com.music.review.app.domain.entities.musics.dtos.MusicGetDTO;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class MusicControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<MusicCreateDTO> musicCreateDTOJacksonTester;

    @Autowired
    private JacksonTester<MusicGetDTO> musicGetDTOJacksonTester;

    @Autowired
    private JacksonTester<MusicUpdateDTO> musicUpdateDTOJacksonTester;

    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private ArtistRepository artistRepository;

    private Artist artist;

    @BeforeEach
    @Transactional
    @WithMockUser
    void setUp(){
        // Cria um artista para deixar pronto
        this.artist = new Artist(null, "Nome", "2004", "País", "Bio");
        this.artistRepository.save(artist);
    }

    @Test
    @DisplayName("Deve criar uma música com dados válidos")
    @Transactional
    @WithMockUser
    void createValidMusic() throws Exception {
        // Mock de DTO de criação de música válido
        MusicCreateDTO musicCreateDTO = new MusicCreateDTO("Nome da música", MusicGen.OUTRO, artist.getName());

        // Requisição POST para criar uma música
        var response = this.mockMvc.perform(post("/musics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.musicCreateDTOJacksonTester.write(musicCreateDTO).getJson()))
                .andReturn().getResponse();

        // Certifica que o retorno é 201 Created
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }
}
