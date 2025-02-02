package com.example.tutoring.dmain.students.conttroller;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class RegistLessonDto {
	private String name;
	private String subject;
	private LocalDateTime startDateTime;
	private Integer duration;
	private String rrule;
	private String memo;
}
