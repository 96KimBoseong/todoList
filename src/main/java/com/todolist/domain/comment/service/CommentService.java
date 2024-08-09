package com.todolist.domain.comment.service;

import com.todolist.domain.comment.dto.CommentDeleteRequestDTO;
import com.todolist.domain.comment.dto.CommentRequestDTO;
import com.todolist.domain.comment.dto.CommentResponseDTO;
import com.todolist.domain.comment.dto.CommentUpdateDTO;

public interface CommentService {

    CommentResponseDTO createComment(Long todoId,CommentRequestDTO commentRequestDTO);

    CommentResponseDTO updateComment(Long commentId, CommentUpdateDTO commentRequestDTO);

    void deleteComment(Long commentId, CommentDeleteRequestDTO commentDeleteRequestDTO);



}
