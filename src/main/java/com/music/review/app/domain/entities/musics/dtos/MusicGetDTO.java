package com.music.review.app.domain.entities.musics.dtos;

import com.music.review.app.domain.entities.musics.Music;
import com.music.review.app.domain.entities.musics.enums.MusicGen;

public record MusicGetDTO(
        Long id,
        String nameMusic,
        MusicGen musicGen
) {
    public MusicGetDTO(Music music){
        this(music.getId(), music.getNameMusic(), music.getMusicGen());
    }
}
