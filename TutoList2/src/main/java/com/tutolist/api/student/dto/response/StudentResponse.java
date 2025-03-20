package com.tutolist.api.student.dto.response;

import java.util.List;

public record StudentResponse(
    Long id,
    String name,
    String schoolGrade,
    String contact,
    String memo,
    Long userId,
	List<String	> subjects
) {} 