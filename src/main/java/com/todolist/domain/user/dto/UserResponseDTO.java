package com.todolist.domain.user.dto;

import com.todolist.domain.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

    private Long id;
    private String nickname;
    private String username;

    public static UserResponseDTO from(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getNickname(),
                user.getUsername()
        );
    }
}
