package com.music.review.app.domain.entities.users.dtos;

import jakarta.validation.constraints.NotNull;


public record UserUpdateDTO(
        @NotNull
        Long id,
        String email,
        String password
) {
}
