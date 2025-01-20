package com.example.tutoring.cmmn.error.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public enum ErrorMessage {
	ERROR_EXISTS_USERNAME("이미 존재하는 아이디입니다."),
	ERROR_INVALID_ROLE("유효하지 않은 권한입니다."),;

	private String description;
}
