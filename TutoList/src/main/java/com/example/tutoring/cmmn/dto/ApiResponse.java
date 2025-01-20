package com.example.tutoring.cmmn.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse <T> {
	private boolean ok;
	private String message;
	private T data;

	public static <T> ApiResponse<T> isSuccess(T data) {
		return new ApiResponse<>(true,"SUCCESS", data);
	}

	public static <T> ApiResponse<T> isError(String message) {
		return new ApiResponse<>(false, message, null);
	}
}
