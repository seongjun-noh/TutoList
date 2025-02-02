package com.example.tutoring.dmain.students.conttroller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.tutoring.cmmn.dto.ApiResponse;
import com.example.tutoring.dmain.students.service.LessonService;
import com.example.tutoring.security.dto.PrincipalDetails;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LessonController {
	private final LessonService lessonService;

	@PostMapping("/lessons/regist")
	public ApiResponse<Object> registLesson(@AuthenticationPrincipal PrincipalDetails principalDetails,
											@RequestBody @Valid RegistLessonDto registLessonDto) {
		Long teacherUserId = principalDetails.getUserId();
		lessonService.registLesson(teacherUserId, registLessonDto);

		return ApiResponse.isSuccess(null);
	}
}
