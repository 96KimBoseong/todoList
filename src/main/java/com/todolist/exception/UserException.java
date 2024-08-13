package com.todolist.exception;

public class UserException extends RuntimeException {
    public UserException(String errorMessage) {
        super(errorMessage);
    }
}
