package com.jk.module_user.user.dto.request;


import com.jk.module_user.user.entity.UserRoleEnum;
import lombok.Builder;

@Builder
public record UserUpdateRequestDto(
        String currentPassword,
        String username,
        String password,
        String email,
        UserRoleEnum role,
        String profileImg,
        String balance
) {
}