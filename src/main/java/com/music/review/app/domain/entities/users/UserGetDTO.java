package com.music.review.app.domain.entities.users;


public record UserGetDTO(
        Long id,
        String email
)
{
    public UserGetDTO(User user){
        this(user.getId(), user.getEmail());
    }

}
