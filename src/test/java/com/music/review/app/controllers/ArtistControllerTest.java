package com.music.review.app.controllers;

import com.music.review.app.domain.entities.artists.Artist;
import com.music.review.app.domain.entities.artists.dtos.ArtistCreateDTO;
import com.music.review.app.domain.entities.artists.dtos.ArtistUpdateDTO;
import com.music.review.app.domain.repositories.ArtistRepository;
import org.junit.jupiter.api.AfterEach;
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
@ActiveProfiles("test")
@Transactional
class ArtistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<ArtistCreateDTO> artistCreateDTOJacksonTester;

    @Autowired
    private JacksonTester<ArtistUpdateDTO> artistUpdateDTOJacksonTester;

    @Autowired
    private ArtistRepository artistRepository;

    private Artist artist;
    private String jsonExpected;

    @BeforeEach
    void setUp() {
        Artist artist = new Artist();
        artist.setName("Artista Teste");
        artist.setYear("2020");
        artist.setCountry("Brasil");
        artist.setBio("Bio do artista");
        this.artist = this.artistRepository.save(artist);

        this.jsonExpected =
                "{\"name\":\"Artista Teste\",\"year\":\"2020\"," +
                        "\"country\":\"Brasil\",\"bio\":\"Bio do artista\"}";
    }

    @AfterEach
    void tearDown() {
        this.artistRepository.deleteAll();
        System.out.println("Todos os artistas foram removidos do banco de dados.");
    }

    @Test
    @DisplayName("Tenta cadastrar artista com informações inválidas - código 400")
    void createArtistFailed() throws Exception {
        var response = this.mockMvc.perform(post("/v1/artists"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Cadastro de artista com dados válidos - código 201")
    void createArtistSucess() throws Exception {
        ArtistCreateDTO artistCreateDTO = new ArtistCreateDTO
                ("Artista Random", "2020", "Brasil", "Bio do artista");

        var response = this.mockMvc.perform(post("/v1/artists")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.artistCreateDTOJacksonTester
                                .write(artistCreateDTO)
                                .getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("Deve retornar o artista encontrado pelo ID")
    void findArtistByIdSucess() throws Exception {
        var response = this.mockMvc.perform(get("/v1/artists/id/{id}",
                        this.artist.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(response.getResponse().getContentAsString())
                .isEqualTo(this.jsonExpected);
    }

    @Test
    @DisplayName("Tenta encontrar artista não cadastrado pelo ID - código 404")
    void findArtistByIdFailed() throws Exception {
        this.mockMvc.perform(get("/v1/artists/id/1000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar o artista encontrado pelo nome")
    void findArtistByNameSucess() throws Exception {
        var response = this.mockMvc.perform(get("/v1/artists/name/{name}",
                        this.artist.getName())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(response.getResponse().getContentAsString()).isEqualTo(this.jsonExpected);
    }

    @Test
    @DisplayName("Tenta encontrar artista não cadastrado pelo nome - código 404")
    void findArtistByNameFailed() throws Exception {
        this.mockMvc.perform(get("/v1/artists/name/ArtistaInexistente")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar todos os artistas cadastrados - código 200")
    void findAllArtistsUniqueCase() throws Exception {
        var response = this.mockMvc.perform(get("/v1/artists")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(response.getResponse().getContentAsString()).contains("Artista Teste");
    }

    @Test
    @DisplayName("Atualização de artista existente - código 200")
    void updateArtistSucess() throws Exception {
        ArtistUpdateDTO artistUpdateDTO = new ArtistUpdateDTO(this.artist.getId(),
                "Artista Atualizado", "2021", "EUA", "Nova bio do artista");

        var response = this.mockMvc.perform(put("/v1/artists")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.artistUpdateDTOJacksonTester
                                .write(artistUpdateDTO)
                                .getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Tenta atualizar artista não existente - código 404")
    void updateArtistFailed() throws Exception {
        ArtistUpdateDTO artistUpdateDTO = new ArtistUpdateDTO(1000L,
                "Artista Atualizado", "2021", "EUA", "Nova bio do artista");

        var response = this.mockMvc.perform(put("/v1/artists")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.artistUpdateDTOJacksonTester
                                .write(artistUpdateDTO)
                                .getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("Exclusão de artista existente - código 204")
    void deleteArtistSucess() throws Exception {
        var response = this.mockMvc.perform(delete("/v1/artists/{id}",
                        this.artist.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("Tenta excluir artista não existente - código 204")
    void deleteArtistFailed() throws Exception {
        var response = this.mockMvc.perform(delete("/v1/artists/1000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
