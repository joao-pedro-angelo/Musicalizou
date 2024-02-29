package com.music.review.app.domain.entities.musics.dtos;

import com.music.review.app.domain.entities.musics.enums.MusicGen;

public record MusicUpdateDTO(
        String nameMusic,
        MusicGen musicGen
) {
}
