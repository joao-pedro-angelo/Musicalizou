package com.music.review.app.services;

import com.music.review.app.domain.entities.artists.Artist;
import com.music.review.app.domain.entities.artists.dtos.ArtistCreateDTO;
import com.music.review.app.domain.entities.artists.dtos.ArtistGetDTO;
import com.music.review.app.domain.entities.artists.dtos.ArtistUpdateDTO;
import com.music.review.app.domain.repositories.ArtistRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;

    @Autowired
    public ArtistService(ArtistRepository artistRepository){
        this.artistRepository = artistRepository;
    }

    @Transactional
    public ArtistGetDTO createArtist(ArtistCreateDTO artistCreateDTO) {
        Artist artist = new Artist(artistCreateDTO);
        this.artistRepository.save(artist);
        return new ArtistGetDTO(artist);
    }

    public Artist findArtistById(Long id) {
        return this.artistRepository.getReferenceById(id);
    }

    public Artist findArtistByName(String name){
        Artist artist = this.artistRepository.getArtistByName(name);
        if (artist == null) throw new EntityNotFoundException();
        return artist;
    }

    public List<ArtistGetDTO> findAllArtists() {
        List<Artist> artists = this.artistRepository.findAll();
        return artists.stream()
                .map(ArtistGetDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public ArtistGetDTO updateArtist(ArtistUpdateDTO artistUpdateDTO) {
        Artist artist = this.artistRepository.getReferenceById(artistUpdateDTO.id());
        artist.updateArtist(artistUpdateDTO);
        this.artistRepository.save(artist);
        return new ArtistGetDTO(artist);
    }

    @Transactional
    public void deleteArtist(Long id) {
        this.artistRepository.deleteById(id);
    }
}
