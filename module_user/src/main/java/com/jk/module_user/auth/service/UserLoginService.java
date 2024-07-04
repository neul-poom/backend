package com.jk.module_user.auth.service;

import com.jk.module_user.auth.dto.UserLoginRequestDto;
import com.jk.module_user.auth.jwt.JwtTokenDto;
import com.jk.module_user.auth.jwt.JwtTokenProvider;
import com.jk.module_user.user.entity.User;
import com.jk.module_user.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserLoginService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    /**
     * 사용자 로그인 메서드
     *
     * @param userLoginRequestDto 사용자 로그인 요청 DTO
     * @return JwtTokenDto 액세스 토큰 및 리프레시 토큰 정보
     */
    public JwtTokenDto login(UserLoginRequestDto userLoginRequestDto) {

        String email = userLoginRequestDto.email();
        String password = userLoginRequestDto.password();

        // 이메일로 사용자 검색, 없으면 예외 발생
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("이메일 또는 비밀번호가 잘못되었습니다."));

        // 비밀번호 일치 여부 확인, 일치하지 않으면 예외 발생
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("이메일 또는 비밀번호가 잘못되었습니다.");
        }

        // 액세스 토큰 및 리프레시 토큰 생성
        String accessToken = jwtTokenProvider.generateAccessToken(email, user.getUserId());
        String refreshToken = jwtTokenProvider.generateRefreshToken(email, user.getUserId());

        // 리프레시 토큰의 만료 시간 계산
        Long expiresTime = jwtTokenProvider.getExpiredTime(refreshToken);

        // 토큰 정보를 담은 DTO 반환
        return new JwtTokenDto(accessToken, refreshToken, expiresTime);
    }
}
