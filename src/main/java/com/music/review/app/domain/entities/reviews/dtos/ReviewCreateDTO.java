package com.music.review.app.domain.entities.reviews.dtos;

import com.music.review.app.domain.entities.musics.Music;
import jakarta.validation.constraints.NotBlank;

public record ReviewCreateDTO(
        @NotBlank
        String comment,
        @NotBlank
        Music music
) {
}
