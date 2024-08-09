package com.todolist.domain.user.service;

import com.todolist.domain.user.dto.LoginRequestDTO;
import com.todolist.domain.user.dto.LoginResponseDTO;
import com.todolist.domain.user.dto.SignUpDTO;
import com.todolist.domain.user.dto.UserResponseDTO;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {
    UserResponseDTO signup(SignUpDTO signUpDTO);
    LoginResponseDTO login(LoginRequestDTO loginRequestDTO, HttpServletResponse response);
}
