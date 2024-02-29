package com.music.review.app.domain.repositories;

import com.music.review.app.domain.entities.musics.Music;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MusicRepository extends JpaRepository<Music, Long> {
    Optional<Music> findByNameMusic(String nameMusic);
}
