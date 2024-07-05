package com.jk.module_user.user.dto.request;

import com.jk.module_user.user.entity.UserRoleEnum;
import lombok.Builder;
//import jakarta.validation.constraints.NotNull;

@Builder
public record UserSignupRequestDto(
//        @NotNull
        String username,
//        @NotNull
        String password,
//        @NotNull
        String email,
        String profileImg,
        String balance,
        String verificationCode
) {
}
