package com.jk.module_user.user.controller;

import com.jk.module_user.common.dto.ApiResponseDto;
import com.jk.module_user.user.dto.response.UserInfoResponseDto;
import com.jk.module_user.user.dto.request.UserSignupRequestDto;
import com.jk.module_user.user.dto.request.UserPasswordRequestDto;
import com.jk.module_user.user.dto.request.UserUpdateRequestDto;
import com.jk.module_user.user.dto.response.UserSignupResponseDto;
import com.jk.module_user.user.dto.response.UserUpdateResponseDto;
import com.jk.module_user.user.entity.User;
import com.jk.module_user.user.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.jk.module_user.user.entity.UserRoleEnum;
import com.jk.module_user.user.security.UserDetailsImpl;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.net.URI;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원 가입
    // Body: { "username": String, "password": String, "email": String, "status": String, "profileImg": String (optional), "balance": String (optional), "adminToken": String (optional) }
    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDto<UserSignupResponseDto>> signup(@Valid @RequestBody UserSignupRequestDto requestDto, BindingResult bindingResult) {
        // Validation 예외처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (fieldErrors.size() > 0) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
        }

        UserSignupResponseDto responseDto = userService.signup(requestDto);

        return ResponseEntity.created(URI.create("api/v1/users/signup"))
                .body(new ApiResponseDto<>(HttpStatus.CREATED, "회원가입성공", responseDto));

    }

    // 회원 정보 수정
    // Body: { "username": String, "email": String, "profileImg": String, "balance": String, "currentPassword": String }
    @PutMapping("/update")
    public ResponseEntity<ApiResponseDto<UserUpdateResponseDto>> update(@RequestBody UserUpdateRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        try {
            UserUpdateResponseDto responseDto = userService.update(user, requestDto);
            return ResponseEntity.ok()
                    .body(new ApiResponseDto<>(HttpStatus.OK, "회원정보수정성공", responseDto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponseDto<>(HttpStatus.BAD_REQUEST, "회원정보수정실패", null));
        }
    }

    // 회원 탈퇴
    // Body: { "currentPassword": String }
    @PostMapping("/resign")
    public ResponseEntity<ApiResponseDto<Void>> resign(@RequestBody UserPasswordRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            User user = userDetails.getUser();
            userService.resign(user, requestDto);
            return ResponseEntity.ok(new ApiResponseDto<>(HttpStatus.OK, "회원 탈퇴가 완료되었습니다.", null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponseDto<>(HttpStatus.BAD_REQUEST, e.getMessage(), null));
        }
    }

    // 비밀번호 수정
    // Body: { "currentPassword": String, "newPassword": String }
    @PutMapping("/password")
    public ResponseEntity<ApiResponseDto<Void>> updatePassword(@RequestBody UserPasswordRequestDto requestDto,
                                                               @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            User user = userDetails.getUser(); // UserDetailsImpl에서 User 객체 가져오기
            userService.updatePassword(user, requestDto);
            return ResponseEntity.ok(new ApiResponseDto<>(HttpStatus.OK, "비밀번호가 수정되었습니다.", null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponseDto<>(HttpStatus.BAD_REQUEST, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDto<>(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
        }
    }

    // 회원 관련 정보 받기
    @GetMapping("/user-info")
    public ResponseEntity<ApiResponseDto<UserInfoResponseDto>> getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            String username = userDetails.getUser().getUsername();
            UserRoleEnum role = userDetails.getUser().getRole();
            boolean isAdmin = (role == UserRoleEnum.ADMIN);

            UserInfoResponseDto responseDto = new UserInfoResponseDto(username, isAdmin);
            return ResponseEntity.ok(new ApiResponseDto<>(HttpStatus.OK, "회원정보조회성공", responseDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseDto<>(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
        }
    }

}