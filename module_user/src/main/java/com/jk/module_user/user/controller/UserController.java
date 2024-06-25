package com.jk.module_user.user.controller;

import com.jk.module_user.user.dto.response.UserInfoDto;
import com.jk.module_user.user.dto.request.SignupRequestDto;
import com.jk.module_user.user.dto.request.UserPasswordRequestDto;
import com.jk.module_user.user.dto.request.UserUpdateRequestDto;
import com.jk.module_user.user.entity.User;
import com.jk.module_user.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원 가입 페이지 요청
    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    // 로그인 페이지 요청
    @GetMapping("/login-page")
    public String loginPage() {
        return "login";
    }

    //회원 가입
    // Body: { "username": String, "password": String, "email": String, "status": String, "profileImg": String (optional), "balance": String, "adminToken": String (optional) }
    @PostMapping("/signup")
    public String signup(@Valid SignupRequestDto requestDto, BindingResult bindingResult) {
        // Validation 예외처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (fieldErrors.size() > 0) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
            return "redirect:/api/v1/signup";
        }

        userService.signup(requestDto);

        return "redirect:/api/v1/login-page";
    }

    // 회원 탈퇴
    // Body: { "currentPassword": String }
    @PostMapping("/resign")
    public ResponseEntity<String> resign(@RequestBody UserPasswordRequestDto userPasswordRequestDto, @AuthenticationPrincipal User user) {
        try {
            userService.resign(user, userPasswordRequestDto);
            return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // 회원 정보 수정
    // Body: { "username": String, "email": String, "profileImg": String, "balance": String, "currentPassword": String }
    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody UserUpdateRequestDto userUpdateRequestDto, @AuthenticationPrincipal User user) {
        try {
            userService.update(user, userUpdateRequestDto);
            return ResponseEntity.ok("회원 정보가 수정되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // 비밀번호 수정
    // Body: { "currentPassword": String, "newPassword": String }
    @PutMapping("/password")
    public ResponseEntity<String> updatePassword(@RequestBody UserPasswordRequestDto updatePasswordRequestDto, @AuthenticationPrincipal User user) {
        try {
            userService.updatePassword(user, updatePasswordRequestDto);
            return ResponseEntity.ok("비밀번호가 수정되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // 회원 관련 정보 받기
    @GetMapping("/user-info")
    @ResponseBody
    public UserInfoDto getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        String username = userDetails.getUser().getUsername();
        UserRoleEnum role = userDetails.getUser().getRole();
        boolean isAdmin = (role == UserRoleEnum.ADMIN);

        return new UserInfoDto(username, isAdmin);
    }

}