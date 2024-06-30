package com.jk.module_api_gateway.client.dto;

import lombok.Builder;

@Builder
public record InternalUserIdResponseDto(
        boolean valid, // 토큰의 유효성 여부
        String userId // 유효한 경우 반환되는 사용자 ID
) {
}