package com.music.review.app.controllers;

import com.music.review.app.domain.entities.artists.Artist;
import com.music.review.app.domain.entities.musics.Music;
import com.music.review.app.domain.entities.musics.enums.MusicGen;
import com.music.review.app.domain.entities.reviews.dtos.ReviewCreateDTO;
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
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WithMockUser
@Transactional
@AutoConfigureJsonTesters
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<ReviewCreateDTO> reviewCreateDTOJacksonTester;

    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private ArtistRepository artistRepository;

    private Artist artist;
    private Music music;

    @BeforeEach
    void setUp(){
        Artist artist = new Artist
                (null, "Nome do Artista", "1968", "Brasil", "Bio");
        this.artist = this.artistRepository.save(artist);

        Music music = new Music
                (null, "Nome da musica", MusicGen.OUTRO, this.artist);
        this.music = this.musicRepository.save(music);
    }

    @Test
    @DisplayName("Tenta criar uma review para uma música que não existe - código 404")
    void createReviewFailed() throws Exception {
        ReviewCreateDTO reviewCreateDTO = new ReviewCreateDTO
                ("MusicaInexistente", "Comentário da Review");

        this.mockMvc.perform(post("/v1/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.reviewCreateDTOJacksonTester
                                .write(reviewCreateDTO)
                                .getJson()))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Cria review válida - Código 201 Created")
    void createReviewSucess() throws Exception{
        ReviewCreateDTO reviewCreateDTO = new ReviewCreateDTO
                ("Comment", this.music.getNameMusic());

        this.mockMvc.perform(post("/v1/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.reviewCreateDTOJacksonTester
                                .write(reviewCreateDTO)
                                .getJson()))
                .andExpect(status().isCreated());
    }
}
