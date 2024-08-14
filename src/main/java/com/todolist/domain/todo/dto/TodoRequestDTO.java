package com.todolist.domain.todo.dto;

import com.todolist.domain.todo.model.Todo;
import com.todolist.domain.user.model.User;
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

    @NotBlank(message = "비밀번호는 필수입니다")
    private String password;

    public Todo toTodo( User user ){
        return new Todo(
                this.title,
                this.content,
                user.getNickname(),
                this.password,
                user
        );
    }
}
