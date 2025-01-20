package com.example.tutoring.dmain.user.dto;

import com.example.tutoring.cmmn.valid.annotation.ValidEnum;
import com.example.tutoring.cmmn.valid.annotation.ValidPassword;
import com.example.tutoring.cmmn.valid.annotation.ValidUsername;
import com.example.tutoring.dmain.user.enums.UserRole;

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

		@ValidEnum(enumClass = UserRole.class)
		private UserRole role;
	}
}
