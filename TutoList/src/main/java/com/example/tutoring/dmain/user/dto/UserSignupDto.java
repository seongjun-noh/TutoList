package com.example.tutoring.dmain.user.dto;

import com.example.tutoring.cmmn.valid.annotation.ValidEnum;
import com.example.tutoring.cmmn.valid.annotation.ValidPassword;
import com.example.tutoring.cmmn.valid.annotation.ValidPhone;
import com.example.tutoring.cmmn.valid.annotation.ValidUsername;
import com.example.tutoring.dmain.user.enums.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class UserSignupDto {
	@Getter
	public static class Request {
		@NotBlank
		@ValidUsername
		private String username;

		@NotBlank
		@ValidPassword
		private String password;

		@NotBlank
		private String name;

		@ValidEnum(enumClass = UserRole.class)
		private UserRole role;

		@ValidPhone
		private String phone;

		@Email
		private String email;
	}
}
