package com.music.review.app.controllers;

import com.music.review.app.domain.entities.users.dtos.UserCreateDTO;
import com.music.review.app.domain.entities.users.dtos.UserGetDTO;
import com.music.review.app.domain.entities.users.dtos.UserUpdateDTO;
import com.music.review.app.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<UserGetDTO> findByIdUser(@PathVariable Long id){
        UserGetDTO userGetDTO = this.userService.findById(id);
        return new ResponseEntity<>(userGetDTO, HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
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
    public ResponseEntity<Object> deleteUser(@PathVariable Long id){
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
