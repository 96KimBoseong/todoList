package com.todolist.domain.todo.service;

import com.todolist.domain.todo.dto.TodoDeleteRequestDTO;
import com.todolist.domain.todo.dto.TodoRequestDTO;
import com.todolist.domain.todo.dto.TodoResponseDTO;
import com.todolist.domain.todo.dto.TodoUpdateDTO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface TodoService {
    TodoResponseDTO createTodo(TodoRequestDTO todoRequestDTO, HttpServletRequest httpServletRequest);

    TodoResponseDTO updateTodo(TodoUpdateDTO todoUpdateDTO, HttpServletRequest httpServletRequest);

    void deleteTodo(TodoDeleteRequestDTO todoDeleteRequestDTO,HttpServletRequest httpServletRequest);

    TodoResponseDTO getTodo(Long todoId);
    List<TodoResponseDTO> getAllTodos();
}
