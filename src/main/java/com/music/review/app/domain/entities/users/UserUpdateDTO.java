package com.music.review.app.domain.entities.users;

public record UserUpdateDTO(
        String email,
        String password
) {
}
