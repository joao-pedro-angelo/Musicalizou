package com.music.review.app.controllers;

import com.music.review.app.domain.entities.users.User;
import com.music.review.app.domain.entities.users.UserCreateDTO;
import com.music.review.app.domain.entities.users.UserUpdateDTO;
import com.music.review.app.domain.repositories.UserRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<UserCreateDTO> userCreateDTOJacksonTester;

    @Autowired
    private JacksonTester<UserUpdateDTO> userUpdateDTOJacksonTester;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Deve devolver código http 400 - informações inválidas")
    void createUserInvalidData() throws Exception{
        var response = this.mockMvc.perform(post("/users"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @Transactional
    @DisplayName("Deve devolver código http 201 - usuário criado com sucesso")
    void createUserValidData() throws Exception {
        // Crie um DTO de criação de usuário válido
        UserCreateDTO userCreateDTO = new UserCreateDTO("email@test.com", "senha123");

        // Envie uma solicitação POST para /users com o DTO de criação de usuário e verifique a resposta
        var response = this.mockMvc.perform(post("/users")
                        .contentType("application/json")
                        .content(this.userCreateDTOJacksonTester.write(userCreateDTO).getJson()))
                .andReturn().getResponse();

        // Verifique se o código de status HTTP é 201 (Created)
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("Deve devolver código http 404 - usuário não encontrado por email")
    void findUserByEmailNotFound() throws Exception {
        // Envie uma solicitação GET para /users/email/{email} com um e-mail inexistente e verifique a resposta
        var response = this.mockMvc.perform(get("/users/email/{email}", "emailinexistente@test.com"))
                .andReturn().getResponse();

        // Verifique se o código de status HTTP é 404 (Not Found)
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @Transactional
    @DisplayName("Deve devolver código http 204 - usuário deletado com sucesso")
    void deleteUserSuccess() throws Exception {
        // Crie um usuário para deletar
        User user = new User();
        user.setEmail("emaildeletar@test.com");
        user.setPassword("senha123");
        userRepository.save(user);

        // Envie uma solicitação DELETE para /users/{id} com o ID do usuário e verifique a resposta
        var response = this.mockMvc.perform(delete("/users/{id}", user.getId()))
                .andReturn().getResponse();

        // Verifique se o código de status HTTP é 204 (No Content)
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());

        // Verifique se o usuário foi deletado do banco de dados
        assertThat(userRepository.findById(user.getId())).isEmpty();
    }

    @Test
    @DisplayName("Deve devolver código http 404 - usuário não encontrado para deletar")
    void deleteUserNotFound() throws Exception {
        // Envie uma solicitação DELETE para /users/{id} com um ID de usuário inexistente e verifique a resposta
        var response = this.mockMvc.perform(delete("/users/{id}", 100))
                .andReturn().getResponse();

        // Verifique se o código de status HTTP é 204 (No Content)
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @Transactional
    @DisplayName("Deve atualizar os dados de um usuário corretamente")
    void updateUserSuccess() throws Exception {
        // Crie um usuário para atualizar
        User user = new User();
        user.setEmail("emailatualizar@test.com");
        user.setPassword("senha123");
        userRepository.save(user);

        // Crie um DTO de atualização de usuário com novos dados
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO(user.getId(), "novoemail@test.com", "novasenha123");

        // Envie uma solicitação PUT para /users com o DTO de atualização de usuário e verifique a resposta
        var response = this.mockMvc.perform(put("/users")
                        .contentType("application/json")
                        .content(this.userUpdateDTOJacksonTester.write(userUpdateDTO).getJson()))
                .andReturn().getResponse();

        // Verifique se o código de status HTTP é 200 (OK)
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        // Verifique se os dados do usuário foram atualizados corretamente no banco de dados
        User updatedUser = userRepository.findById(user.getId()).orElse(null);
        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getEmail()).isEqualTo(userUpdateDTO.email());
        assertThat(updatedUser.getPassword()).isEqualTo(userUpdateDTO.password());
    }

    @Test
    @Transactional
    @DisplayName("Deve devolver código http 404 - usuário não encontrado para atualizar")
    void updateUserNotFound() throws Exception {
        // Crie um DTO de atualização de usuário com um ID de usuário inexistente
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO(100L, "novoemail@test.com", "novasenha123");

        // Envie uma solicitação PUT para /users com o DTO de atualização de usuário e verifique a resposta
        var response = this.mockMvc.perform(put("/users")
                        .contentType("application/json")
                        .content(this.userUpdateDTOJacksonTester.write(userUpdateDTO).getJson()))
                .andReturn().getResponse();

        // Verifique se o código de status HTTP é 404 (Not Found)
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

}