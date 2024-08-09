package com.todolist.domain.todo.service;

import com.todolist.domain.todo.dto.TodoRequestDTO;
import com.todolist.domain.todo.dto.TodoResponseDTO;
import com.todolist.domain.todo.model.Todo;
import com.todolist.domain.todo.repository.TodoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Transactional
    @Override
    public TodoResponseDTO createTodo(TodoRequestDTO todoRequestDTO) {

        Todo todo = todoRequestDTO.toTodo();
        todoRepository.save(todo);
        return TodoResponseDTO.fromTodo(todo);
    }
    @Transactional
    @Override
    public TodoResponseDTO updateTodo(Long todoId,TodoRequestDTO todoRequestDTO) {
        Todo todo = todoRepository.findByIdAndPassword(todoId,todoRequestDTO.getPassword()).orElseThrow(
                ()-> new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다")
        );
        todo.update(todoRequestDTO.getTitle(),todoRequestDTO.getContent(),todoRequestDTO.getWriter());
        todoRepository.save(todo);
        return TodoResponseDTO.fromTodo(todo);
    }
}
