package com.example.tutoring.dmain.lessons.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.tutoring.dmain.calendar.entity.CalendarEventEntity;
import com.example.tutoring.dmain.calendar.repository.CalendarEventRepository;
import com.example.tutoring.dmain.lessons.conttroller.RegistLessonDto;
import com.example.tutoring.dmain.lessons.dto.LessonDto;
import com.example.tutoring.dmain.lessons.entity.LessonEntity;
import com.example.tutoring.dmain.lessons.mapper.LessonMapper;
import com.example.tutoring.dmain.lessons.repository.LessonRepository;
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

	public LessonDto registLesson(Long teacherUserId , RegistLessonDto registLessonDto) {
		UserEntity teacherUser = userRepository.findById(teacherUserId)
			.orElseThrow(() -> new IllegalArgumentException("Invalid teacher user ID: " + teacherUserId));


		LessonEntity lessonEntity = LessonEntity.builder()
			.name(registLessonDto.getName())
			.subject(registLessonDto.getSubject())
			.memo(registLessonDto.getMemo())
			.teacherUser(teacherUser)
			.startDate(registLessonDto.getStartDateTime().toLocalDate())
			.startTime(registLessonDto.getStartDateTime().toLocalTime())
			.duration(registLessonDto.getDuration())
			.rrule(registLessonDto.getRrule())
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

		LessonDto savedLessonDto = lessonMapper.toDto(savedLesson);

		return savedLessonDto;
	}

	public List<LessonDto> getTeachersLessonList(Long teacherUserId) {
		List<LessonEntity> teachersLessonList = lessonRepository.findByTeacherUserId(teacherUserId);

		List<LessonDto> teachersLessonDtoList = teachersLessonList.stream().map(lessonMapper::toDto).toList();

		return teachersLessonDtoList;
	}

	public LessonDto getTeachersLessonById(Long teacherUserId, Long lessonId) {
		LessonEntity teachersLesson = lessonRepository.findByTeacherUserIdAndId(teacherUserId, lessonId)
			.orElseThrow(() -> new IllegalArgumentException("ERROR_LESSON_NOT_FOUND"));

		LessonDto teachersLessonDto = lessonMapper.toDto(teachersLesson);

		return teachersLessonDto;
	}
}
