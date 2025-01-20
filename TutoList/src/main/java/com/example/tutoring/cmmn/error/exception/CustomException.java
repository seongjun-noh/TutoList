package com.example.tutoring.cmmn.error.exception;

import com.example.tutoring.cmmn.error.enums.ErrorMessage;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
	public CustomException(ErrorMessage errorMessage) {
		super(errorMessage.toString());
	}
}
