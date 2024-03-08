package com.music.review.app.controllers;

import com.music.review.app.domain.entities.users.dtos.UserCreateDTO;
import com.music.review.app.domain.entities.users.dtos.UserGetDTO;
import com.music.review.app.domain.entities.users.dtos.UserUpdateDTO;
import com.music.review.app.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
@SecurityRequirement(name = "bearer-key")
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

    @GetMapping("/id/{id}")
    public ResponseEntity<UserGetDTO> findByIdUser(@PathVariable Long id){
        UserGetDTO userGetDTO = new UserGetDTO(this.userService.findById(id));
        return ResponseEntity.ok(userGetDTO);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserGetDTO> findByEmailUser(@PathVariable String email){
        UserGetDTO userGetDTO = new UserGetDTO(this.userService.findByEmail(email));
        return ResponseEntity.ok(userGetDTO);
    }

    @GetMapping
    public ResponseEntity<List<UserGetDTO>> findAllUsers(){
        return ResponseEntity.ok(this.userService.findAll());
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<Object> deleteUser(@PathVariable Long id){
        this.userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity<UserGetDTO> updateUser(@RequestBody @Valid UserUpdateDTO userUpdateDTO){
        UserGetDTO userGetDTO = this.userService.update(userUpdateDTO);
        return ResponseEntity.ok(userGetDTO);
    }
}
