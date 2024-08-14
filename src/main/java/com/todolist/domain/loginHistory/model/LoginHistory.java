package com.todolist.domain.loginHistory.model;

import com.todolist.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "login_history")
public class LoginHistory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDateTime lastTime;

    public LoginHistory(String username, String description) {
        this.username = username;
        this.description = description;
        this.lastTime = LocalDateTime.now();
    }

}
