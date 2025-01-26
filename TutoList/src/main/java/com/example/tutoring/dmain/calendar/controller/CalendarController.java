package com.example.tutoring.dmain.calendar.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.tutoring.cmmn.dto.ApiResponse;
import com.example.tutoring.dmain.calendar.dto.CalendarEventListDto;
import com.example.tutoring.dmain.calendar.dto.CreateEventDto;
import com.example.tutoring.dmain.calendar.service.CalendarEventService;
import com.example.tutoring.security.dto.PrincipalDetails;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CalendarController {
	private final CalendarEventService calendarEventService;

	@PostMapping("/calendar/event/create")
	public ApiResponse<CreateEventDto.Response> createEvent(@RequestBody @Valid CreateEventDto.Request requestBody,
															@AuthenticationPrincipal PrincipalDetails principalDetails) {
		long userId = principalDetails.getUserId();
		CreateEventDto.Response responseBody = calendarEventService.createEvent(userId, requestBody);

		return ApiResponse.isSuccess(responseBody);
	}

	@GetMapping("/calendar/event")
	public ApiResponse<CalendarEventListDto.Response> getCalendarEventList(@ModelAttribute @Valid CalendarEventListDto.Request requestBody,
																		   @AuthenticationPrincipal PrincipalDetails principalDetails) {
		long userId = principalDetails.getUserId();
		CalendarEventListDto.Response responseBody = calendarEventService.getUserCalendarEventList(userId, requestBody);

		return ApiResponse.isSuccess(responseBody);
	}
}
