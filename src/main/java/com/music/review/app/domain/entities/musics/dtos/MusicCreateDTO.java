package com.music.review.app.domain.entities.musics.dtos;

import com.music.review.app.domain.entities.musics.enums.MusicGen;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MusicCreateDTO(
        @NotBlank
        String nameMusic,

        @NotNull
        @Enumerated(EnumType.STRING)
        @Valid
        MusicGen musicGen
) {
}
