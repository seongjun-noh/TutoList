package com.example.tutoring.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class LoginDto {
	@Getter
	public static class Request {
		private String username;
		private String password;
	}

	@Getter
	@Builder
	@AllArgsConstructor
	public static class Response {

	}
}
