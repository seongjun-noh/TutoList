package com.example.tutoring.security.handler;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.tutoring.cmmn.dto.ApiResponse;
import com.example.tutoring.security.dto.PrincipalDetails;
import com.example.tutoring.security.dto.response.ResponseLoginDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        SecurityContextHolder.getContext().setAuthentication(authentication); // SecurityContext에 인증 정보 저장
        request.getSession().setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext()); // 세션에 저장

        HttpStatus status = HttpStatus.OK;

        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        ResponseLoginDto responseLoginDto = ResponseLoginDto.builder()
            .id(principalDetails.getUserId())
            .username(principalDetails.getUsername())
            .name(principalDetails.getName())
            .build();
        ApiResponse responseBody = ApiResponse.isSuccess(responseLoginDto);

        response.setStatus(status.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(objectMapper.writeValueAsString(responseBody));
    }
}