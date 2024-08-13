package com.todolist.domain.comment.service;

import com.todolist.domain.comment.dto.CommentRequestDTO;
import com.todolist.domain.comment.dto.CommentResponseDTO;
import com.todolist.domain.comment.dto.CommentUpdateDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface CommentService {

    CommentResponseDTO createComment(Long todoId, CommentRequestDTO commentRequestDTO, HttpServletRequest httpServletRequest);

    CommentResponseDTO updateComment(Long commentId, CommentUpdateDTO commentRequestDTO,HttpServletRequest httpServletRequest);

    void deleteComment(Long commentId, HttpServletRequest httpServletRequest);



}
