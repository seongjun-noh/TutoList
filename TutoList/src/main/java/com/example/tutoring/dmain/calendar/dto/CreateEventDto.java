package com.example.tutoring.dmain.calendar.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class CreateEventDto {
	@Getter
	public static class Request {
		@NotBlank
		private String title;

		@NotNull
		private LocalDateTime start;

		private LocalDateTime end;

		private String description;

		@NotNull
		private Boolean allDay = false;

		@NotNull
		private Boolean repeat = false;

		@Setter
		private String rrule;
	}

	@Getter
	@Builder
	@AllArgsConstructor
	public static class Response {
		private CalendarEventDto calendarEvent;
	}
}
