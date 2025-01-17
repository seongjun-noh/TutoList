package com.example.tutoring.dmain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.tutoring.cmmn.dto.ApiResponse;
import com.example.tutoring.dmain.user.dto.UserSignupDto;
import com.example.tutoring.dmain.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@PostMapping("/user/signup")
	public ResponseEntity<ApiResponse> userSignup(@RequestBody @Valid UserSignupDto.Request requestBody) {

		userService.signup(requestBody);

		return null;
	}
}
