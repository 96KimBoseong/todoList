package com.todolist.domain.comment.controller;

import com.todolist.domain.comment.dto.CommentRequestDTO;
import com.todolist.domain.comment.dto.CommentResponseDTO;
import com.todolist.domain.comment.dto.CommentUpdateDTO;
import com.todolist.domain.comment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/creat/{todoId}")
    @Operation(summary = "댓글 작성",description = "댓글작성기능")
    public ResponseEntity<CommentResponseDTO> createComment(@PathVariable Long todoId,@Valid @RequestBody CommentRequestDTO request, HttpServletRequest httpServletRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(todoId, request, httpServletRequest));
    }

    @PatchMapping("/update/{commentId}")
    @Operation(summary = "댓글 수정",description = "댓글수정기능")
    public ResponseEntity<CommentResponseDTO> updateComment(@PathVariable Long commentId, @Valid @RequestBody CommentUpdateDTO request, HttpServletRequest httpServletRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.updateComment(commentId,request, httpServletRequest));
    }

    @DeleteMapping("/delete/{commentId}")
    @Operation(summary = "댓글 삭제",description = "댓글 삭제기능")
    public ResponseEntity<CommentResponseDTO> deleteComment(@PathVariable Long commentId, HttpServletRequest httpServletRequest) {
        commentService.deleteComment(commentId,httpServletRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
