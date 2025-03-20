package com.tutolist.api.student.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record StudentCreateRequest(
    @NotBlank(message = "이름은 필수입니다.")
    String name,
    
    @NotNull(message = "학교, 학년은 필수입니다.")
    String schoolGrade,
    
    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "올바른 전화번호 형식이 아닙니다.")
    String contact,
    
    String memo,
    
    Long userId,  // 기존 회원인 경우 userId 입력

    List<String> subjects
) {} 