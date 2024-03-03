package com.music.review.app.controllers;

import com.music.review.app.domain.entities.musics.Music;
import com.music.review.app.domain.entities.reviews.Review;
import com.music.review.app.domain.entities.reviews.dtos.ReviewCreateDTO;
import com.music.review.app.domain.entities.reviews.dtos.ReviewGetDTO;
import com.music.review.app.services.MusicService;
import com.music.review.app.services.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("reviews")
public class ReviewController {

    private final MusicService musicService;
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(MusicService musicService, ReviewService reviewService){
        this.musicService = musicService;
        this.reviewService = reviewService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ReviewGetDTO> createReview(@RequestBody @Valid ReviewCreateDTO reviewCreateDTO){
        Music music = this.musicService.findByName(reviewCreateDTO.musicName());
        return new ResponseEntity<>(this.reviewService.createReview(reviewCreateDTO, music),
                HttpStatus.CREATED);
    }

    @GetMapping("/get/{music}")
    public ResponseEntity<List<Review>> getReviews(@PathVariable String music){
        Music music1 = this.musicService.findByName(music);
        return new ResponseEntity<>(this.reviewService.getReviewByMusicName(music1),
                HttpStatus.OK);
    }
}
