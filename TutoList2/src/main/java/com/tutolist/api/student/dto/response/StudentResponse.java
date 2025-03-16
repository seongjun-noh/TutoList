package com.tutolist.api.student.dto.response;

public record StudentResponse(
    Long id,
    String name,
    String schoolGrade,
    String contact,
    String memo,
    Long userId
) {} 