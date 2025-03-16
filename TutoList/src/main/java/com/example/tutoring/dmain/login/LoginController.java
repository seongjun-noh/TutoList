package com.example.tutoring.dmain.login;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tutoring.cmmn.dto.ApiResponse;
import com.example.tutoring.security.dto.PrincipalDetails;
import com.example.tutoring.security.dto.response.ResponseLoginDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LoginController {

	@GetMapping("/login/check")
	public ApiResponse<ResponseLoginDto> loginCheck(@AuthenticationPrincipal PrincipalDetails principalDetails) {
		ResponseLoginDto responseLoginDto = ResponseLoginDto.builder()
			.id(principalDetails.getUserId())
			.username(principalDetails.getUsername())
			.name(principalDetails.getName())
			.build();
		return ApiResponse.isSuccess(responseLoginDto);
	}
}
