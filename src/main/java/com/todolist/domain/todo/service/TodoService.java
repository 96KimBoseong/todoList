package com.todolist.domain.todo.service;

import com.todolist.domain.todo.dto.TodoRequestDTO;
import com.todolist.domain.todo.dto.TodoResponseDTO;

public interface TodoService {
    TodoResponseDTO createTodo(TodoRequestDTO todoRequestDTO);

    TodoResponseDTO updateTodo(Long todoId,TodoRequestDTO todoRequestDTO);
}
