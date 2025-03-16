package com.tutolist.api.post.dto.response;

import java.time.LocalDateTime;

public record PostResponse(
	Long id,
	String title,
	String content,
	Long userId,
	String userName,
	Integer viewCount,
	Boolean hasFiles,
	LocalDateTime createdAt,
	LocalDateTime updatedAt
) {}
