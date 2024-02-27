package com.music.review.app.domain.entities.users;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UserUpdateDTO(
        @NotNull
        UUID id,
        String email,
        String password
) {
}
