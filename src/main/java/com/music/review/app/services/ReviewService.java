package com.music.review.app.services;

import com.music.review.app.domain.entities.musics.Music;
import com.music.review.app.domain.entities.reviews.Review;
import com.music.review.app.domain.entities.reviews.dtos.ReviewCreateDTO;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    public Review createReview(ReviewCreateDTO reviewCreateDTO, Music music){
        Review review = new Review(reviewCreateDTO, music);
        music.addReview(review);
        return review;
    }
}
