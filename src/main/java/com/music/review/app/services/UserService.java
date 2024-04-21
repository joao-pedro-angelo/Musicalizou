package com.music.review.app.services;

import com.music.review.app.domain.entities.users.User;
import com.music.review.app.domain.entities.users.dtos.UserCreateDTO;
import com.music.review.app.domain.entities.users.dtos.UserGetDTO;
import com.music.review.app.domain.entities.users.dtos.UserUpdateDTO;
import com.music.review.app.domain.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Transactional
    public UserGetDTO saveUser(UserCreateDTO userCreateDTO){
        User user = new User(userCreateDTO);
        this.userRepository.save(user);
        return new UserGetDTO(user);
    }

    public User findById(Long id){
        return this.userRepository.getReferenceById(id);
    }

    public User findByEmail(String email){
        User user = this.userRepository.getUserByEmail(email);
        if (user == null) throw new EntityNotFoundException();
        return user;
    }

    public List<UserGetDTO> findAll(){
        List<User> users = this.userRepository.findAll();
        return users.stream()
                .map(UserGetDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteById(Long id){
        this.userRepository.deleteById(id);
    }

    @Transactional
    public UserGetDTO update(UserUpdateDTO userUpdateDTO){
        User user = this.userRepository.getReferenceById(userUpdateDTO.id());
        user.updateUser(userUpdateDTO);
        this.userRepository.save(user);
        return new UserGetDTO(user);
    }

}
