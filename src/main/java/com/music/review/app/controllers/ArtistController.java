package com.music.review.app.controllers;

import com.music.review.app.domain.entities.artists.dtos.ArtistCreateDTO;
import com.music.review.app.domain.entities.artists.dtos.ArtistGetDTO;
import com.music.review.app.domain.entities.artists.dtos.ArtistUpdateDTO;
import com.music.review.app.services.ArtistService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("artists")
public class ArtistController {

    private final ArtistService artistService;

    public ArtistController(ArtistService artistService){
        this.artistService = artistService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ArtistGetDTO> create(@RequestBody @Valid ArtistCreateDTO artistCreateDTO){
        ArtistGetDTO createdArtist = artistService.createArtist(artistCreateDTO);
        return new ResponseEntity<>(createdArtist, HttpStatus.CREATED);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ArtistGetDTO> findById(@PathVariable Long id) {
        ArtistGetDTO artist = new ArtistGetDTO(artistService.findArtistById(id));
        return ResponseEntity.ok(artist);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ArtistGetDTO> findByName(@PathVariable String name) {
        ArtistGetDTO artist = new ArtistGetDTO(artistService.findArtistByName(name));
        return ResponseEntity.ok(artist);
    }

    @GetMapping
    public ResponseEntity<List<ArtistGetDTO>> findAll() {
        List<ArtistGetDTO> artists = artistService.findAllArtists();
        return ResponseEntity.ok(artists);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<ArtistGetDTO> update(@RequestBody @Valid ArtistUpdateDTO artistUpdateDTO) {
        ArtistGetDTO updatedArtist = artistService.updateArtist(artistUpdateDTO);
        return ResponseEntity.ok(updatedArtist);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        artistService.deleteArtist(id);
        return ResponseEntity.noContent().build();
    }
}
