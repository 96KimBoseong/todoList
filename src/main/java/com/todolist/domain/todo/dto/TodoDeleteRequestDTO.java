package com.todolist.domain.todo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodoDeleteRequestDTO {
    @NotNull(message = "일정 아이디를 입력해주세요")
    private Long id;
    @NotBlank(message = "비밀번호는 필수입니다")
    private String password;
    }

