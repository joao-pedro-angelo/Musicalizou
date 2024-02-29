package com.music.review.app.domain.entities.reviews.dtos;

import com.music.review.app.domain.entities.musics.Music;
import com.music.review.app.domain.entities.reviews.Review;

public record ReviewGetDTO(
        String comment,
        Music music
) {
    public ReviewGetDTO(Review review){
        this(review.getComment(), review.getMusic());
    }
}
