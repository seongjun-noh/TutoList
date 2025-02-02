package com.example.tutoring.dmain.calendar.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.example.tutoring.dmain.calendar.entity.CalendarEventEntity;

public interface CalendarEventCustomRepository {
	List<CalendarEventEntity> findTeacherCalendarEventByCondition(Long teacherUserId, LocalDateTime startDate, LocalDateTime endDate);

	List<CalendarEventEntity> findTeachersStudentCalendarEventByCondition(Long teacherUserId, Long studentId, LocalDateTime startDate, LocalDateTime endDate);
}
