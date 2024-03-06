package com.music.review.app.domain.entities.users;

import com.music.review.app.domain.entities.users.dtos.UserCreateDTO;
import com.music.review.app.domain.entities.users.dtos.UserUpdateDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name = "user")
@Table(name = "users")
public class User implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
