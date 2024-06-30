package com.jk.module_user.auth.service;

import com.jk.module_user.auth.jwt.JwtUtil;
import com.jk.module_user.auth.dto.UserValidationResponse;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * JWT 토큰 유효성 검사
 */
@Service
public class JwtValidationService {

    private final JwtUtil jwtUtil;

    @Autowired
    public JwtValidationService(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    public UserValidationResponse validateToken(String token) {
        UserValidationResponse response = new UserValidationResponse();
        if (!jwtUtil.validateToken(token)) {
            response.setValid(false);
            return response;
        }

        Claims info = jwtUtil.getUserInfoFromToken(token);
        response.setValid(true);
        response.setUserId(info.getSubject());
        return response;
    }
}