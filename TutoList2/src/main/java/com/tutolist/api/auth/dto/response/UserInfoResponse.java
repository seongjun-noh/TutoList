package com.tutolist.api.auth.dto.response;

import com.tutolist.common.enums.UserRole;

public record UserInfoResponse(
    Long id,
    String email,
    String name,
    UserRole role,
    String profileImageUrl
) {} 