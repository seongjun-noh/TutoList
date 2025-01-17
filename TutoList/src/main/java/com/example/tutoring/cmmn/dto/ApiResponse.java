package com.example.tutoring.cmmn.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse <T> {
	private int status;
	private String message;
	private T data;

	public static <T> ApiResponse<T> isSuccess(T data) {
		return new ApiResponse<>(200,"SUCCESS", data);
	}

	public static <T> ApiResponse<T> isError(int status, String message) {
		return new ApiResponse<>(status, message, null);
	}
}
