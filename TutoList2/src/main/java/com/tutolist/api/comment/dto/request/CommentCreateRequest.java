package com.tutolist.api.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CommentCreateRequest(
	@NotBlank(message = "내용을 입력해주세요.")
	@Size(max = 1000, message = "내용은 1000자를 초과할 수 없습니다.")
	String content,

	Long parentCommentId
) {
	public CommentCreateRequest {
		if (content != null) {
			content = content.trim();
		}
	}
}
