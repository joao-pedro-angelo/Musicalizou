package com.music.review.app.controllers;

import com.music.review.app.domain.entities.reviews.Review;
import com.music.review.app.domain.entities.reviews.dtos.ReviewCreateDTO;
import com.music.review.app.domain.entities.reviews.dtos.ReviewGetDTO;
import com.music.review.app.services.MusicService;
import com.music.review.app.services.ReviewService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/reviews")
@SecurityRequirement(name = "bearer-key")
public class ReviewController {

    private final MusicService musicService;
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(MusicService musicService, ReviewService reviewService){
        this.musicService = musicService;
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<ReviewGetDTO> createReview(@RequestBody @Valid ReviewCreateDTO reviewCreateDTO){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.reviewService.createReview(reviewCreateDTO,
                        this.musicService.findByName(reviewCreateDTO.musicName())
                        )
                );
    }

    @GetMapping("/{music}")
    public ResponseEntity<List<Review>> getReviews(@PathVariable String music){
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.reviewService.getReviewByMusicName(
                        this.musicService.findByName(music))
                );
    }
}
