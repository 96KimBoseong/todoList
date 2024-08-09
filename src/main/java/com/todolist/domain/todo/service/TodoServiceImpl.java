package com.todolist.domain.todo.service;

import com.todolist.domain.todo.dto.TodoDeleteRequestDTO;
import com.todolist.domain.todo.dto.TodoRequestDTO;
import com.todolist.domain.todo.dto.TodoResponseDTO;
import com.todolist.domain.todo.dto.TodoUpdateDTO;
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
    public TodoResponseDTO updateTodo(TodoUpdateDTO todoUpdateDTO) {
        Todo todo = todoRepository.findByIdAndPassword(todoUpdateDTO.getId(),todoUpdateDTO.getPassword()).orElseThrow(
                ()-> new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다")
        );
        todo.update(todoUpdateDTO.getTitle(),todoUpdateDTO.getContent(),todoUpdateDTO.getWriter());
        todoRepository.save(todo);
        return TodoResponseDTO.fromTodo(todo);
    }
    @Transactional
    @Override
    public void deleteTodo(TodoDeleteRequestDTO todoDeleteRequestDTO) {
        Todo todo = todoRepository.findByIdAndPassword(todoDeleteRequestDTO.getId(),todoDeleteRequestDTO.getPassword()).orElseThrow(
                ()-> new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다")
        );
        todoRepository.delete(todo);
    }

}
