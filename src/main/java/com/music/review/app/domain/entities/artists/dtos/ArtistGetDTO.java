package com.music.review.app.domain.entities.artists.dtos;

import com.music.review.app.domain.entities.artists.Artist;

public record ArtistGetDTO(
        String name,
        String year,
        String country,
        String bio
) {
    public ArtistGetDTO(Artist artist){
        this(artist.getName(), artist.getYear(), artist.getCountry(), artist.getBio());
    }
}
