package com.music.review.app.services;

import com.music.review.app.domain.entities.musics.Music;
import com.music.review.app.domain.entities.musics.dtos.MusicCreateDTO;
import com.music.review.app.domain.entities.musics.dtos.MusicGetDTO;
import com.music.review.app.domain.entities.musics.dtos.MusicUpdateDTO;
import com.music.review.app.domain.entities.reviews.Review;
import com.music.review.app.domain.entities.reviews.dtos.ReviewCreateDTO;
import com.music.review.app.domain.entities.reviews.dtos.ReviewGetDTO;
import com.music.review.app.domain.repositories.MusicRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MusicService {

    private final MusicRepository musicRepository;

    @Autowired
    public MusicService(MusicRepository musicRepository){
        this.musicRepository = musicRepository;
    }

    public MusicGetDTO saveMusic(MusicCreateDTO musicCreateDTO){
        Music music = new Music(musicCreateDTO);
        this.musicRepository.save(music);
        return new MusicGetDTO(music);
    }

    public MusicGetDTO findById(Long id){
        Optional<Music> optionalMusic = this.musicRepository.findById(id);
        if (optionalMusic.isPresent()){
            Music music = optionalMusic.get();
            return new MusicGetDTO(music);
        } else {
            throw new EntityNotFoundException("Não há música com o ID: " + id);
        }
    }

    public MusicGetDTO findByName(String nameMusic){
        Optional<Music> optionalMusic = this.musicRepository.findByNameMusic(nameMusic);
        if (optionalMusic.isPresent()){
            Music music = optionalMusic.get();
            return new MusicGetDTO(music);
        } else{
            throw new EntityNotFoundException("Não há música com este nome: " + nameMusic);
        }
    }

    public List<MusicGetDTO> findAll(){
        List<Music> musics = this.musicRepository.findAll();
        return musics.stream()
                .map(MusicGetDTO::new)
                .collect(Collectors.toList());
    }

    public MusicGetDTO update(MusicUpdateDTO musicUpdateDTO){
        Optional<Music> music = this.musicRepository.findById(musicUpdateDTO.id());
        if (music.isPresent()){
            Music musicPresent = music.get();
            musicPresent.updateMusic(musicUpdateDTO);
            return new MusicGetDTO(musicPresent);
        } else {
            throw new EntityNotFoundException("Não há música com o ID: " + musicUpdateDTO.id());
        }
    }

    public void deleteMusic(Long id){
        this.musicRepository.deleteById(id);
    }

    public void addReview(Review review){
        Music music = review.getMusic();
        music.addReview(review);
    }

    public List<ReviewGetDTO> findReviewsByMusicName(String nameMusic){
        Optional<Music> music = this.musicRepository.findByNameMusic(nameMusic);
        if (music.isPresent()){
            Music musicPresent = music.get();
            List<Review> reviews = musicPresent.getReviews();
            return reviews.stream()
                    .map(ReviewGetDTO::new)
                    .collect(Collectors.toList());
        } else{
            throw new EntityNotFoundException("Não há música com o nome: " + nameMusic);
        }
    }
}
