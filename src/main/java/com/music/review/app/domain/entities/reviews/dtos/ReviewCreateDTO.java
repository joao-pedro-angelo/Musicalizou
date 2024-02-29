package com.music.review.app.domain.entities.reviews.dtos;

import jakarta.validation.constraints.NotBlank;

public record ReviewCreateDTO(
        @NotBlank
        String comment,
        @NotBlank
        String musicName
) {
}
