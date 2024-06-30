package com.jk.module_api_gateway.service;

import com.jk.module_api_gateway.client.UserClient;
import com.jk.module_api_gateway.client.dto.InternalUserIdResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User 모듈의 토큰 유효성 검사
 */
@Service
public class UserValidationService {

    private final UserClient userClient;

    @Autowired
    public UserValidationService(UserClient userClient) {
        this.userClient = userClient;
    }

    public InternalUserIdResponseDto validateToken(String token) {
        return userClient.validateToken(token);
    }
}