package com.example.tutoring.dmain.user.dto;

import com.example.tutoring.cmmn.valid.annotation.ValidUsername;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UsernameExistsDto {
	@Getter
	public static class Request {
		@NotBlank
		@ValidUsername
		private String username;
	}

	@Getter
	@Builder
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor
	public static class Response {
		private boolean exists;
	}
}
