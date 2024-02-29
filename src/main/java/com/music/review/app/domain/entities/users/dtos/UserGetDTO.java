package com.music.review.app.domain.entities.users.dtos;


import com.music.review.app.domain.entities.users.User;

public record UserGetDTO(
        Long id,
        String email
)
{
    public UserGetDTO(User user){
        this(user.getId(), user.getEmail());
    }

}
