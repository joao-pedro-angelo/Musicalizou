package com.music.review.app.controllers;

import com.music.review.app.domain.entities.reviews.dtos.ReviewCreateDTO;
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

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<ReviewCreateDTO> reviewCreateDTOJacksonTester;

    @Test
    @DisplayName("Deve devolver código 400 - Informações Inválidas")
    void createReviewInvalidData() throws Exception{
        var response = this.mockMvc.perform(post("/reviews"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @Transactional
    @DisplayName("Criação válida de review")
    void createReviewValidData() throws Exception{
        ReviewCreateDTO reviewCreateDTO = new ReviewCreateDTO("Comment", "Canudinho");

        var response = this.mockMvc.perform(post("/reviews")
                .contentType("application/json")
                .content(this.reviewCreateDTOJacksonTester.write(reviewCreateDTO).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }


}
