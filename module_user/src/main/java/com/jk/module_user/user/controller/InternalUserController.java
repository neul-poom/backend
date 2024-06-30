package com.jk.module_user.controller;

import com.jk.module_user.user.controller.dto.response.InternalUserIdResponseDto;
import com.jk.module_user.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 내부 API를 위한 컨트롤러, Feign 클라이언트를 통해 호출
 */
@RestController
public class InternalUserController {

    private final UserService userService;

    @Autowired
    public InternalUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/internal/validateToken")
    public InternalUserIdResponseDto validateToken(@RequestParam String token) {
        return userService.validateToken(token);
    }
}