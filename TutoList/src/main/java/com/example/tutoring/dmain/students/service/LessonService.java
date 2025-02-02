package com.example.tutoring.dmain.students.service;

import org.springframework.stereotype.Service;

import com.example.tutoring.dmain.calendar.entity.CalendarEventEntity;
import com.example.tutoring.dmain.calendar.repository.CalendarEventRepository;
import com.example.tutoring.dmain.students.conttroller.RegistLessonDto;
import com.example.tutoring.dmain.students.entity.LessonEntity;
import com.example.tutoring.dmain.students.mapper.LessonMapper;
import com.example.tutoring.dmain.students.repository.LessonRepository;
import com.example.tutoring.dmain.user.entity.UserEntity;
import com.example.tutoring.dmain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LessonService {
	private final UserRepository userRepository;
	private final LessonRepository lessonRepository;
	private final CalendarEventRepository calendarEventRepository;

	private final LessonMapper lessonMapper;

	public void registLesson(Long teacherUserId , RegistLessonDto registLessonDto) {
		UserEntity teacherUser = userRepository.findById(teacherUserId)
			.orElseThrow(() -> new IllegalArgumentException("Invalid teacher user ID: " + teacherUserId));


		LessonEntity lessonEntity = LessonEntity.builder()
			.name(registLessonDto.getName())
			.subject(registLessonDto.getSubject())
			.memo(registLessonDto.getMemo())
			.teacherUser(teacherUser)
			.build();

		LessonEntity savedLesson = lessonRepository.save(lessonEntity);

		CalendarEventEntity calendarEvent = CalendarEventEntity.builder()
			.title(registLessonDto.getName())
			.start(registLessonDto.getStartDateTime())
			.end(registLessonDto.getStartDateTime().plusMinutes(registLessonDto.getDuration()))
			.allDay(false)
			.rrule(registLessonDto.getRrule())
			.teacherUser(teacherUser)
			.lesson(savedLesson)
			.build();

		calendarEventRepository.save(calendarEvent);
	}
}
