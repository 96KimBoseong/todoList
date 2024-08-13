package com.todolist.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {

        StringBuilder errorMessage = new StringBuilder("유효성 검사 실패: ");


        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errorMessage.append(String.format("필드: %s, 오류: %s; ", fieldName, message));
        });
        // 오류 추출 + 메세지 생성


        return new ResponseEntity<>(
                new ErrorResponse(errorMessage.toString(), HttpStatus.BAD_REQUEST.value()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> handleException(IllegalArgumentException ex) {
        ErrorResponse ErrorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(ErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundExceptions(NotFoundException ex) {
        ErrorResponse ErrorResponse = new ErrorResponse(ex.getMessage(),HttpStatus.NOT_FOUND.value() );
        return new ResponseEntity<>(ErrorResponse, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResponse> handleUserExceptions(UserException ex) {
        ErrorResponse ErrorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(ErrorResponse, HttpStatus.BAD_REQUEST);
    }
}
