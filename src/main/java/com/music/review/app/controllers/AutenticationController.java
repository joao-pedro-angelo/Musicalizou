package com.music.review.app.controllers;

import com.music.review.app.domain.entities.users.User;
import com.music.review.app.domain.entities.users.dtos.DataLogin;
import com.music.review.app.infra.security.Tokens;
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
    private final Tokens tokens;

    @Autowired
    public AutenticationController(AuthenticationManager manager, Tokens tokens){
        this.manager = manager;
        this.tokens = tokens;
    }

    @PostMapping
    public ResponseEntity<String> login(@RequestBody @Valid DataLogin dataLogin){
        var dataLoginSpring = new UsernamePasswordAuthenticationToken(dataLogin.email(), dataLogin.password());
        var authentication = this.manager.authenticate(dataLoginSpring);
        var token = this.tokens.gerarToken((User) authentication.getPrincipal());

        return ResponseEntity.ok(token);
    }
}
