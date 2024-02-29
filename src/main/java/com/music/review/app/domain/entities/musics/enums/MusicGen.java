package com.music.review.app.domain.entities.musics.enums;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum MusicGen {
    @Enumerated(EnumType.STRING)
    SERTANEJO,
    @Enumerated(EnumType.STRING)
    POP,
    @Enumerated(EnumType.STRING)
    MPB,
    @Enumerated(EnumType.STRING)
    ROCK,
    @Enumerated(EnumType.STRING)
    OUTRO
}
