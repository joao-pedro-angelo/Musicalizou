package com.music.review.app.controllers;

import com.music.review.app.domain.entities.musics.Music;
import com.music.review.app.domain.entities.reviews.Review;
import com.music.review.app.domain.entities.reviews.dtos.ReviewCreateDTO;
import com.music.review.app.domain.entities.reviews.dtos.ReviewGetDTO;
import com.music.review.app.services.MusicService;
import com.music.review.app.services.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("reviews")
public class ReviewController {

    private MusicService musicService;
    private ReviewService reviewService;

    @Autowired
    public ReviewController(MusicService musicService, ReviewService reviewService){
        this.musicService = musicService;
        this.reviewService = reviewService;
    }

    @PostMapping
    @Transactional
    public ReviewGetDTO createReview(@RequestBody @Valid ReviewCreateDTO reviewCreateDTO){
        Music music = this.musicService.findByName(reviewCreateDTO.musicName());
        return this.reviewService.createReview(reviewCreateDTO, music);
    }

    @GetMapping("/get/{music}")
    public List<Review> getReviews(@PathVariable String music){
        Music music1 = this.musicService.findByName(music);
        return this.reviewService.getReviewByMusicName(music1);
    }
}
