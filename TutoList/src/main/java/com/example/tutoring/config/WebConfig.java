package com.example.tutoring.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
	@Override
	public void addFormatters(FormatterRegistry registry) {
		// ISO 포멧 날짜 String을 LocalDateTime 형식으로 변환
		registry.addConverter(String.class, LocalDateTime.class, source -> {
			try {
				return LocalDateTime.parse(source, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
			} catch (Exception e) {
				throw new IllegalArgumentException("Invalid date format: " + source);
			}
		});
	}
}
