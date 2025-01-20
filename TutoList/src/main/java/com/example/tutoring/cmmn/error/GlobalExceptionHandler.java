package com.example.tutoring.cmmn.error;

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
    public ApiResponse<String> handleCustomException(CustomException ex) {
        log.warn("Internal Server Error", ex);
        return ApiResponse.isError(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<String> handleMethodValidException(MethodArgumentNotValidException ex){
        log.warn("MethodArgumentNotValidException Occurred: {}", ex.getMessage());
        return ApiResponse.isError(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());

    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<String> handleException(Exception ex) {
        log.error("Unhandled Exception", ex);
        return ApiResponse.isError("UNEXPECTED_ERROR");
    }
}
