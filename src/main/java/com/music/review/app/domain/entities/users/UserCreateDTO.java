package com.music.review.app.domain.entities.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserCreateDTO(
        @Email
        @NotBlank
        String email,

        @NotBlank
        String password
) {
}
