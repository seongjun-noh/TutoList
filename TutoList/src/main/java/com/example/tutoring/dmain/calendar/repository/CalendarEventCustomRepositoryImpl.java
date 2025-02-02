package com.example.tutoring.dmain.calendar.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.example.tutoring.dmain.calendar.entity.CalendarEventEntity;
import com.example.tutoring.dmain.calendar.entity.QCalendarEventEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CalendarEventCustomRepositoryImpl implements CalendarEventCustomRepository {
	private final JPAQueryFactory queryFactory;

	@Override
	public List<CalendarEventEntity> findTeacherCalendarEventByCondition(Long teacherUserId, LocalDateTime startDate, LocalDateTime endDate) {
		QCalendarEventEntity qCalendarEvent = QCalendarEventEntity.calendarEventEntity;

		return queryFactory.selectFrom(qCalendarEvent)
			.where(
				qCalendarEvent.teacherUser.id.eq(teacherUserId)
					.and(qCalendarEvent.start.between(startDate, endDate))
			)
			.leftJoin(qCalendarEvent.teacherUser)
			.fetch();
	}

	@Override
	public List<CalendarEventEntity> findTeachersStudentCalendarEventByCondition(Long teacherUserId, Long studentId, LocalDateTime startDate, LocalDateTime endDate) {
		QCalendarEventEntity qCalendarEvent = QCalendarEventEntity.calendarEventEntity;

		return queryFactory.selectFrom(qCalendarEvent)
			.where(
				qCalendarEvent.teacherUser.id.eq(teacherUserId)
					.and(qCalendarEvent.start.between(startDate, endDate))
			)
			.leftJoin(qCalendarEvent.teacherUser)
			.fetch();
	}
}
