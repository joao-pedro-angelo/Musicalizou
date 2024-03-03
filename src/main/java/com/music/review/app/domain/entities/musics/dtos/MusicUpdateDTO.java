package com.music.review.app.domain.entities.musics.dtos;

import com.music.review.app.domain.entities.musics.enums.MusicGen;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

public record MusicUpdateDTO(
        @NotNull
        Long id,
        String nameMusic,
        @Enumerated(EnumType.STRING)
        MusicGen musicGen
) {
}
