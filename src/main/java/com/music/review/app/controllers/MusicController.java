package com.music.review.app.controllers;

import com.music.review.app.domain.entities.musics.dtos.MusicCreateDTO;
import com.music.review.app.domain.entities.musics.dtos.MusicGetDTO;
import com.music.review.app.domain.entities.musics.dtos.MusicUpdateDTO;
import com.music.review.app.services.MusicService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/musics")
@SecurityRequirement(name = "bearer-key")
public class MusicController {

    private final MusicService musicService;

    @Autowired
    public MusicController(MusicService musicService){
        this.musicService = musicService;
    }

    @PostMapping
    public ResponseEntity<MusicGetDTO> createMusic(@RequestBody @Valid MusicCreateDTO musicCreateDTO){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.musicService.saveMusic(musicCreateDTO));
    }

    @GetMapping("/id/{idVar}")
    public ResponseEntity<MusicGetDTO> findMusicById(@PathVariable Long idVar){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new MusicGetDTO(this.musicService.findById(idVar)));
    }

    @GetMapping("/name/{nameMusic}")
    public ResponseEntity<MusicGetDTO> findMusicByName(@PathVariable String nameMusic){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new MusicGetDTO(this.musicService.findByName(nameMusic)));
    }

    @GetMapping
    public ResponseEntity<List<MusicGetDTO>> findAllMusics(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.musicService.findAll());
    }

    @DeleteMapping("/{deleteId}")
    public ResponseEntity<Object> deleteById(@PathVariable Long deleteId){
        this.musicService.deleteMusic(deleteId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<MusicGetDTO> updateMusic(@RequestBody @Valid MusicUpdateDTO musicUpdateDTO){
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.musicService.update(musicUpdateDTO));
    }
}
