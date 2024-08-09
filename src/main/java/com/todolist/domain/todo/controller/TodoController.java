package com.todolist.domain.todo.controller;

import com.todolist.domain.todo.dto.TodoRequestDTO;
import com.todolist.domain.todo.dto.TodoResponseDTO;
import com.todolist.domain.todo.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;
    public TodoController(final TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping("/create")
    public ResponseEntity<TodoResponseDTO> createTodo(@Valid @RequestBody TodoRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(todoService.createTodo(request));
    }

    @PatchMapping("/update/{todoId}")
    public ResponseEntity<TodoResponseDTO> updateTodo(@PathVariable Long todoId,@Valid @RequestBody TodoRequestDTO request) {
        return ResponseEntity.status(HttpStatus.OK).body(todoService.updateTodo(todoId, request));
    }
}
