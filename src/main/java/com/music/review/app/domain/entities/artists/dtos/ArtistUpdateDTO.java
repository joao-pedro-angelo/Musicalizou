package com.music.review.app.domain.entities.artists.dtos;

import jakarta.validation.constraints.NotNull;

public record ArtistUpdateDTO(
        @NotNull
        Long id,

        String name,

        String year,
        String country,
        String bio
) {
}
