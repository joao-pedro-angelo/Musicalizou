package com.music.review.app.controllers;

import com.music.review.app.domain.entities.artists.Artist;
import com.music.review.app.domain.entities.artists.dtos.ArtistCreateDTO;
import com.music.review.app.domain.entities.artists.dtos.ArtistUpdateDTO;
import com.music.review.app.domain.repositories.ArtistRepository;
import jakarta.transaction.Transactional;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@WithMockUser
@Transactional
@ActiveProfiles("test")
class ArtistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<ArtistCreateDTO> artistCreateDTOJacksonTester;

    @Autowired
    private JacksonTester<ArtistUpdateDTO> artistUpdateDTOJacksonTester;

    @Autowired
    private ArtistRepository artistRepository;

    @Test
    @DisplayName("Tenta cadastrar artista com informações inválidas - código 400")
    void createArtistWithInvalidData() throws Exception {
        var response = this.mockMvc.perform(post("/v1/artists"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Cadastro de artista com dados válidos - código 201")
    void createArtistWithValidData() throws Exception {
        ArtistCreateDTO artistCreateDTO = new ArtistCreateDTO("Artista Teste", "2020", "Brasil", "Bio do artista");

        var response = this.mockMvc.perform(post("/v1/artists")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.artistCreateDTOJacksonTester.write(artistCreateDTO).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("Deve retornar o artista encontrado pelo ID")
    void findArtistById() throws Exception {
        Artist artist = new Artist();
        artist.setName("Artista Teste");
        artist.setYear("2020");
        artist.setCountry("Brasil");
        artist.setBio("Bio do artista");
        this.artistRepository.save(artist);

        var response = this.mockMvc.perform(get("/v1/artists/id/{id}", artist.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String expectedJsonResponse = "{\"name\":\"Artista Teste\",\"year\":\"2020\",\"country\":\"Brasil\",\"bio\":\"Bio do artista\"}";
        assertThat(response.getResponse().getContentAsString()).isEqualTo(expectedJsonResponse);
    }

    @Test
    @DisplayName("Tenta encontrar artista não cadastrado pelo ID - código 404")
    void findArtistByIdNotFound() throws Exception {
        this.mockMvc.perform(get("/v1/artists/id/1000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar o artista encontrado pelo nome")
    void findArtistByName() throws Exception {
        Artist artist = new Artist();
        artist.setName("Artista Teste");
        artist.setYear("2020");
        artist.setCountry("Brasil");
        artist.setBio("Bio do artista");
        this.artistRepository.save(artist);

        var response = this.mockMvc.perform(get("/v1/artists/name/{name}", artist.getName())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String expectedJsonResponse = "{\"name\":\"Artista Teste\",\"year\":\"2020\",\"country\":\"Brasil\",\"bio\":\"Bio do artista\"}";
        assertThat(response.getResponse().getContentAsString()).isEqualTo(expectedJsonResponse);
    }

    @Test
    @DisplayName("Tenta encontrar artista não cadastrado pelo nome - código 404")
    void findArtistByNameNotFound() throws Exception {
        this.mockMvc.perform(get("/v1/artists/name/ArtistaInexistente")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar todos os artistas cadastrados - código 200")
    void findAllArtists() throws Exception {
        Artist artist1 = new Artist();
        artist1.setName("Artista 1");
        artist1.setYear("2020");
        artist1.setCountry("Brasil");
        artist1.setBio("Bio do artista 1");
        this.artistRepository.save(artist1);

        Artist artist2 = new Artist();
        artist2.setName("Artista 2");
        artist2.setYear("2021");
        artist2.setCountry("EUA");
        artist2.setBio("Bio do artista 2");
        this.artistRepository.save(artist2);

        var response = this.mockMvc.perform(get("/v1/artists")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(response.getResponse().getContentAsString()).contains("Artista 1", "Artista 2");
    }

    @Test
    @DisplayName("Atualização de artista existente - código 200")
    void updateExistingArtist() throws Exception {
        Artist artist = new Artist();
        artist.setName("Artista Original");
        artist.setYear("2020");
        artist.setCountry("Brasil");
        artist.setBio("Bio original do artista");
        this.artistRepository.save(artist);

        ArtistUpdateDTO artistUpdateDTO = new ArtistUpdateDTO(artist.getId(), "Artista Atualizado", "2021", "EUA", "Nova bio do artista");

        var response = this.mockMvc.perform(put("/v1/artists")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.artistUpdateDTOJacksonTester.write(artistUpdateDTO).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Tenta atualizar artista não existente - código 404")
    void updateNonExistingArtist() throws Exception {
        ArtistUpdateDTO artistUpdateDTO = new ArtistUpdateDTO(1000L, "Artista Atualizado", "2021", "EUA", "Nova bio do artista");

        var response = this.mockMvc.perform(put("/v1/artists")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.artistUpdateDTOJacksonTester.write(artistUpdateDTO).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("Exclusão de artista existente - código 204")
    void deleteExistingArtist() throws Exception {
        Artist artist = new Artist();
        artist.setName("Artista para Excluir");
        artist.setYear("2020");
        artist.setCountry("Brasil");
        artist.setBio("Bio do artista para excluir");
        this.artistRepository.save(artist);

        var response = this.mockMvc.perform(delete("/v1/artists/{id}", artist.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("Tenta excluir artista não existente - código 204")
    void deleteNonExistingArtist() throws Exception {
        var response = this.mockMvc.perform(delete("/v1/artists/1000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
