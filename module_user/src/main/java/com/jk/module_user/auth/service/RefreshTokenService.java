package com.jk.module_user.auth.service;

import com.jk.module_user.auth.jwt.JwtTokenProvider;
import com.jk.module_user.user.entity.User;
import com.jk.module_user.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
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
        String email = jwtTokenProvider.getEmail(refreshToken);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // accessToken 생성 및 반환
        return jwtTokenProvider.generateAccessToken(user.getEmail(), user.getUserId());
    }

}
