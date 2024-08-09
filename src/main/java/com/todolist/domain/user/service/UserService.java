package com.todolist.domain.user.service;

import com.todolist.domain.user.dto.SignUpDTO;
import com.todolist.domain.user.dto.UserResponseDTO;

public interface UserService {
    UserResponseDTO signup(SignUpDTO signUpDTO);
}
