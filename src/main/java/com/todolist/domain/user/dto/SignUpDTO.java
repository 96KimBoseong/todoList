package com.todolist.domain.user.dto;

import com.todolist.domain.user.model.User;
import com.todolist.domain.user.model.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpDTO {
    @NotBlank(message = "별명은 필수입니다")
    private String nickname;

    @NotBlank(message = "이름은 필수값입니다")
    @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "이름은 4자 이상 10자 이하의 알파벳 소문자와 숫자로만 구성되어야 합니다.")
    private String username;

    @NotBlank(message = "비밀번호는 필수값입니다")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,15}$", message = "비밀번호는 8자 이상 15자 이하의 알파벳 대소문자와 숫자로만 구성되어야 합니다.")
    private String password;

    private UserRole role;

    public User toUser(){
        return new User(
                this.nickname,
                this.username,
                this.password,
                this.role
        );
    }

    public SignUpDTO(String nickname,String username, String password, UserRole role){
        this.username = username;
        this.password = password;
        this.role = role;
    }
    // 테스트용 생성자
}
