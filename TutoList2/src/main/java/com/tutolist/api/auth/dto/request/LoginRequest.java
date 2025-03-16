package com.tutolist.api.auth.dto.request;

import com.tutolist.common.validation.annotation.ValidPassword;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
	@Email(message = "올바른 이메일 형식이 아닙니다.")
	@NotBlank(message = "이메일을 입력해주세요.")
	String email,

	@ValidPassword
	String password
) {
	public LoginRequest {
		if (email != null) {
			email = email.trim();
		}
	}
}
