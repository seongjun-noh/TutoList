package com.example.tutoring.cmmn.xss;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

@Component
@WebFilter(urlPatterns = "/*")
public class XssProtectFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        XssRequestWrapper wrappedRequest = new XssRequestWrapper((HttpServletRequest) request);
        String body = IOUtils.toString(wrappedRequest.getReader());

        String contentType = request.getContentType();
        if (contentType != null && contentType.contains("application/json") && !StringUtils.isBlank(body)) {
            try {
                Map<String, Object> oldJsonObject = new ObjectMapper().readValue(body, HashMap.class);
                Map<String, Object> newJsonObject = new HashMap<>();
                oldJsonObject.forEach((key, value) ->
                    newJsonObject.put(key, XssUtils.charEscape(value.toString()))
                );
                byte[] newBody = new ObjectMapper()
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(newJsonObject)
                    .getBytes();
                wrappedRequest.resetInputStream(newBody);
            } catch (Exception e) {
                // JSON 파싱 오류 처리: 로그 출력 또는 예외 무시
                e.printStackTrace();
            }
        }

        chain.doFilter(wrappedRequest, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}