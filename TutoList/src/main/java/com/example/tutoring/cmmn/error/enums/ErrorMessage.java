package com.example.tutoring.cmmn.error.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {
	// 사용자 관련 에러
	ERROR_USER_NOT_FOUND("유저 정보를 찾을 수 없습니다."),
	ERROR_EXISTS_USERNAME("이미 존재하는 아이디입니다."),
	ERROR_INVALID_ROLE("유효하지 않은 권한입니다."),

	ERROR_INVALID_DATE_RANGE("유효하지 않은 날짜범위입니다."),
	ERROR_INVALID_RRULE("유효하지 않은 RRULE입니다."),

	ERROR_STUDENT_NOT_FOUND("학생 정보를 찾을 수 없습니다."),

	// 인증/권한 에러
	ERROR_UNAUTHORIZED("인증되지 않은 사용자입니다."),
	ERROR_FORBIDDEN("권한이 없는 사용자입니다."),
	ERROR_ACCOUNT_EXPIRED("만료된 계정입니다."),
	ERROR_INVALID_USERNAME_OR_PASSWORD("아이디 또는 비밀번호가 일치하지 않습니다."),
	ERROR_ACCOUNT_LOCKED("계정이 잠겨 있습니다."),
	ERROR_ACCOUNT_DISABLED("계정이 비활성화되었습니다."),

	// 기타 에러
	ERROR_UNKNOWN("알 수 없는 에러입니다.");

	private final String description;
}
