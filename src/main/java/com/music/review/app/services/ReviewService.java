package com.music.review.app.services;

import com.music.review.app.domain.entities.musics.Music;
import com.music.review.app.domain.entities.reviews.Review;
import com.music.review.app.domain.entities.reviews.dtos.ReviewCreateDTO;
import com.music.review.app.domain.entities.reviews.dtos.ReviewGetDTO;
import com.music.review.app.domain.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository repository;

    @Autowired
    public ReviewService(ReviewRepository repository){
        this.repository = repository;
    }

    @Transactional
    public ReviewGetDTO createReview(ReviewCreateDTO reviewCreateDTO, Music music){
        Review review = new Review(reviewCreateDTO.comment(), music);
        this.repository.save(review);
        return new ReviewGetDTO(review.getComment(), music.getNameMusic());
    }

    public List<Review> getReviewByMusicName(Music music){
        return this.repository.findByMusic(music);
    }
}
