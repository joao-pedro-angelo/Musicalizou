package com.music.review.app.controllers;

import com.music.review.app.domain.entities.users.User;
import com.music.review.app.domain.entities.users.dtos.UserCreateDTO;
import com.music.review.app.domain.repositories.UserRepository;
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


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@Transactional
@WithMockUser
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<UserCreateDTO> userCreateDTOJacksonTester;

    @Autowired
    private UserRepository repository;

    private User user;

    @BeforeEach
    void setUp() {
        // Inicializa os dados antes de cada teste
        UserCreateDTO userCreateDTO =
                new UserCreateDTO("oi@gmail.com", "password");
        User user = new User(userCreateDTO);
        this.user = this.repository.save(user);
    }

    @AfterEach
    void tearDown() {
        // Limpa os dados após cada teste
        this.repository.deleteAll();
    }

    @Test
    @DisplayName("Tenta cadastrar usuário com informações inválidas - código 400")
    void userCreateFailed() throws Exception {
        UserCreateDTO dto = new UserCreateDTO(
                null, null);
        var response = this.getResponsePost(dto);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Cadastro de usuário com dados válidos - código 200")
    void userCreateSucess() throws Exception{
        UserCreateDTO userCreateDTO =
                new UserCreateDTO("username@gmail.com", "password");
        var response = this.getResponsePost(userCreateDTO);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("Deve retornar o usuário encontrado pelo ID")
    void findByIdUserSucess() throws Exception {
        var response = this.getResponseGetId(this.user.getId());
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Tenta encontrar usuário não cadastrado - código 404")
    void findByIdUserFailed() throws Exception{
        var response = this.getResponseGetId(123L);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }


    private MockHttpServletResponse getResponsePost(
            UserCreateDTO dto) throws Exception{
        return this.mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.userCreateDTOJacksonTester
                                .write(dto)
                                .getJson())
                )
                .andReturn()
                .getResponse();
    }

    private MockHttpServletResponse getResponseGetId(
            Long id) throws Exception{
        return this.mockMvc.perform(get("/users/id/{idUser}",
                        id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
    }
}