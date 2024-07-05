package com.jk.module_user.user.service;

import com.jk.module_user.common.redis.service.RedisService;
import com.jk.module_user.user.dto.request.UserPasswordRequestDto;
import com.jk.module_user.user.dto.request.UserSignupRequestDto;
import com.jk.module_user.user.dto.request.UserUpdateRequestDto;
import com.jk.module_user.user.dto.response.UserInfoResponseDto;
import com.jk.module_user.user.dto.response.UserSignupResponseDto;
import com.jk.module_user.user.dto.response.UserUpdateResponseDto;
import com.jk.module_user.user.entity.User;
import com.jk.module_user.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisService redisService;

    @Transactional
    public UserSignupResponseDto signup(UserSignupRequestDto userSignupRequestDto) {
        log.info("UserService - 회원가입 서비스 호출");

        String verificationCode = userSignupRequestDto.verificationCode();
        String userEmail = userSignupRequestDto.email();

        // -> 이메일 인증 시 이메일 중복 검사하도록 수정
//        Optional<User> checkEmail = userRepository.findByEmail(userSignupRequestDto.email());
//        if (checkEmail.isPresent()) {
//            throw new IllegalArgumentException("중복된 Email 입니다.");
//        }

        if (isVerify(userEmail, verificationCode)) {
            String encodedPassword = passwordEncoder.encode(userSignupRequestDto.password());
            //1. userRequest로 entity 생성
            User signupUser = User.builder()
                    .username(userSignupRequestDto.username())
                    .password(passwordEncoder.encode(userSignupRequestDto.password()))
                    .email(userSignupRequestDto.email())
                    .profileImg(userSignupRequestDto.profileImg())
                    .balance(userSignupRequestDto.balance() != null ? userSignupRequestDto.balance() : "0")
                    .build();

            //2. 생성된 entity db에 저장
            User createdUser = userRepository.save(signupUser);

            redisService.deleteValue(userEmail);

            return UserSignupResponseDto.toDto(createdUser);
        } else {
            throw new IllegalArgumentException("인증코드 불일치");
        }

    }

    public boolean isVerify(String userEmail, String requestCode) {
        return redisService.compareValue(userEmail, requestCode);
    }

    @Transactional
    public UserUpdateResponseDto update(Long userId, UserUpdateRequestDto userUpdateRequestDto) {
        log.info("UserService - 회원정보 수정 서비스 호출");
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(userUpdateRequestDto.currentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }

        user.updateUserInfo(
                userUpdateRequestDto.username(),
                userUpdateRequestDto.email(),
                userUpdateRequestDto.profileImg(),
                userUpdateRequestDto.balance()
        );

        if (userUpdateRequestDto.password() != null && !userUpdateRequestDto.password().isEmpty()) {
            user.updatePassword(passwordEncoder.encode(userUpdateRequestDto.password()));
        }

        User savedUser = userRepository.save(user);
        return UserUpdateResponseDto.toDto(savedUser);
    }

    @Transactional
    public void updatePassword(Long userId, UserPasswordRequestDto userPasswordRequestDto) {
        log.info("UserService - 비밀번호 변경 서비스 호출");
        User user = userRepository.findById(userId)
                .orElseThrow(()->new IllegalArgumentException("유저를 찾을 수 없습니다."));
        if (!passwordEncoder.matches(userPasswordRequestDto.currentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }

        user.updatePassword(passwordEncoder.encode(userPasswordRequestDto.newPassword()));
        userRepository.save(user);

    }

    @Transactional
    public void resign(Long userId, UserPasswordRequestDto userPasswordRequestDto) {
        log.info("UserService - 회원탈퇴 서비스 호출");
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(userPasswordRequestDto.currentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }

        user.deactivate();
        userRepository.save(user);
        log.info("UserService - 회원탈퇴 처리 완료: {}", userId);
    }

    @Transactional(readOnly = true)
    public UserInfoResponseDto getUserInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
        return UserInfoResponseDto.toDto(user);
    }
}
