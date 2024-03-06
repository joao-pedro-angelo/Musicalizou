package com.music.review.app.controllers;

import com.music.review.app.domain.entities.users.dtos.DataLogin;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticationController {

    private final AuthenticationManager manager;

    @Autowired
    public AutenticationController(AuthenticationManager manager){
        this.manager = manager;
    }

    @PostMapping
    public ResponseEntity<Object> login(@RequestBody @Valid DataLogin dataLogin){
        var token = new UsernamePasswordAuthenticationToken(dataLogin.email(), dataLogin.password());
        var authentication = this.manager.authenticate(token);
        return ResponseEntity.ok().build();
    }
}
