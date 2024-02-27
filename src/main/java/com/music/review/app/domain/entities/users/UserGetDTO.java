package com.music.review.app.domain.entities.users;

import java.util.UUID;

public record UserGetDTO(
        UUID id,
        String email
)
{
    public UserGetDTO(User user){
        this(user.getId(), user.getEmail());
    }

}
