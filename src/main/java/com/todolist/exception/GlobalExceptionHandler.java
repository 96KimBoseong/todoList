package com.todolist.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {

        StringBuilder errorMessage = new StringBuilder("유효성 검사 실패, ");


        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errorMessage.append(String.format("필드: %s, 오류: %s; ", fieldName, message));

        });
        // 오류 추출 + 메세지 생성
        ErrorResponse errorResponse = new ErrorResponse(errorMessage.toString(),HttpStatus.BAD_REQUEST.value());
        int status = HttpStatus.BAD_REQUEST.value();
        log.error("상태코드: {}, 메세지: {}",status,errorMessage,ex);
        // 로그파일에 남기기

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> handleException(IllegalArgumentException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value());

        String errorMessage = errorResponse.getMessage();
        int status = errorResponse.getStatusCode() ;

        log.error("상태코드: {}, 메세지: {}",status,errorMessage,ex);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundExceptions(NotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(),HttpStatus.NOT_FOUND.value() );

        String errorMessage = errorResponse.getMessage();
        int status = errorResponse.getStatusCode() ;

        log.error("상태코드: {}, 메세지: {}",status,errorMessage,ex);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResponse> handleUserExceptions(UserException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value());

        String errorMessage = errorResponse.getMessage();
        int status = errorResponse.getStatusCode() ;

        log.error("상태코드: {}, 메세지: {}",status,errorMessage,ex);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
