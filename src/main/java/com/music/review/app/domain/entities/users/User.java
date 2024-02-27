package com.music.review.app.domain.entities.users;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user")
@Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private Long id;
    //Não precisa validar aqui, pois a validação será nos DTOs
    private String email;
    private String password;

    public User(UserCreateDTO userCreateDTO){
        this.email = userCreateDTO.email();
        this.password = userCreateDTO.password();
    }

    public void updateUser(UserUpdateDTO userUpdateDTO){
        if (userUpdateDTO.email() != null) this.email = userUpdateDTO.email();
        if (userUpdateDTO.password() != null) this.password = userUpdateDTO.password();
    }

}
