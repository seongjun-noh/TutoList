package com.tutolist.api.post.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostUpdateRequest(
	@NotBlank(message = "제목을 입력해주세요.")
	@Size(max = 100, message = "제목은 100자를 초과할 수 없습니다.")
	String title,

	@NotBlank(message = "내용을 입력해주세요.")
	@Size(max = 10000, message = "내용은 10000자를 초과할 수 없습니다.")
	String content,

	List<String> deletedFileNames
) {
	public PostUpdateRequest {
		if (title != null) {
			title = title.trim();
		}
		if (content != null) {
			content = content.trim();
		}
	}
}
