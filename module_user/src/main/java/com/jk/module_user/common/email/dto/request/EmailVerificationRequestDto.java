package com.jk.module_user.common.email.dto.request;

import lombok.Builder;

@Builder
public record EmailVerificationRequestDto(String email) {
}
