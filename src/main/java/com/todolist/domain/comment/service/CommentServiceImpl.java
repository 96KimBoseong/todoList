package com.todolist.domain.comment.service;

import com.todolist.domain.comment.dto.CommentRequestDTO;
import com.todolist.domain.comment.dto.CommentResponseDTO;
import com.todolist.domain.comment.model.Comment;
import com.todolist.domain.comment.repository.CommentRepository;
import com.todolist.domain.todo.model.Todo;
import com.todolist.domain.todo.repository.TodoRepository;
import com.todolist.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final TodoRepository todoRepository;
    public CommentServiceImpl(CommentRepository commentRepository, TodoRepository todoRepository) {
        this.commentRepository = commentRepository;
        this.todoRepository = todoRepository;
    }

    @Transactional
    @Override
    public CommentResponseDTO createComment(Long todoId,CommentRequestDTO commentRequestDTO) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(()-> new NotFoundException("일정을 찾을 수 없습니다"));
        Comment comment = commentRequestDTO.toComment(todo);
        commentRepository.save(comment);
        return CommentResponseDTO.from(comment);
    }

    @Override
    public CommentResponseDTO updateComment( Long commentId, CommentRequestDTO commentRequestDTO) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundException("댓글이 없습니다")
        );
        comment.update(commentRequestDTO.getContent());
        commentRepository.save(comment);
        return CommentResponseDTO.from(comment);
    }
}
