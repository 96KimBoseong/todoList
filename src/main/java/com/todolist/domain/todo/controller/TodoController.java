package com.todolist.domain.todo.controller;

import com.todolist.domain.todo.dto.TodoDeleteRequestDTO;
import com.todolist.domain.todo.dto.TodoRequestDTO;
import com.todolist.domain.todo.dto.TodoResponseDTO;
import com.todolist.domain.todo.dto.TodoUpdateDTO;
import com.todolist.domain.todo.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping("/create")
    @Operation(summary = "todo 생성", description = "todo 생성")
    public ResponseEntity<TodoResponseDTO> createTodo(@Valid @RequestBody TodoRequestDTO request, HttpServletRequest httpServletRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(todoService.createTodo(request, httpServletRequest));
    }

    @PatchMapping("/update")
    @Operation(summary = "todo 수정", description = "todo 수정")
    public ResponseEntity<TodoResponseDTO> updateTodo(@Valid @RequestBody TodoUpdateDTO request, HttpServletRequest httpServletRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(todoService.updateTodo(request,httpServletRequest));
    }

    @DeleteMapping("/delete/{todoId}")
    @Operation(summary = "todo 삭제", description = "todo 삭제")
    public ResponseEntity<Void> deleteTodo(@Valid @RequestBody TodoDeleteRequestDTO request, HttpServletRequest httpServletRequest) {
        todoService.deleteTodo(request, httpServletRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/get/{todoId}")
    @Operation(summary = "todo 단건 조회", description = "todo 단건 조회")
    public ResponseEntity<TodoResponseDTO> getTodo(@PathVariable Long todoId) {
        return ResponseEntity.status(HttpStatus.OK).body(todoService.getTodo(todoId));
    }
    @GetMapping("/getList")
    @Operation(summary = "todoList 조회", description = "todoList 조회")
    public ResponseEntity<List<TodoResponseDTO>> getTodoList() {
        return ResponseEntity.status(HttpStatus.OK).body(todoService.getAllTodos());
    }
}
