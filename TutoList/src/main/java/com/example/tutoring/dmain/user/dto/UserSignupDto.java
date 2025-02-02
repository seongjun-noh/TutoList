package com.example.tutoring.dmain.user.dto;

import com.example.tutoring.cmmn.valid.annotation.ValidPassword;
import com.example.tutoring.cmmn.valid.annotation.ValidUsername;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class UserSignupDto {
	@Getter
	public static class Request {
		@NotBlank(message = "ERROR_USERNAME_IS_REQUIRED")
		@ValidUsername
		private String username;

		@NotBlank(message = "ERROR_PASSWORD_IS_REQUIRED")
		@ValidPassword
		private String password;

		@NotBlank(message = "ERROR_NAME_IS_REQUIRED")
		private String name;

		@Email(message = "ERROR_INVALID_EMAIL")
		@NotBlank(message = "ERROR_EMAIL_IS_REQUIRED")
		private String email;

		// @ValidEnum(enumClass = UserRole.class)
		// private UserRole role;
	}
}
