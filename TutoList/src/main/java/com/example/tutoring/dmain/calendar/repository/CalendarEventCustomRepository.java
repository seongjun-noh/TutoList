package com.example.tutoring.dmain.calendar.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.example.tutoring.dmain.calendar.entity.CalendarEventEntity;

public interface CalendarEventCustomRepository {
	List<CalendarEventEntity> findUserCalendarEventByCondition(Long userId, LocalDateTime startDate, LocalDateTime endDate);
}
