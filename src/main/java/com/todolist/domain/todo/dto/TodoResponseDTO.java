package com.todolist.domain.todo.dto;

import com.todolist.domain.todo.model.Todo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TodoResponseDTO {
    private Long id;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime createdAt;


    public static TodoResponseDTO fromTodo(Todo todo) {
        return new TodoResponseDTO(
                todo.getId(),
                todo.getTitle(),
                todo.getContent(),
                todo.getWriter(),
                todo.getCreatedAt()
        );
    }
}

