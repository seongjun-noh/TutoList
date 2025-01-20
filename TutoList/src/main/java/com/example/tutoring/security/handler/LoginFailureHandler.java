package com.example.tutoring.security.handler;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.example.tutoring.cmmn.dto.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoginFailureHandler implements AuthenticationFailureHandler {
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        String message = exception.getMessage();
        if (exception instanceof AccountExpiredException) {
            message = "ERROR_ACCOUNT_EXPIRED";
        } else if (exception instanceof BadCredentialsException) {
            message = "ERROR_INVALID_USERNAME_OR_PASSWORD";
        } else if (exception instanceof LockedException) {
            message = "ERROR_ACCOUNT_LOCKED";
        } else if (exception instanceof DisabledException) {
            message = "ERROR_ACCOUNT_DISABLED";
        } else {
            message = "ERROR_UNKNOWN";
        }

        ApiResponse<Object> responseBody = ApiResponse.isError("ERROR_UNKNOWN");
        response.getWriter().write(objectMapper.writeValueAsString(responseBody));
    }
}
