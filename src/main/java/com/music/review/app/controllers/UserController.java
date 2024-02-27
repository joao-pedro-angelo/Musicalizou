package com.music.review.app.controllers;

import com.music.review.app.domain.entities.users.UserCreateDTO;
import com.music.review.app.domain.entities.users.UserGetDTO;
import com.music.review.app.domain.entities.users.UserUpdateDTO;
import com.music.review.app.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<UserGetDTO> createUser(@RequestBody @Valid UserCreateDTO userCreateDTO){
        UserGetDTO userGetDTO = this.userService.saveUser(userCreateDTO);
        return new ResponseEntity<>(userGetDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserGetDTO> findByIdUser(@PathVariable UUID id){
        UserGetDTO userGetDTO = this.userService.findById(id);
        return new ResponseEntity<>(userGetDTO, HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserGetDTO> findByEmailUser(@PathVariable String email){
        UserGetDTO userGetDTO = this.userService.findByEmail(email);
        return new ResponseEntity<>(userGetDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserGetDTO>> findAllUsers(){
        return new ResponseEntity<>(this.userService.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Object> deleteUser(@PathVariable UUID id){
        this.userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<UserGetDTO> updateUser(@RequestBody @Valid UserUpdateDTO userUpdateDTO){
        UserGetDTO userGetDTO = this.userService.update(userUpdateDTO);
        return new ResponseEntity<>(userGetDTO, HttpStatus.OK);
    }
}
