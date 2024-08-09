package com.todolist.domain.todo.service;

import com.todolist.domain.todo.dto.TodoDeleteRequestDTO;
import com.todolist.domain.todo.dto.TodoRequestDTO;
import com.todolist.domain.todo.dto.TodoResponseDTO;
import com.todolist.domain.todo.dto.TodoUpdateDTO;
import com.todolist.domain.todo.model.Todo;
import com.todolist.domain.todo.repository.TodoRepository;
import com.todolist.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

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
                ()-> {
                    boolean exists = todoRepository.existsById(todoDeleteRequestDTO.getId());
                    if(exists) {
                        return new IllegalArgumentException("아이디랑 비밀면호를 확인하세요");
                    }else{
                        return new NotFoundException("일정을 찾을 수 없습니다");
                    }
                }
        );
        todoRepository.delete(todo);
    }

    @Override
    public TodoResponseDTO getTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(
                () -> new IllegalArgumentException("일정이 없습니다")
        );
        return TodoResponseDTO.fromTodo(todo);
    }

    @Override
    public List<TodoResponseDTO> getAllTodos() {
        List<Todo> todos = todoRepository.findAll();
        todos.sort(Comparator.comparing(Todo::getCreatedAt).reversed());
        return todos.stream().map(TodoResponseDTO::fromTodo).toList();
    }
}
