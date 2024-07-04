package com.jk.module_user.auth.controller;

import com.jk.module_user.auth.dto.UserLoginRequestDto;
import com.jk.module_user.auth.jwt.JwtTokenDto;
import com.jk.module_user.auth.service.UserLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 사용자 로그인 컨트롤러
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserLoginController {

    private final UserLoginService userLoginService;

    /**
     * 로그인 요청을 처리
     * @param userLoginRequestDto 로그인 요청 DTO
     * @return JWT 토큰 DTO
     */
    @PostMapping("/login")
    public ResponseEntity<JwtTokenDto> login(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        JwtTokenDto jwtTokenDto = userLoginService.login(userLoginRequestDto);
        return ResponseEntity.ok(jwtTokenDto);
    }
}
