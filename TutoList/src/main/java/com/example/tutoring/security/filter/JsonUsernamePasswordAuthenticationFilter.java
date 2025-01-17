package com.example.tutoring.security.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JsonUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	@Value("${auth.login.url}")
	private static String loginUrl;

	@Value("${auth.login.username}")
	private static String usernameKey;

	@Value("${auth.login.password}")
	private static String passwordKey;

	private final ObjectMapper objectMapper;

	private static final String HTTP_METHOD = "POST";    				// HTTP 메서드의 방식은 POST 이다.
	private static final String CONTENT_TYPE = "application/json";		// json 타입의 데이터로만 로그인을 진행한다.

	private static final AntPathRequestMatcher DEFAULT_LOGIN_PATH_REQUEST_MATCHER =
			new AntPathRequestMatcher(loginUrl, HTTP_METHOD); 			// /login 의 요청에, POST로 온 요청에 매칭

	public JsonUsernamePasswordAuthenticationFilter(ObjectMapper objectMapper) {

		super(DEFAULT_LOGIN_PATH_REQUEST_MATCHER);   // 위에서 설정한  /oauth2/login/* 의 요청에, GET으로 온 요청을 처리하기 위해 설정

		this.objectMapper = objectMapper;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
		if(request.getContentType() == null || !request.getContentType().equals(CONTENT_TYPE)  ) {
			throw new AuthenticationServiceException("Authentication Content-Type not supported: " + request.getContentType());
		}

		String messageBody = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);

		Map<String, String> usernamePasswordMap = objectMapper.readValue(messageBody, Map.class);

		String username = usernamePasswordMap.get(usernameKey);
		String password = usernamePasswordMap.get(passwordKey);

		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);//principal 과 credentials 전달

		return this.getAuthenticationManager().authenticate(authRequest);
	}
}