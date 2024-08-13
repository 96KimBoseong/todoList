package com.todolist.domain.todo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodoUpdateDTO {
    @NotNull(message = "id를 입력해주세요")
    private Long id;

    @NotBlank(message = "제목을 입력해주세요")
    @Size(max = 200,message = "200자 이내로 작성해주세요")
    private String title;

    private String content;

    @NotBlank(message = "비밀번호는 필수입니다")
    private String password;

}
