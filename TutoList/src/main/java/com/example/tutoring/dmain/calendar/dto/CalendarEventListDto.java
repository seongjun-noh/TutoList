package com.example.tutoring.dmain.calendar.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class CalendarEventListDto {
	@Getter
	@Setter
	@NoArgsConstructor
	public static class Request {
		@NotNull
		private LocalDateTime startDate;
		@NotNull
		private LocalDateTime endDate;
	}

	@Getter
	@Builder
	@AllArgsConstructor
	public static class Response {
		List<CalendarEventDto> events;
	}
}
