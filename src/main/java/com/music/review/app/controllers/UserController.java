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
    public ResponseEntity<UserGetDTO> createUser(@RequestBody @Valid UserCreateDTO userCreateDTO){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.userService.saveUser(userCreateDTO));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UserGetDTO> findByIdUser(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new UserGetDTO(this.userService.findById(id)));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserGetDTO> findByEmailUser(@PathVariable String email){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new UserGetDTO(this.userService.findByEmail(email)));
    }

    @GetMapping
    public ResponseEntity<List<UserGetDTO>> findAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).
                body(this.userService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id){
        this.userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<UserGetDTO> updateUser(@RequestBody @Valid UserUpdateDTO userUpdateDTO){
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.userService.update(userUpdateDTO));
    }
}
