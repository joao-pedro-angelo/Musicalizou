package com.music.review.app.services;

import com.music.review.app.domain.entities.artists.Artist;
import com.music.review.app.domain.entities.musics.Music;
import com.music.review.app.domain.entities.musics.dtos.MusicCreateDTO;
import com.music.review.app.domain.entities.musics.dtos.MusicGetDTO;
import com.music.review.app.domain.entities.musics.dtos.MusicUpdateDTO;
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

    private final ArtistService artistService;

    @Autowired
    public MusicService(MusicRepository musicRepository, ArtistService artistService){
        this.musicRepository = musicRepository;
        this.artistService = artistService;
    }

    public MusicGetDTO saveMusic(MusicCreateDTO musicCreateDTO){
        Artist artist = this.artistService.findArtistByName(musicCreateDTO.artist());
        Music music = new Music(musicCreateDTO, artist);
        this.musicRepository.save(music);
        return new MusicGetDTO(music);
    }

    public Music findById(Long id){
        return this.musicRepository.getReferenceById(id);
    }

    public Music findByName(String nameMusic){
        Music music = this.musicRepository.getMusicByNameMusic(nameMusic);
        if (music == null) throw new EntityNotFoundException();
        return music;
    }

    public List<MusicGetDTO> findAll(){
        List<Music> musics = this.musicRepository.findAll();
        return musics.stream()
                .map(MusicGetDTO::new)
                .collect(Collectors.toList());
    }

    public MusicGetDTO update(MusicUpdateDTO musicUpdateDTO){
        Music music = this.musicRepository.getReferenceById(musicUpdateDTO.id());
        music.updateMusic(musicUpdateDTO);
        return new MusicGetDTO(music);
    }

    public void deleteMusic(Long id){
        this.musicRepository.deleteById(id);
    }
}
