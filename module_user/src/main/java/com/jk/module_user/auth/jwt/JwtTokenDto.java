package com.jk.module_user.auth.jwt;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record JwtTokenDto(
        String accessToken,
        String refreshToken,
        Long refreshTokenExpiresTime
) {

}
