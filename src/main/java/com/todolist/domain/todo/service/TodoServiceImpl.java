package com.todolist.domain.todo.service;

import com.todolist.domain.todo.dto.TodoDeleteRequestDTO;
import com.todolist.domain.todo.dto.TodoRequestDTO;
import com.todolist.domain.todo.dto.TodoResponseDTO;
import com.todolist.domain.todo.dto.TodoUpdateDTO;
import com.todolist.domain.todo.model.Todo;
import com.todolist.domain.todo.repository.TodoRepository;
import com.todolist.domain.user.model.User;
import com.todolist.domain.user.repository.UserRepository;
import com.todolist.exception.NotFoundException;
import com.todolist.exception.UserException;
import com.todolist.util.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    public TodoServiceImpl(TodoRepository todoRepository , JwtUtil jwtUtil,  UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public TodoResponseDTO createTodo(TodoRequestDTO todoRequestDTO, HttpServletRequest httpServletRequest) {

        String username = jwtUtil.getUsernameFromToken(httpServletRequest);

        User user = userRepository.findByUsername(username).orElseThrow(
                ()-> new UserException("로그인 정보가 올바르지 않습니다")
        );


        Todo todo = todoRequestDTO.toTodo(user);
        todoRepository.save(todo);
        return TodoResponseDTO.fromTodo(todo);
    }
    @Transactional
    @Override
    public TodoResponseDTO updateTodo(TodoUpdateDTO todoUpdateDTO, HttpServletRequest httpServletRequest) {

        String username = jwtUtil.getUsernameFromToken(httpServletRequest);

        Todo todo = todoRepository.findById(todoUpdateDTO.getId()).orElseThrow(
                () -> new NotFoundException("게시글이 없습니다")
        );

        if (!todo.getPassword().equals(todoUpdateDTO.getPassword())){
            throw new IllegalArgumentException("비밀번호가 다릅니다");
        }

        if (!todo.getUser().getUsername().equals(username)){
            throw new UserException("권한이 없습니다");
        }

        todo.update(todoUpdateDTO.getTitle(),todoUpdateDTO.getContent());
        todoRepository.save(todo);
        return TodoResponseDTO.fromTodo(todo);



    }
    @Transactional
    @Override
    public void deleteTodo(TodoDeleteRequestDTO todoDeleteRequestDTO, HttpServletRequest httpServletRequest) {

        String username = jwtUtil.getUsernameFromToken(httpServletRequest);

        Todo todo = todoRepository.findById(todoDeleteRequestDTO.getId()).orElseThrow(
                () -> new NotFoundException("게시글이 없습니다")
        );

        if (!todo.getPassword().equals(todoDeleteRequestDTO.getPassword())){
            throw new IllegalArgumentException("비밀번호가 다릅니다");
        }

        if (!todo.getUser().getUsername().equals(username)){
            throw new UserException("권한이 없습니다");
        }
        todoRepository.delete(todo);
    }

    @Override
    public TodoResponseDTO getTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(
                () -> new NotFoundException("일정이 없습니다")
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
