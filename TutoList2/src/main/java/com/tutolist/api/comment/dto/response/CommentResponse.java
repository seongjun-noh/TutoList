package com.tutolist.api.comment.dto.response;

import java.time.LocalDateTime;

public record CommentResponse(
	Long id,
	String content,
	Long userId,
	String userName,
	Long postId,
	Long parentCommentId,
	LocalDateTime createdAt,
	LocalDateTime updatedAt
) {}
