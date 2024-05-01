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
import org.springframework.mock.web.MockHttpServletResponse;
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

    @BeforeEach
    void setUp() {
        Artist artist = new Artist();
        artist.setName("Artista Teste");
        artist.setYear("2020");
        artist.setCountry("Brasil");
        artist.setBio("Bio do artista");
        this.artist = this.artistRepository.save(artist);
    }

    @AfterEach
    void tearDown() {
        this.artistRepository.deleteAll();
        System.out.println("Todos os artistas foram removidos do banco de dados.");
    }

    @Test
    @DisplayName("Tenta cadastrar artista com informações inválidas - código 400")
    void createArtistFailed() throws Exception {
        ArtistCreateDTO dto = new ArtistCreateDTO
                (null , null,
                        null, null);
        var response = this.getResponsePost(dto);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Cadastro de artista com dados válidos - código 201")
    void createArtistSucess() throws Exception {
        ArtistCreateDTO dto = new ArtistCreateDTO
                ("Artista Random", "2020",
                        "Brasil", "Bio do artista");
        var response = this.getResponsePost(dto);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("Deve retornar o artista encontrado pelo ID")
    void findArtistByIdSucess() throws Exception {
        var response = this.getResponseGetId(this.artist.getId());
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Tenta encontrar artista não cadastrado pelo ID - código 404")
    void findArtistByIdFailed() throws Exception {
        var response = this.getResponseGetId(90812L);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("Deve retornar o artista encontrado pelo nome")
    void findArtistByNameSucess() throws Exception {
        var response = this.getResponseGetName(this.artist.getName());
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Tenta encontrar artista não cadastrado pelo nome - código 404")
    void findArtistByNameFailed() throws Exception {
        var response = this.getResponseGetName("Not");
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
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
        ArtistUpdateDTO dto = new ArtistUpdateDTO(this.artist.getId(),
                "Artista Atualizado", "2021", "EUA", "Nova bio do artista");
        var response = this.getResponsePut(dto);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Tenta atualizar artista não existente - código 404")
    void updateArtistFailed() throws Exception {
        ArtistUpdateDTO dto = new ArtistUpdateDTO(1000L,
                "Artista Atualizado", "2021", "EUA", "Nova bio do artista");
        var response = this.getResponsePut(dto);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("Exclusão de artista existente - código 204")
    void deleteArtistSucess() throws Exception {
        var response = this.getResponseDelete(this.artist.getId());
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("Tenta excluir artista não existente - código 204")
    void deleteArtistFailed() throws Exception {
        var response = this.getResponseDelete(1000L);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }


    private MockHttpServletResponse getResponseDelete(Long id) throws Exception{
        return this.mockMvc.perform(delete("/v1/artists/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
    }

    private MockHttpServletResponse getResponsePut(
            ArtistUpdateDTO dto) throws Exception{
        return this.mockMvc.perform(put("/v1/artists")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.artistUpdateDTOJacksonTester
                                .write(dto)
                                .getJson()))
                .andReturn().getResponse();
    }

    private MockHttpServletResponse getResponsePost(
            ArtistCreateDTO dto) throws Exception{
        return this.mockMvc.perform(post("/v1/artists")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.artistCreateDTOJacksonTester
                                .write(dto)
                                .getJson()))
                .andReturn()
                .getResponse();
    }

    private MockHttpServletResponse getResponseGetId(
            Long id) throws Exception{
        return this.mockMvc
                .perform(get("/v1/artists/id/{id}",
                        id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
    }

    private MockHttpServletResponse getResponseGetName(
            String name) throws Exception{
        return this.mockMvc
                .perform(get("/v1/artists/name/{name}",
                        name)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
    }
}
