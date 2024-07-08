package com.jk.module_user.user.dto.request;


import com.jk.module_user.user.entity.UserRoleEnum;
import lombok.Builder;

@Builder
public record UserUpdateRequestDto(
        String currentPassword,
        String username,
        String email,
        String profileImg,
        String balance
) {
}