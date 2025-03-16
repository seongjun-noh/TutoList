package com.tutolist.api.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserUpdateRequest(
	@NotBlank(message = "이름을 입력해주세요.")
	@Size(min = 2, max = 20, message = "이름은 2자 이상 20자 이하로 입력해주세요.")
	String name
) {
	public UserUpdateRequest {
		if (name != null) {
			name = name.trim();
		}
	}
}
