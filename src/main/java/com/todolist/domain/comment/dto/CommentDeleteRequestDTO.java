package com.todolist.domain.comment.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentDeleteRequestDTO {
    @Email
    private String writer;
}
