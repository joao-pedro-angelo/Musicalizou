package com.music.review.app.domain.entities.artists;

import com.music.review.app.domain.entities.artists.dtos.ArtistCreateDTO;
import com.music.review.app.domain.entities.artists.dtos.ArtistUpdateDTO;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "artist")
@Table(name = "artists")
public class Artist {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "r__year")
    private String year;

    private String country;

    private String bio;

    public Artist(ArtistCreateDTO artistCreateDTO){
        this.name = artistCreateDTO.name();
        this.year = artistCreateDTO.year();
        this.country = artistCreateDTO.country();
        this.bio = artistCreateDTO.bio();
    }

    public void updateArtist(ArtistUpdateDTO artistUpdateDTO){
        if (artistUpdateDTO.bio() != null) this.bio = artistUpdateDTO.bio();
        if (artistUpdateDTO.country() != null) this.country = artistUpdateDTO.country();
        if (artistUpdateDTO.year() != null) this.year = artistUpdateDTO.year();
        if (artistUpdateDTO.name() != null) this.name = artistUpdateDTO.name();
    }
}
