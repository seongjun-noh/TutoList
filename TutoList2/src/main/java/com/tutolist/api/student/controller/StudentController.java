package com.tutolist.api.student.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tutolist.api.student.dto.request.StudentCreateRequest;
import com.tutolist.api.student.dto.response.StudentResponse;
import com.tutolist.common.dto.ApiResponse;
import com.tutolist.security.details.PrincipalDetails;
import com.tutolist.service.student.StudentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/students")
@Tag(name = "Student", description = "학생 관리 API")
public class StudentController {
    
    private final StudentService studentService;
    
    @PostMapping("/create")
    @Operation(summary = "학생 등록", description = "새로운 학생을 등록합니다.")
    public ResponseEntity<ApiResponse<StudentResponse>> createStudent(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @Valid @RequestBody StudentCreateRequest request
    ) {
        log.info("Creating student with name: {}", request.name());

        Long userId = principalDetails.getId();
        StudentResponse response = studentService.createStudent(userId, request);
        
        return ResponseEntity.ok(ApiResponse.success(
            "학생이 성공적으로 등록되었습니다.",
            response
        ));
    }

    @GetMapping
    @Operation(summary = "학생 목록 조회", description = "등록된 학생 목록을 페이징하여 조회합니다.")
    public ResponseEntity<ApiResponse<Page<StudentResponse>>> getStudents(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @Parameter(description = "페이지 번호 (0부터 시작)") @RequestParam(defaultValue = "0") int page,
        @Parameter(description = "페이지 크기") @RequestParam(defaultValue = "10") int size,
        @Parameter(description = "정렬 기준 (name, grade)") @RequestParam(defaultValue = "name") String sort,
        @Parameter(description = "정렬 방향 (asc, desc)") @RequestParam(defaultValue = "asc") String direction
    ) {
        log.info("Retrieving student list for user: {}, page: {}, size: {}", principalDetails.getId(), page, size);
        
        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));
        
        Page<StudentResponse> response = studentService.getStudents(principalDetails.getId(), pageable);
        
        return ResponseEntity.ok(ApiResponse.success(
            "학생 목록을 성공적으로 조회했습니다.",
            response
        ));
    }
} 