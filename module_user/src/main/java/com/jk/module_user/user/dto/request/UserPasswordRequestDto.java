package com.jk.module_user.user.dto.request;

import lombok.Builder;

@Builder
public record UserPasswordRequestDto(
        String currentPassword,
        String newPassword
) {
}
