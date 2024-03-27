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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/artists")
@SecurityRequirement(name = "bearer-key")
public class ArtistController {

    private final ArtistService artistService;

    @Autowired
    public ArtistController(ArtistService artistService){
        this.artistService = artistService;
    }

    @PostMapping
    public ResponseEntity<ArtistGetDTO> create(@RequestBody @Valid ArtistCreateDTO artistCreateDTO){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.artistService.createArtist(artistCreateDTO));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ArtistGetDTO> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ArtistGetDTO(this.artistService.findArtistById(id)));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ArtistGetDTO> findByName(@PathVariable String name) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ArtistGetDTO(this.artistService.findArtistByName(name)));
    }

    @GetMapping
    public ResponseEntity<List<ArtistGetDTO>> findAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.artistService.findAllArtists());
    }

    @PutMapping
    public ResponseEntity<ArtistGetDTO> update(@RequestBody @Valid ArtistUpdateDTO artistUpdateDTO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.artistService.updateArtist(artistUpdateDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        this.artistService.deleteArtist(id);
        return ResponseEntity.noContent().build();
    }
}
