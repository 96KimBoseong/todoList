package com.todolist.domain.user.service;

import com.todolist.domain.user.dto.SignUpDTO;
import com.todolist.domain.user.dto.UserResponseDTO;
import com.todolist.domain.user.model.User;
import com.todolist.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserResponseDTO signup(SignUpDTO signUpDTO) {
        if (userRepository.existsByUsername(signUpDTO.getUsername())) {
            throw new IllegalArgumentException("Username is already in use");
        }

        User user = signUpDTO.toUser();
        userRepository.save(user);
        return UserResponseDTO.from(user);
    }
}
