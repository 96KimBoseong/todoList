package com.todolist.domain.todo.repository;

import com.todolist.domain.todo.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    Optional<Todo> findByIdAndPassword(Long id, String password);
}
