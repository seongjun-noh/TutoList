package com.example.tutoring.security.handler;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.example.tutoring.cmmn.dto.ApiResponse;
import com.example.tutoring.cmmn.error.enums.ErrorMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException, ServletException {
        log.warn("Access is Denied - IP: {}, URL: {}, Exception: {}",
            request.getRemoteAddr(),
            request.getRequestURI(),
            exception.getMessage());

        HttpStatus status = HttpStatus.FORBIDDEN;
        String message = ErrorMessage.ERROR_FORBIDDEN.getDescription();
        ApiResponse responseBody = new ApiResponse<>(status.value(), message, null);

        response.setStatus(status.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(objectMapper.writeValueAsString(responseBody));
    }
}