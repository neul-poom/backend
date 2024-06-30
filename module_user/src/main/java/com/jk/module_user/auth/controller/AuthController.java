package com.jk.module_user.auth.controller;

import com.jk.module_user.user.dto.request.UserLoginRequestDto;
import com.jk.module_user.user.dto.response.UserLoginResponseDto;
import com.jk.module_user.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/v1/users/login")
    public ResponseEntity<Void> login(@RequestBody UserLoginRequestDto loginRequest, HttpServletResponse response) {
        UserLoginResponseDto loginResponse = userService.authenticateUser(loginRequest);
        response.addHeader("Authorization", "Bearer " + loginResponse.token());
        return ResponseEntity.ok().build();
    }
}