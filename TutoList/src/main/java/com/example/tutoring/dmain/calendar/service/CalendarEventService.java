package com.example.tutoring.dmain.calendar.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tutoring.cmmn.error.enums.ErrorMessage;
import com.example.tutoring.cmmn.error.exception.CustomException;
import com.example.tutoring.dmain.calendar.dto.CalendarEventDto;
import com.example.tutoring.dmain.calendar.dto.CalendarEventListDto;
import com.example.tutoring.dmain.calendar.dto.CreateEventDto;
import com.example.tutoring.dmain.calendar.entity.CalendarEventEntity;
import com.example.tutoring.dmain.calendar.mapper.CalendarEventMapper;
import com.example.tutoring.dmain.calendar.repository.CalendarEventRepository;
import com.example.tutoring.dmain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CalendarEventService {
	private final CalendarEventRepository calendarEventRepository;
	private final UserRepository userRepository;

	private final CalendarEventMapper calendarEventMapper;

	@Transactional
	public CreateEventDto.Response createEvent(long teacherId, CreateEventDto.Request requestBody) {

		if (requestBody.getStart().isAfter(requestBody.getEnd())) {
			throw new CustomException(ErrorMessage.ERROR_INVALID_DATE_RANGE);
		}

		if (requestBody.getRepeat() && requestBody.getRrule().isBlank()) {
			throw new CustomException(ErrorMessage.ERROR_INVALID_RRULE);
		} else {
			requestBody.setRrule(null);
		}

		CalendarEventEntity calendarEvent = calendarEventMapper.toEntity(teacherId, requestBody);

		CalendarEventEntity savedCalendarEvent = calendarEventRepository.save(calendarEvent);

		CalendarEventDto calendarEventDto = calendarEventMapper.toDto(savedCalendarEvent);

		return new CreateEventDto.Response(calendarEventDto);
	}

	public CalendarEventListDto.Response getUserCalendarEventList(long userId, CalendarEventListDto.Request requestBody) {

		List<CalendarEventEntity> calendarEventList = calendarEventRepository.findTeacherCalendarEventByCondition(userId, requestBody.getStartDate(), requestBody.getEndDate());

		List<CalendarEventDto> calendarEventDtoList = calendarEventList.stream().map(calendarEventMapper::toDto).toList();

		return new CalendarEventListDto.Response(calendarEventDtoList);
	}
}
