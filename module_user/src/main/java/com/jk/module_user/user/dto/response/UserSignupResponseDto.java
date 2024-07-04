package com.jk.module_user.user.dto.response;

import com.jk.module_user.user.entity.User;
import com.jk.module_user.user.entity.UserRoleEnum;
import lombok.Builder;

@Builder
public record UserSignupResponseDto(
        String username,
        String password,
        String email,
        String profileImg,
        String balance
) {
    public static UserSignupResponseDto toDto(User user){
        return UserSignupResponseDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .profileImg(user.getProfileImg())
                .balance(user.getBalance())
                .build();
    }
}
