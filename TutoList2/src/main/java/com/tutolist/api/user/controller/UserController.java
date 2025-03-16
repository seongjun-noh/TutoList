package com.tutolist.api.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tutolist.api.user.dto.request.UserUpdateRequest;
import com.tutolist.common.dto.ApiResponse;
import com.tutolist.security.details.PrincipalDetails;
import com.tutolist.service.user.UserService.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "User", description = "사용자 관련 API")
public class UserController {
	private final UserService userService;

	@Operation(summary = "사용자 정보 수정", description = "사용자의 정보를 수정합니다.")
	@PutMapping()
	public ResponseEntity<ApiResponse<Void>> updateUser(
			@AuthenticationPrincipal PrincipalDetails principalDetails,
			@Valid @RequestPart(name = "userData") UserUpdateRequest userData,
			@RequestPart(name = "profileImage", required = false) MultipartFile profileImage) {
		
		Long userId = principalDetails.getId();
		log.info("Updating user information for user: {}", userId);
		
		userService.updateUser(userId, userData, profileImage);
		return ResponseEntity.ok(ApiResponse.success("사용자 정보가 수정되었습니다.", null));
	}
}
