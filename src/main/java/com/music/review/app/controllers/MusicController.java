package com.music.review.app.controllers;

import com.music.review.app.domain.entities.musics.dtos.MusicCreateDTO;
import com.music.review.app.domain.entities.musics.dtos.MusicGetDTO;
import com.music.review.app.domain.entities.musics.dtos.MusicUpdateDTO;
import com.music.review.app.services.MusicService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("musics")
public class MusicController {

    private final MusicService musicService;

    @Autowired
    public MusicController(MusicService musicService){
        this.musicService = musicService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<MusicGetDTO> createMusic(@RequestBody @Valid MusicCreateDTO musicCreateDTO){
        MusicGetDTO musicGetDTO = this.musicService.saveMusic(musicCreateDTO);
        return new ResponseEntity<>(musicGetDTO, HttpStatus.CREATED);
    }

    @GetMapping("/id/{idVar}")
    public ResponseEntity<MusicGetDTO> findMusicById(@PathVariable Long idVar){
        MusicGetDTO musicGetDTO = new MusicGetDTO(this.musicService.findById(idVar));
        return ResponseEntity.ok(musicGetDTO);
    }

    @GetMapping("/name/{nameMusic}")
    public ResponseEntity<MusicGetDTO> findMusicByName(@PathVariable String nameMusic){
        MusicGetDTO musicGetDTO = new MusicGetDTO(this.musicService.findByName(nameMusic));
        return ResponseEntity.ok(musicGetDTO);
    }

    @GetMapping
    public ResponseEntity<List<MusicGetDTO>> findAllMusics(){
        return ResponseEntity.ok(this.musicService.findAll());
    }

    @DeleteMapping("/delete/{deleteId}")
    @Transactional
    public ResponseEntity<Object> deleteById(@PathVariable Long deleteId){
        this.musicService.deleteMusic(deleteId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity<MusicGetDTO> updateMusic(@RequestBody @Valid MusicUpdateDTO musicUpdateDTO){
        MusicGetDTO musicGetDTO = this.musicService.update(musicUpdateDTO);
        return ResponseEntity.ok(musicGetDTO);
    }
}
