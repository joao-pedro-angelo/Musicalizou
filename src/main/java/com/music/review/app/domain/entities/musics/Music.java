package com.music.review.app.domain.entities.musics;

import com.music.review.app.domain.entities.artists.Artist;
import com.music.review.app.domain.entities.musics.dtos.MusicCreateDTO;
import com.music.review.app.domain.entities.musics.dtos.MusicUpdateDTO;
import com.music.review.app.domain.entities.musics.enums.MusicGen;
import jakarta.persistence.*;
import lombok.*;


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

    @Enumerated(EnumType.STRING)
    @Column(name = "music_gen")
    private MusicGen musicGen;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;

    public Music(MusicCreateDTO musicCreateDTO){
        this.nameMusic = musicCreateDTO.nameMusic();
        this.musicGen = musicCreateDTO.musicGen();
    }

    public void updateMusic(MusicUpdateDTO musicUpdateDTO){
        if (musicUpdateDTO.nameMusic() != null) this.nameMusic = musicUpdateDTO.nameMusic();
        if (musicUpdateDTO.musicGen() != null) this.musicGen = musicUpdateDTO.musicGen();
    }

    public String nameArtist(){
        return this.artist.getName();
    }
}
