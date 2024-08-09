package com.todolist.domain.user.controller;

import com.todolist.domain.user.dto.SignUpDTO;
import com.todolist.domain.user.dto.UserResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDTO> signup(SignUpDTO request){
        return ResponseEntity.status(HttpStatus.CREATED).body()
    }
}
