package com.jk.module_user.user.service;

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

    @Transactional
    public UserSignupResponseDto signup(UserSignupRequestDto userSignupRequestDto) {
        log.info("UserService - 회원가입 서비스 호출");
        Optional<User> checkUsername = userRepository.findByUsername(userSignupRequestDto.username());
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        Optional<User> checkEmail = userRepository.findByEmail(userSignupRequestDto.email());
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }

        User signupUser = User.builder()
                .username(userSignupRequestDto.username())
                .password(passwordEncoder.encode(userSignupRequestDto.password()))
                .email(userSignupRequestDto.email())
                .profileImg(userSignupRequestDto.profileImg())
                .balance(userSignupRequestDto.balance() != null ? userSignupRequestDto.balance() : "0")
                .build();

        User savedUser = userRepository.save(signupUser);
        return UserSignupResponseDto.toDto(savedUser);
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
