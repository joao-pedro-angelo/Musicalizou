package com.music.review.app.domain.repositories;

import com.music.review.app.domain.entities.artists.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Artist getArtistByName(String name);
}
