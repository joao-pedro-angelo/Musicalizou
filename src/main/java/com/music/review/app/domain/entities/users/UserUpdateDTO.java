package com.music.review.app.domain.entities.users;

import jakarta.validation.constraints.NotNull;


public record UserUpdateDTO(
        @NotNull
        Long id,
        String email,
        String password
) {
}
