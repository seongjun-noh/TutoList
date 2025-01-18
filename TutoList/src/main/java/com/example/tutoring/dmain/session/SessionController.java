package com.example.tutoring.dmain.session;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tutoring.cmmn.dto.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
public class SessionController {

	@GetMapping("/check-session")
	public ApiResponse<Boolean> checkSession(HttpServletRequest request) {
		HttpSession session = request.getSession(false); // 존재하는 세션만 가져옴, 없으면 null 반환

		boolean isLogin = false;
		if (session != null) {
			 isLogin = true;
		}

		return ApiResponse.isSuccess(isLogin);
	}
}
