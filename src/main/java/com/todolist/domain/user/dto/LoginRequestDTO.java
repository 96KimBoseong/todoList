package com.todolist.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class LoginRequestDTO {
    @NotBlank(message = "id는 필수값입니다")
    private String username;
    @NotBlank(message = "패스워드는 필수값입니다")
    private String password;
}
