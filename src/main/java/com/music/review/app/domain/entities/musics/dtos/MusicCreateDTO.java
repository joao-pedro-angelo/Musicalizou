package com.music.review.app.domain.entities.musics.dtos;

import com.music.review.app.domain.entities.musics.enums.MusicGen;
import jakarta.validation.constraints.NotBlank;

public record MusicCreateDTO(
        @NotBlank
        String nameMusic,

        @NotBlank
        MusicGen musicGen
) {
}
