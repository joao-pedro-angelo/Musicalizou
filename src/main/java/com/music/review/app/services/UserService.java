package com.music.review.app.services;

import com.music.review.app.domain.entities.users.User;
import com.music.review.app.domain.entities.users.UserCreateDTO;
import com.music.review.app.domain.entities.users.UserGetDTO;
import com.music.review.app.domain.entities.users.UserUpdateDTO;
import com.music.review.app.domain.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserGetDTO saveUser(UserCreateDTO userCreateDTO){
        User user = new User(userCreateDTO);
        UserGetDTO userGetDTO = new UserGetDTO(user);
        this.userRepository.save(user);
        return userGetDTO;
    }

    public UserGetDTO findById(UUID id){
        Optional<User> userOptional = this.userRepository.findById(id);
        if (userOptional.isPresent()){
            User user = userOptional.get();
            return new UserGetDTO(user);
        } else {
            throw new EntityNotFoundException("User not found with id: " + id);
        }
    }

    public UserGetDTO findByEmail(String email){
        Optional<User> userOptional = this.userRepository.findByEmail(email);
        if (userOptional.isPresent()){
            User user = userOptional.get();
            return new UserGetDTO(user);
        } else{
            throw new EntityNotFoundException("User not found with email: " + email);
        }
    }

    public List<UserGetDTO> findAll(){
        List<User> users = this.userRepository.findAll();
        return users.stream()
                .map(UserGetDTO::new)
                .collect(Collectors.toList());
    }

    public void deleteById(UUID id){
        this.userRepository.deleteById(id);
    }

    public UserGetDTO update(UserUpdateDTO userUpdateDTO){
        Optional<User> userOptional = this.userRepository.findById(userUpdateDTO.id());
        if (userOptional.isPresent()){
            User user = userOptional.get();
            user.updateUser(userUpdateDTO);
            return new UserGetDTO(user);
        } else{
            throw new EntityNotFoundException("Not found User with ID: " + userUpdateDTO.id());
        }
    }

}
