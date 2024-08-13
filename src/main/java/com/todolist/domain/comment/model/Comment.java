package com.todolist.domain.comment.model;

import com.todolist.domain.todo.model.Todo;
import com.todolist.domain.user.model.User;
import com.todolist.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "comment")
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "writer")
    private String writer;

    @ManyToOne
    @JoinColumn(name = "todo")
    private Todo todo;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    public Comment(String content, String writer, Todo todo, User user) {
        this.content = content;
        this.writer = writer;
        this.todo = todo;
        this.user = user;
    }

    public void update(String content){
        this.content = content;
    }

}
