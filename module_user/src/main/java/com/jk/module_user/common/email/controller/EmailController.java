package com.jk.module_user.common.email.controller;

import com.jk.module_user.common.email.dto.request.EmailVerificationRequestDto;
import com.jk.module_user.common.email.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1/users")
public class EmailController {
    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/verification")
    public ResponseEntity<?> verifyEmail(@RequestBody EmailVerificationRequestDto verificationRequest) {
        log.info("이메일 인증 시작");
        String email = verificationRequest.email();
        emailService.verifyEmail(email);
        log.info("이메일 인증 끝");
        return ResponseEntity.ok().body("send email successful");
    }
}
