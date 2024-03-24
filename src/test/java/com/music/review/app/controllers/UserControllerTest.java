package com.music.review.app.controllers;

import com.music.review.app.domain.entities.users.User;
import com.music.review.app.domain.entities.users.dtos.UserCreateDTO;
import com.music.review.app.domain.entities.users.dtos.UserUpdateDTO;
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
@Transactional
@WithMockUser
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<UserCreateDTO> userCreateDTOJacksonTester;

    @Autowired
    private JacksonTester<UserUpdateDTO> userUpdateDTOJacksonTester;

    @Autowired
    private UserRepository repository;

    private User user;
    private String jsonExpected;

    @BeforeEach
    void setUp() {
        // Inicializa os dados antes de cada teste
        UserCreateDTO userCreateDTO =
                new UserCreateDTO("oi@gmail.com", "password");
        User user = new User(userCreateDTO);
        this.user = this.repository.save(user);

        this.jsonExpected = "{\"id\":"
                + this.user.getId()
                + ",\"email\":\"oi@gmail.com\"}";
    }

    @AfterEach
    void tearDown() {
        // Limpa os dados após cada teste
        this.repository.deleteAll();
    }

    @Test
    @DisplayName("Tenta cadastrar usuário com informações inválidas - código 400")
    void userCreateFailed() throws Exception {
        var response = this.mockMvc.perform(post("/users"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Cadastro de usuário com dados válidos - código 200")
    void userCreateSucess() throws Exception{
        // Cria um DTO de criação de usuário com dados válidos
        UserCreateDTO userCreateDTO =
                new UserCreateDTO("username@gmail.com", "password");

        // Envia uma solicitação POST para /users com o DTO de criação de usuário
        // e verifica a resposta
        var response = this.mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.userCreateDTOJacksonTester
                                .write(userCreateDTO)
                                .getJson())
                )
                .andReturn().getResponse();

        // Verifica se o código de status HTTP é 200 (OK)
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("Deve retornar o usuário encontrado pelo ID")
    void findByIdUserSucess() throws Exception {
        // Faz uma solicitação GET para /users/id/{id} e verifica se a resposta tem status 200 (OK)
        var response = this.mockMvc.perform(get("/users/id/{idUser}",
                        this.user.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Verifica se o conteúdo da resposta corresponde ao JSON esperado
        assertThat(response.getResponse().getContentAsString())
                .isEqualTo(this.jsonExpected);
    }

    @Test
    @DisplayName("Tenta encontrar usuário não cadastrado - código 404")
    void findByIdUserFailed() throws Exception{
        this.mockMvc.perform(get("/users/id/800")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar status 404 - Not Found ao buscar um usuário por email não existente")
    void findByEmailFailed() throws Exception {
        // Faz uma solicitação GET para /users/email/{email}
        // e verifica se a resposta tem status 404 (Not Found)
        mockMvc.perform(get("/users/email/emailnaoexistente@test.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar status 204 - No Content ao tentar excluir um usuário não existente")
    void deleteUserFailed() throws Exception {
        // Faz uma solicitação DELETE para /users/delete/1
        // e verifica se a resposta tem status 200 (OK)
        this.mockMvc.perform(delete("/users/900")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve retornar status 404 - Not Found ao tentar atualizar um usuário não existente")
    void updateUserFailed() throws Exception {
        // Faz uma solicitação PUT para /users e verifica se a resposta tem status 404 (Not Found)
        this.mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": 700, \"email\": \"novoemail@test.com\" }"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar status 200 - OK ao atualizar um usuário existente")
    void updateUserSucess() throws Exception {
        // Cria usuário para atualizar email
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO(this.user.getId(),
                "novoemail@gmail.com", null);

        // Requisição
        var response = this.mockMvc.perform(put("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.userUpdateDTOJacksonTester
                        .write(userUpdateDTO)
                        .getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
}