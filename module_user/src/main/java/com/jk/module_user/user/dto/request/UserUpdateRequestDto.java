package com.jk.module_user.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class UserUpdateRequestDto {
    private String username;
    private String email;
    private String profileImg;
    private String balance;
    private String currentPassword; // 비밀번호 확인을 위한 필드 추가
}
