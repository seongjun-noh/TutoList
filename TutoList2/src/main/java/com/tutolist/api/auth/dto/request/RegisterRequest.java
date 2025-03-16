package com.tutolist.api.auth.dto.request;

import com.tutolist.common.validation.annotation.ValidPassword;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
	@Email(message = "올바른 이메일 형식이 아닙니다.")
	@NotBlank(message = "이메일을 입력해주세요.")
	String email,

	@ValidPassword
	String password,

	@NotBlank(message = "비밀번호 확인을 입력해주세요.")
	String passwordCheck,

	@NotBlank(message = "이름을 입력해주세요.")
	@Size(min = 2, max = 20, message = "이름은 2자 이상 20자 이하로 입력해주세요.")
	String name
) {
	public RegisterRequest {
		if (email != null) {
			email = email.trim();
		}
		if (name != null) {
			name = name.trim();
		}
	}
}
