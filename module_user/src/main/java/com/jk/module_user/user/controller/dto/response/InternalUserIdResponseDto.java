package com.jk.module_user.user.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record InternalUserIdResponseDto(boolean valid, Long userId) {
}