package com.example.tutoring.cmmn.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse <T> {
	private int status;
	private String message;
	private T data;

	public static <T> ApiResponse<T> isSuccess(T data) {
		ApiResponse responseBody =  new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", data);

		return responseBody;
	}

	public static <T> ResponseEntity<ApiResponse<T>> isError(HttpStatus status, String message) {
		int statusCode = status.value();
		ApiResponse responseBody =  new ApiResponse<>(statusCode, message, null);

		return ResponseEntity.status(statusCode).body(responseBody);
	}
}
