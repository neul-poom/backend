package com.jk.module_user.user.dto.response;

import com.jk.module_user.user.entity.User;
import lombok.Builder;

@Builder
public record UserUpdateResponseDto(String username, String email, String profileImg, String balance) {

    public static UserUpdateResponseDto toDto(User user){
        return UserUpdateResponseDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .profileImg(user.getProfileImg())
                .balance(user.getBalance())
                .build();
    }
}
