package com.todolist.domain.todo.dto;

import com.todolist.domain.comment.dto.CommentResponseDTO;
import com.todolist.domain.todo.model.Todo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TodoResponseDTO {
    private Long id;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime createdAt;
    private List<CommentResponseDTO> commentList;


    public static TodoResponseDTO fromTodo(Todo todo) {
        List<CommentResponseDTO> comments = todo.getComments().stream().map(CommentResponseDTO::from).toList();
        // 엔티티 리스트 덩어리 dto 덩어리로 변환
        return new TodoResponseDTO(
                todo.getId(),
                todo.getTitle(),
                todo.getContent(),
                todo.getWriter(),
                todo.getCreatedAt(),
                comments
        );
    }
}

