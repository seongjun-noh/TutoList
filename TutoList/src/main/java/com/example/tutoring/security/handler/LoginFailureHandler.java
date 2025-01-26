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
import com.example.tutoring.cmmn.error.enums.ErrorMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginFailureHandler implements AuthenticationFailureHandler {
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        log.warn("Authentication Failure - IP: {}, URL: {}, Exception: {}",
            request.getRemoteAddr(),
            request.getRequestURI(),
            exception.getMessage());

        HttpStatus status = HttpStatus.UNAUTHORIZED;
        String message = this.getErrorMessage(exception);
        ApiResponse responseBody = new ApiResponse<>(status.value(), message, null);

        response.setStatus(status.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(objectMapper.writeValueAsString(responseBody));
    }

    private static String getErrorMessage(AuthenticationException exception) {
        ErrorMessage message;
        if (exception instanceof AccountExpiredException) {
            message = ErrorMessage.ERROR_ACCOUNT_EXPIRED;
        } else if (exception instanceof BadCredentialsException) {
            message = ErrorMessage.ERROR_INVALID_USERNAME_OR_PASSWORD;
        } else if (exception instanceof LockedException) {
            message = ErrorMessage.ERROR_ACCOUNT_LOCKED;
        } else if (exception instanceof DisabledException) {
            message = ErrorMessage.ERROR_ACCOUNT_DISABLED;
        } else {
            message = ErrorMessage.ERROR_UNKNOWN;
        }
        return message.toString();
    }
}
