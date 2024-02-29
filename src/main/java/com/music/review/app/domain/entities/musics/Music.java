package com.music.review.app.domain.entities.musics;

import com.music.review.app.domain.entities.musics.dtos.MusicCreateDTO;
import com.music.review.app.domain.entities.musics.dtos.MusicUpdateDTO;
import com.music.review.app.domain.entities.musics.enums.MusicGen;
import com.music.review.app.domain.entities.reviews.Review;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@Entity(name = "music")
@Table(name = "musics")
public class Music {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_music")
    private String nameMusic;

    @OneToMany(mappedBy = "music")
    private List<Review> reviews = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "music_gen")
    private MusicGen musicGen;

    public Music(MusicCreateDTO musicCreateDTO){
        this.nameMusic = musicCreateDTO.nameMusic();
        this.musicGen = musicCreateDTO.musicGen();
    }

    public void addReview(Review review){
        this.reviews.add(review);
    }

    public void updateMusic(MusicUpdateDTO musicUpdateDTO){
        if (musicUpdateDTO.nameMusic() != null) this.nameMusic = musicUpdateDTO.nameMusic();
        if (musicUpdateDTO.musicGen() != null) this.musicGen = musicUpdateDTO.musicGen();
    }

}
