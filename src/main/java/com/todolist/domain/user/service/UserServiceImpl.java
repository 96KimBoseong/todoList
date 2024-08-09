package com.todolist.domain.user.service;

import com.todolist.domain.user.dto.LoginRequestDTO;
import com.todolist.domain.user.dto.LoginResponseDTO;
import com.todolist.domain.user.dto.SignUpDTO;
import com.todolist.domain.user.dto.UserResponseDTO;
import com.todolist.domain.user.model.User;
import com.todolist.domain.user.repository.UserRepository;
import com.todolist.exception.NotFoundException;
import com.todolist.util.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.jwtUtil = new JwtUtil();
    }
    @Transactional
    @Override
    public UserResponseDTO signup(SignUpDTO signUpDTO) {
        if (userRepository.existsByUsername(signUpDTO.getUsername())) {
            throw new IllegalArgumentException("Username is already in use");
        }

        User user = signUpDTO.toUser();
        userRepository.save(user);
        return UserResponseDTO.from(user);
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO, HttpServletResponse response) {
        User user = userRepository.findByUsername(loginRequestDTO.getUsername()).orElseThrow(
                ()-> new NotFoundException("user 정보가 없습니다 아이디를 확인해보세요")
        );
        if (!user.getPassword().equals(loginRequestDTO.getPassword())) {
            String token = jwtUtil.createToken(user.getUsername(), user.getRole());
            jwtUtil.addJwtToCookie(token,response);
            return LoginResponseDTO.from(user,token);
        }else {
            throw new IllegalArgumentException("password is wrong");
        }
    }


}
