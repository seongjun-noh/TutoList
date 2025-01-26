package com.example.tutoring.cmmn.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.tutoring.cmmn.dto.ApiResponse;
import com.example.tutoring.cmmn.error.exception.CustomException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<String>> handleCustomException(CustomException ex) {
        log.warn("Internal Server Error", ex);
        return ApiResponse.isError(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<String>> handleMethodValidException(MethodArgumentNotValidException ex){
        log.warn("MethodArgumentNotValidException Occurred: {}", ex);
        return ApiResponse.isError(HttpStatus.BAD_REQUEST, ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleException(Exception ex) {
        log.error("Unhandled Exception", ex);
        return ApiResponse.isError(HttpStatus.INTERNAL_SERVER_ERROR, "UNEXPECTED_ERROR");
    }
}
