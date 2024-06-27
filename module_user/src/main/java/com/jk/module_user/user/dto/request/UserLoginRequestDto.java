package com.jk.module_user.user.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserLoginRequestDto {
    private String username;
    private String password;
}