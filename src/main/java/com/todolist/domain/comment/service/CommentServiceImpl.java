package com.todolist.domain.comment.service;

import com.todolist.domain.comment.dto.CommentRequestDTO;
import com.todolist.domain.comment.dto.CommentResponseDTO;
import com.todolist.domain.comment.dto.CommentUpdateDTO;
import com.todolist.domain.comment.model.Comment;
import com.todolist.domain.comment.repository.CommentRepository;
import com.todolist.domain.todo.model.Todo;
import com.todolist.domain.todo.repository.TodoRepository;
import com.todolist.domain.user.model.User;
import com.todolist.domain.user.repository.UserRepository;
import com.todolist.exception.NotFoundException;
import com.todolist.exception.UserException;
import com.todolist.util.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    public CommentServiceImpl(CommentRepository commentRepository, TodoRepository todoRepository, UserRepository userRepository, JwtUtil jwtUtil) {
        this.commentRepository = commentRepository;
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    @Override
    public CommentResponseDTO createComment(Long todoId,CommentRequestDTO commentRequestDTO, HttpServletRequest httpServletRequest) {

        String username = jwtUtil.getUsernameFromToken(httpServletRequest);

        Todo todo = todoRepository.findById(todoId).orElseThrow(()-> new NotFoundException("일정을 찾을 수 없습니다"));

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UserException("로그인 정보가 올바르지 않습니다")
        );

        Comment comment = commentRequestDTO.toComment(todo,user);
        commentRepository.save(comment);
        return CommentResponseDTO.from(comment);
    }

    @Transactional
    @Override
    public CommentResponseDTO updateComment(Long commentId, CommentUpdateDTO commentRequestDTO, HttpServletRequest httpServletRequest) {

        String username = jwtUtil.getUsernameFromToken(httpServletRequest);

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundException("댓글이 없습니다")
        );

        if (!comment.getUser().getUsername().equals(username)){
            throw new UserException("로그인 정보와 상이하여 권한이 없습니다");
        }

        comment.update(commentRequestDTO.getContent());
        commentRepository.save(comment);
        return CommentResponseDTO.from(comment);
    }
    @Transactional
    @Override
    public void deleteComment(Long commentId, HttpServletRequest httpServletRequest) {
        String username = jwtUtil.getUsernameFromToken(httpServletRequest);
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new NotFoundException("댓글이 없습니다")
        );
        if (!comment.getUser().getUsername().equals(username)) {
            throw new UserException("사용자가 일치 하지 않습니다");
        }
        commentRepository.delete(comment);
    }
}
