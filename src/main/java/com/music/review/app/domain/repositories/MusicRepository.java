package com.music.review.app.domain.repositories;

import com.music.review.app.domain.entities.musics.Music;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicRepository extends JpaRepository<Music, Long> {
    Music getMusicByNameMusic(String nameMusic);
}
