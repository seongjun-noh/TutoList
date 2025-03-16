package com.tutolist.api.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutolist.api.auth.dto.request.LoginRequest;
import com.tutolist.api.auth.dto.request.LogoutRequest;
import com.tutolist.api.auth.dto.request.RegisterRequest;
import com.tutolist.api.auth.dto.request.TokenRefreshRequest;
import com.tutolist.api.auth.dto.response.UserInfoResponse;
import com.tutolist.common.dto.ApiResponse;
import com.tutolist.security.details.PrincipalDetails;
import com.tutolist.security.jwt.JwtDto;
import com.tutolist.service.auth.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "인증 관련 API")
public class AuthController {
	private final AuthService authService;

	@Operation(summary = "상태 확인", description = "서버 상태를 확인합니다.")
	@GetMapping("/status")
	public ResponseEntity<ApiResponse<String>> checkStatus() {
		return ResponseEntity.ok(ApiResponse.success("Server is running", null));
	}

	@Operation(summary = "회원가입", description = "새로운 사용자를 등록합니다.")
	@PostMapping("/register")
	public ResponseEntity<ApiResponse<Void>> register(@Valid @RequestBody RegisterRequest request) {
		log.info("Registering new user with email: {}", request.email());
		authService.register(request);
		return ResponseEntity.ok(ApiResponse.success("회원가입이 완료되었습니다.", null));
	}

	@Operation(summary = "로그인", description = "사용자 인증 후 JWT 토큰을 발급합니다.")
	@PostMapping("/login")
	public ResponseEntity<ApiResponse<JwtDto>> login(@Valid @RequestBody LoginRequest request) {
		log.info("Login attempt for user: {}", request.email());
		JwtDto jwtDto = authService.login(request);
		return ResponseEntity.ok(ApiResponse.success("로그인이 완료되었습니다.", jwtDto));
	}

	@Operation(summary = "로그아웃", description = "사용자 로그아웃을 처리합니다.")
	@PostMapping("/logout")
	public ResponseEntity<ApiResponse<Void>> logout(
			@AuthenticationPrincipal PrincipalDetails principalDetails,
			@Valid @RequestBody LogoutRequest request,
			HttpServletRequest httpRequest) {
		
		String accessToken = extractAccessToken(httpRequest);
		String email = principalDetails.getEmail();
		
		log.info("Logging out user: {}", email);
		authService.logout(email, accessToken, request.getRefreshToken());
		
		return ResponseEntity.ok(ApiResponse.success("로그아웃이 완료되었습니다.", null));
	}

	@Operation(summary = "토큰 갱신", description = "Refresh 토큰을 사용하여 새로운 Access 토큰을 발급합니다.")
	@PostMapping("/refresh")
	public ResponseEntity<ApiResponse<JwtDto>> refresh(@Valid @RequestBody TokenRefreshRequest request) {
		log.info("Token refresh requested");
		JwtDto jwtDto = authService.refresh(request);
		return ResponseEntity.ok(ApiResponse.success("토큰이 갱신되었습니다.", jwtDto));
	}

	@Operation(summary = "사용자 정보 조회", description = "현재 로그인한 사용자의 정보를 조회합니다.")
	@GetMapping("/me")
	public ResponseEntity<ApiResponse<UserInfoResponse>> getCurrentUser(
			@AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		log.info("Fetching user info for user: {}", principalDetails.getEmail());
		UserInfoResponse userInfo = authService.getCurrentUser(principalDetails.getId());
		return ResponseEntity.ok(ApiResponse.success("사용자 정보를 조회했습니다.", userInfo));
	}

	private String extractAccessToken(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		if (header == null || !header.startsWith("Bearer ")) {
			throw new IllegalArgumentException("유효하지 않은 Authorization 헤더입니다.");
		}
		return header.substring(7);
	}
}
