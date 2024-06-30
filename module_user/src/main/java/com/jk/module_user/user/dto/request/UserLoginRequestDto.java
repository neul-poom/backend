package com.jk.module_user.user.dto.request;

import lombok.Builder;

@Builder
public record UserLoginRequestDto(String username, String password) {
}