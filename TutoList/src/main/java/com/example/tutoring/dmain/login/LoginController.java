package com.example.tutoring.dmain.login;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tutoring.cmmn.dto.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LoginController {

	@GetMapping("/login/check")
	public ApiResponse<String> loginCheck() {
		return ApiResponse.isSuccess("OK");
	}
}
