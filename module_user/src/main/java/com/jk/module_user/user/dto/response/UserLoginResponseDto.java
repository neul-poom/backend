package com.jk.module_user.user.dto.response;

import lombok.Builder;

@Builder
public record UserLoginResponseDto(String username, String token) {
}