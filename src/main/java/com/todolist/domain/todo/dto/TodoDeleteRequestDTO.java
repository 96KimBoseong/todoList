package com.todolist.domain.todo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodoDeleteRequestDTO {

    @NotBlank(message = "비밀번호는 필수입니다")
    private String password;
    }

