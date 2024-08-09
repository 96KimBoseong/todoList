package com.todolist.domain.user.dto;

import com.todolist.domain.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {
    private Long id;
    private String nickname;
    private String username;
    private String token;

    public static LoginResponseDTO from(User user, String token) {
        return new LoginResponseDTO(
                user.getId(),
                user.getNickname(),
                user.getUsername(),
                token
        );
    }
}
