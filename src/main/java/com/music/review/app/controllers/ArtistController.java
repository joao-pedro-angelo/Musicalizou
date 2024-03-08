package com.music.review.app.controllers;

import com.music.review.app.domain.entities.artists.dtos.ArtistCreateDTO;
import com.music.review.app.domain.entities.artists.dtos.ArtistGetDTO;
import com.music.review.app.domain.entities.artists.dtos.ArtistUpdateDTO;
import com.music.review.app.services.ArtistService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("artists")
@SecurityRequirement(name = "bearer-key")
public class ArtistController {

    private final ArtistService artistService;

    @Autowired
    public ArtistController(ArtistService artistService){
        this.artistService = artistService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ArtistGetDTO> create(@RequestBody @Valid ArtistCreateDTO artistCreateDTO){
        ArtistGetDTO createdArtist = this.artistService.createArtist(artistCreateDTO);
        return new ResponseEntity<>(createdArtist, HttpStatus.CREATED);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ArtistGetDTO> findById(@PathVariable Long id) {
        ArtistGetDTO artist = new ArtistGetDTO(this.artistService.findArtistById(id));
        return ResponseEntity.ok(artist);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ArtistGetDTO> findByName(@PathVariable String name) {
        ArtistGetDTO artist = new ArtistGetDTO(this.artistService.findArtistByName(name));
        return ResponseEntity.ok(artist);
    }

    @GetMapping
    public ResponseEntity<List<ArtistGetDTO>> findAll() {
        List<ArtistGetDTO> artists = this.artistService.findAllArtists();
        return ResponseEntity.ok(artists);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<ArtistGetDTO> update(@RequestBody @Valid ArtistUpdateDTO artistUpdateDTO) {
        ArtistGetDTO updatedArtist = this.artistService.updateArtist(artistUpdateDTO);
        return ResponseEntity.ok(updatedArtist);
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        this.artistService.deleteArtist(id);
        return ResponseEntity.noContent().build();
    }
}
