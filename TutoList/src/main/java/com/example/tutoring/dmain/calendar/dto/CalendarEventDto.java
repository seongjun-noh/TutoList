package com.example.tutoring.dmain.calendar.dto;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CalendarEventDto {
	private Long id;

	private String title;

	private LocalDateTime start;

	private LocalDateTime end;

	private String description;

	private boolean allDay = false;

	private String rrule;

	private Long userId;
}
