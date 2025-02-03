package com.example.tutoring.dmain.lessons.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class LessonDto {
	private Long id;
	private String name;
	private String teacherUserId;
	private String subject;
	private String memo;
	private LocalDate startDate;
	private LocalTime startTime;
	private Integer duration;
	private String rrule;
}
