package com.todolist.domain.comment.service;

import com.todolist.domain.comment.dto.CommentRequestDTO;
import com.todolist.domain.comment.dto.CommentResponseDTO;

public interface CommentService {

    CommentResponseDTO createComment(Long todoId,CommentRequestDTO commentRequestDTO);

    CommentResponseDTO updateComment(Long commentId,CommentRequestDTO commentRequestDTO);

}
