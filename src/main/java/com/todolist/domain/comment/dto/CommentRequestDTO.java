package com.todolist.domain.comment.dto;

import com.todolist.domain.comment.model.Comment;
import com.todolist.domain.todo.model.Todo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDTO {
    @NotBlank(message = "댓글은 공백이 불가합니다")
    @Size(max = 200,message = "200자 이내로 작성해주세요")
    private String content;
    @Email(message = "email 형식으로 입력해주세요")
    private String writer;

    public Comment toComment(Todo todo){
        return new Comment(
                this.content,
                this.writer,
                todo
        );
    }
}
