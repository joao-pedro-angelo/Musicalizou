package com.music.review.app.domain.entities.reviews;

import com.music.review.app.domain.entities.musics.Music;
import com.music.review.app.domain.entities.reviews.dtos.ReviewCreateDTO;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "review")
@Table(name = "reviews")
public class Review {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "music_id")
    private Music music;

    public Review(ReviewCreateDTO reviewCreateDTO, Music music){
        this.comment = reviewCreateDTO.comment();
        this.music = music;
    }
}
