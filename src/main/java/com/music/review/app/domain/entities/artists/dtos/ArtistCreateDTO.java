package com.music.review.app.domain.entities.artists.dtos;

import jakarta.validation.constraints.NotBlank;

public record ArtistCreateDTO(
        @NotBlank
        String name,
        String year,
        String country,
        String bio
) {
}
