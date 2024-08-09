package com.todolist.domain.todo.service;

import com.todolist.domain.todo.dto.TodoDeleteRequestDTO;
import com.todolist.domain.todo.dto.TodoRequestDTO;
import com.todolist.domain.todo.dto.TodoResponseDTO;
import com.todolist.domain.todo.dto.TodoUpdateDTO;

import java.util.List;

public interface TodoService {
    TodoResponseDTO createTodo(TodoRequestDTO todoRequestDTO);

    TodoResponseDTO updateTodo(TodoUpdateDTO todoUpdateDTO);

    void deleteTodo(TodoDeleteRequestDTO todoDeleteRequestDTO);

    TodoResponseDTO getTodo(Long todoId);
    List<TodoResponseDTO> getAllTodos();
}
