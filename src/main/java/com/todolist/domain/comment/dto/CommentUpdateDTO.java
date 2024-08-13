package com.todolist.domain.comment.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentUpdateDTO {
    @NotBlank(message = "내용은 필수 값입니다")
    private String content;
    @Email
    private String writer;
}
