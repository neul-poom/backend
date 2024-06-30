package com.jk.module_user.user.service;

import com.jk.module_user.auth.jwt.JwtUtil;
import com.jk.module_user.user.controller.dto.response.InternalUserIdResponseDto;
import com.jk.module_user.user.dto.request.UserLoginRequestDto;
import com.jk.module_user.user.dto.request.UserSignupRequestDto;
import com.jk.module_user.user.dto.request.UserPasswordRequestDto;
import com.jk.module_user.user.dto.request.UserUpdateRequestDto;
import com.jk.module_user.user.dto.response.UserLoginResponseDto;
import com.jk.module_user.user.dto.response.UserSignupResponseDto;
import com.jk.module_user.user.dto.response.UserUpdateResponseDto;
import com.jk.module_user.user.entity.User;
import com.jk.module_user.user.entity.UserRoleEnum;
import com.jk.module_user.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    // ADMIN_TOKEN
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    /**
     * 사용자 인증, JWT 토큰 생성
     *
     * @param loginRequest 로그인 요청 DTO
     * @return UserLoginResponseDto 로그인 응답 DTO
     */
    public UserLoginResponseDto authenticateUser(UserLoginRequestDto loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(),
                        loginRequest.password()
                )
        );

        String token = jwtUtil.generateToken(loginRequest.username());
        return new UserLoginResponseDto(loginRequest.username(), token);
    }

    /**
     * JWT 토큰 검증, 사용자 ID 반환
     *
     * @param token JWT 토큰
     * @return InternalUserIdResponseDto 토큰 검증 결과 및 사용자 ID
     */
    public InternalUserIdResponseDto validateToken(String token) {
        if (!jwtUtil.validateToken(token)) {
            return new InternalUserIdResponseDto(false, null);
        }

        Claims info = jwtUtil.getUserInfoFromToken(token);
        Long userId = Long.valueOf(info.getSubject());
        return new InternalUserIdResponseDto(true, userId);
    }

    // 회원 가입 로직
    public UserSignupResponseDto signup(UserSignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // email 중복확인
        String email = requestDto.getEmail();
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        // 프로필 이미지
        String profileImg = requestDto.getProfileImg();

        // 밸런스
        String balance = requestDto.getBalance();

        // 사용자 등록
        User user = new User(username, password, email, role, profileImg, balance);
        User savedUser = userRepository.save(user);

        return UserSignupResponseDto.toDto(savedUser);

    }

    // 회원 정보 수정 로직
    @Transactional
    public UserUpdateResponseDto update(User user, UserUpdateRequestDto requestDto) {
        // 비밀번호 확인
        if (!passwordEncoder.matches(requestDto.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }

        if (requestDto.getUsername() != null && !requestDto.getUsername().isEmpty()) {
            user.setUsername(requestDto.getUsername());
        }
        if (requestDto.getEmail() != null && !requestDto.getEmail().isEmpty()) {
            user.setEmail(requestDto.getEmail());
        }
        if (requestDto.getProfileImg() != null && !requestDto.getProfileImg().isEmpty()) {
            user.setProfileImg(requestDto.getProfileImg());
        }
        if (requestDto.getBalance() != null && !requestDto.getBalance().isEmpty()) {
            user.setBalance(requestDto.getBalance());
        }
        User savedUser = userRepository.save(user);
        return UserUpdateResponseDto.toDto(savedUser);
    }

    // 비밀번호 수정 로직
    @Transactional
    public void updatePassword(User user, UserPasswordRequestDto requestDto) {
        // 현재 비밀번호 확인
        if (!passwordEncoder.matches(requestDto.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }

        // 새로운 비밀번호 암호화 및 업데이트
        String encodedNewPassword = passwordEncoder.encode(requestDto.getNewPassword());
        user.setPassword(encodedNewPassword);
        userRepository.save(user); // 변경 사항 저장
    }

    // 회원 탈퇴 로직
    @Transactional
    public void resign(User user, UserPasswordRequestDto requestDto) {
        // 변경된 부분: status 필드를 boolean으로 변경하고 탈퇴 시 false로 설정
        if (passwordEncoder.matches(requestDto.getCurrentPassword(), user.getPassword())) {
            user.setStatus(false); // 비밀번호 일치 시, 회원 상태를 false로 변경
            userRepository.save(user); // 변경 사항 저장
        } else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }
}