package com.jk.module_user.auth.service;

import com.jk.module_user.auth.jwt.JwtTokenProvider;
import com.jk.module_user.common.exception.CustomException;
import com.jk.module_user.common.exception.ErrorCode;
import com.jk.module_user.user.entity.User;
import com.jk.module_user.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    /**
     * accessToken 만료 시 refreshToken이 있다면 재발급해주는 메서드
     */
    @Transactional
    public String refresh(HttpServletRequest request) {
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);
        if (refreshToken == null || !jwtTokenProvider.validateToken(refreshToken)) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        String email = jwtTokenProvider.getEmail(refreshToken);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // accessToken 생성 및 반환
        return jwtTokenProvider.generateAccessToken(user.getEmail(), user.getUserId());
    }
}
