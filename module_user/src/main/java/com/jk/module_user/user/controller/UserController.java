package com.jk.module_user.user.controller;

import com.jk.module_user.common.dto.ApiResponseDto;
import com.jk.module_user.user.dto.request.UserPasswordRequestDto;
import com.jk.module_user.user.dto.request.UserSignupRequestDto;
import com.jk.module_user.user.dto.request.UserUpdateRequestDto;
import com.jk.module_user.user.dto.response.UserInfoResponseDto;
import com.jk.module_user.user.dto.response.UserSignupResponseDto;
import com.jk.module_user.user.dto.response.UserUpdateResponseDto;
import com.jk.module_user.user.entity.User;
import com.jk.module_user.auth.security.UserDetailsImpl;
import com.jk.module_user.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDto<UserSignupResponseDto>> signup(
            @Valid @RequestBody UserSignupRequestDto userSignupRequestDto
    ) {
        log.info("UserController - 회원가입 요청: {}", userSignupRequestDto);
        UserSignupResponseDto userSignupResponseDto = userService.signup(userSignupRequestDto);
        return ResponseEntity.created(URI.create("/api/v1/users/signup"))
                .body(new ApiResponseDto<>(HttpStatus.CREATED, "회원 가입 성공", userSignupResponseDto));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponseDto<UserUpdateResponseDto>> update(
            @RequestHeader(value = "X-USER-ID") Long userId,
            @Valid @RequestBody UserUpdateRequestDto userUpdateRequestDto
    ) {
        log.info("UserController - 회원정보 수정 요청: {}", userUpdateRequestDto);
        UserUpdateResponseDto userUpdateResponseDto = userService.update(userId, userUpdateRequestDto);
        return ResponseEntity.ok(new ApiResponseDto<>(HttpStatus.OK, "회원 정보 수정 성공", userUpdateResponseDto));
    }

    @PostMapping("/password")
    public ResponseEntity<ApiResponseDto<Void>> updatePassword(
            @RequestHeader(value = "X-USER-ID") Long userId,
            @Valid @RequestBody UserPasswordRequestDto userPasswordRequestDto
    ) {
        log.info("UserController - 비밀번호 변경 요청: {}", userPasswordRequestDto);
        userService.updatePassword(userId, userPasswordRequestDto);
        return ResponseEntity.ok(new ApiResponseDto<>(HttpStatus.OK, "비밀번호 변경 성공", null));
    }

    @DeleteMapping("/resign")
    public ResponseEntity<ApiResponseDto<Void>> resign(
            @RequestHeader(value = "X-USER-ID") Long userId,
            @Valid @RequestBody UserPasswordRequestDto userPasswordRequestDto
    ) {
        log.info("UserController - 회원탈퇴 요청: {}", userPasswordRequestDto);
        userService.resign(userId, userPasswordRequestDto);
        return ResponseEntity.ok(new ApiResponseDto<>(HttpStatus.OK, "회원 탈퇴 성공", null));
    }

    @GetMapping("/user-info")
    public ResponseEntity<ApiResponseDto<UserInfoResponseDto>> getUserInfo(
            @RequestHeader(value = "X-USER-ID") Long userId
    ) {
        log.info("UserController - 회원정보 조회 요청");
        UserInfoResponseDto userInfoResponseDto = userService.getUserInfo(userId);
        return ResponseEntity.ok().body(new ApiResponseDto<>(HttpStatus.OK, "회원 정보 조회 성공", userInfoResponseDto));
    }
}
