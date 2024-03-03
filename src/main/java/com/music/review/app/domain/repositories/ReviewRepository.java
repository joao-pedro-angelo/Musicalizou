package com.music.review.app.domain.repositories;

import com.music.review.app.domain.entities.musics.Music;
import com.music.review.app.domain.entities.reviews.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByMusic(Music music);
}
