package com.music.review.app.domain.entities.musics.dtos;

import com.music.review.app.domain.entities.musics.enums.MusicGen;
import jakarta.validation.constraints.NotNull;

public record MusicUpdateDTO(
        @NotNull
        Long id,
        String nameMusic,
        MusicGen musicGen
) {
}
