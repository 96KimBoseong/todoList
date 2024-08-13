package com.todolist.domain.comment.dto;

import com.todolist.domain.comment.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDTO {
    private Long id;
    private String content;
    private String writer;
    private LocalDateTime createdAt;
    private Long todoId;
    private String username;

    public static CommentResponseDTO from(Comment comment) {
        return new CommentResponseDTO(
                comment.getId(),
                comment.getContent(),
                comment.getWriter(),
                comment.getCreatedAt(),
                comment.getTodo().getId(),
                comment.getUser().getUsername()
        );
    }
}
