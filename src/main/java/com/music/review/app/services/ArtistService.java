package com.music.review.app.services;

import com.music.review.app.domain.entities.artists.Artist;
import com.music.review.app.domain.entities.artists.dtos.ArtistCreateDTO;
import com.music.review.app.domain.entities.artists.dtos.ArtistGetDTO;
import com.music.review.app.domain.entities.artists.dtos.ArtistUpdateDTO;
import com.music.review.app.domain.repositories.ArtistRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;

    @Autowired
    public ArtistService(ArtistRepository artistRepository){
        this.artistRepository = artistRepository;
    }

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
        List<Artist> artists = artistRepository.findAll();
        return artists.stream()
                .map(ArtistGetDTO::new)
                .collect(Collectors.toList());
    }

    public ArtistGetDTO updateArtist(ArtistUpdateDTO artistUpdateDTO) {
        Optional<Artist> optionalArtist = artistRepository.findById(artistUpdateDTO.id());
        if (optionalArtist.isPresent()) {
            Artist artist = optionalArtist.get();
            artist.updateArtist(artistUpdateDTO);
            Artist updatedArtist = artistRepository.save(artist);
            return new ArtistGetDTO(updatedArtist);
        } else {
            throw new EntityNotFoundException("Artist not found with id: " + artistUpdateDTO.id());
        }
    }

    public void deleteArtist(Long id) {
        artistRepository.deleteById(id);
    }
}
