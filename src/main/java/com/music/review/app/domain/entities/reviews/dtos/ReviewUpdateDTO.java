package com.music.review.app.domain.entities.reviews.dtos;

import com.music.review.app.domain.entities.musics.Music;

public record ReviewUpdateDTO(
        String comment,
        Music music
) {
}
