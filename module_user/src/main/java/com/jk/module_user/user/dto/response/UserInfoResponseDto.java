package com.jk.module_user.user.dto.response;

import com.jk.module_user.user.entity.User;
import com.jk.module_user.user.entity.UserRoleEnum;
import lombok.Builder;

@Builder
public record UserInfoResponseDto(
        String username,
        String email,
        UserRoleEnum role,
        String profileImg,
        String balance
) {
    public static UserInfoResponseDto toDto(User user) {
        return UserInfoResponseDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .profileImg(user.getProfileImg())
                .balance(user.getBalance())
                .build();
    }
}