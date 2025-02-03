package com.example.tutoring.dmain.lessons.conttroller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.tutoring.cmmn.dto.ApiResponse;
import com.example.tutoring.dmain.lessons.dto.LessonDto;
import com.example.tutoring.dmain.lessons.service.LessonService;
import com.example.tutoring.security.dto.PrincipalDetails;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LessonController {
	private final LessonService lessonService;

	@PostMapping("/lessons/regist")
	public ApiResponse<LessonDto> registLesson(@AuthenticationPrincipal PrincipalDetails principalDetails,
											@RequestBody @Valid RegistLessonDto registLessonDto) {
		Long teacherUserId = principalDetails.getUserId();
		LessonDto lessonDto = lessonService.registLesson(teacherUserId, registLessonDto);

		return ApiResponse.isSuccess(lessonDto);
	}

	@GetMapping("/lessons")
	public ApiResponse<List<LessonDto>> getLessonList(@AuthenticationPrincipal PrincipalDetails principalDetails) {
		Long teacherUserId = principalDetails.getUserId();
		List<LessonDto> teachersLessonList = lessonService.getTeachersLessonList(teacherUserId);

		return ApiResponse.isSuccess(teachersLessonList);
	}

	@GetMapping("/lessons/{lessonId}")
	public ApiResponse<LessonDto> getLessonById(@AuthenticationPrincipal PrincipalDetails principalDetails,
												@PathVariable(name = "lessonId") Long lessonId) {
		Long teacherUserId = principalDetails.getUserId();
		LessonDto teachersLesson = lessonService.getTeachersLessonById(teacherUserId, lessonId);

		return ApiResponse.isSuccess(teachersLesson);
	}
}
