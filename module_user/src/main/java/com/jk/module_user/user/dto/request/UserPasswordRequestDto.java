package com.jk.module_user.user.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPasswordRequestDto {
    private String currentPassword;
    private String newPassword;
}
