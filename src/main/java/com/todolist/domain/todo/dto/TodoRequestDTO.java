package com.todolist.domain.todo.dto;

import com.todolist.domain.todo.model.Todo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodoRequestDTO {
    @NotBlank(message = "제목을 입력해주세요")
    @Size(max = 200,message = "200자 이내로 작성해주세요")
    private String title;

    private String content;
    @Email(message = "이메일형식으로 입력해주세요")
    private String writer;
    @NotBlank(message = "비밀번호는 필수입니다")
    private String password;

    public Todo toTodo(){
        return new Todo(
                this.title,
                this.content,
                this.writer,
                this.password
        );
    }
}