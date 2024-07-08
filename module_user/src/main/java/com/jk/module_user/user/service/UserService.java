package com.jk.module_user.user.service;

import com.jk.module_user.common.exception.CustomException;
import com.jk.module_user.common.exception.ErrorCode;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisService redisService;

    @Transactional
    public UserSignupResponseDto signup(UserSignupRequestDto userSignupRequestDto) {
        String verificationCode = userSignupRequestDto.verificationCode();
        String userEmail = userSignupRequestDto.email();

        if (isVerify(userEmail, verificationCode)) {
            String encodedPassword = passwordEncoder.encode(userSignupRequestDto.password());
            User signupUser = User.builder()
                    .username(userSignupRequestDto.username())
                    .password(passwordEncoder.encode(userSignupRequestDto.password()))
                    .email(userSignupRequestDto.email())
                    .profileImg(userSignupRequestDto.profileImg())
                    .balance(userSignupRequestDto.balance() != null ? userSignupRequestDto.balance() : "0")
                    .build();

            User createdUser = userRepository.save(signupUser);
            redisService.deleteValue(userEmail);

            return UserSignupResponseDto.toDto(createdUser);
        } else {
            throw new CustomException(ErrorCode.EMAIL_CODE_NOT_MATCH);
        }
    }

    public boolean isVerify(String userEmail, String requestCode) {
        return redisService.compareValue(userEmail, requestCode);
    }

    @Transactional
    public UserUpdateResponseDto update(Long userId, UserUpdateRequestDto userUpdateRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(userUpdateRequestDto.currentPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_NOT_MATCH);
        }

        user.updateUserInfo(
                userUpdateRequestDto.username(),
                userUpdateRequestDto.email(),
                userUpdateRequestDto.profileImg(),
                userUpdateRequestDto.balance()
        );

        User savedUser = userRepository.save(user);
        return UserUpdateResponseDto.toDto(savedUser);
    }

    @Transactional
    public void updatePassword(Long userId, UserPasswordRequestDto userPasswordRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(userPasswordRequestDto.currentPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_NOT_MATCH);
        }

        user.updatePassword(passwordEncoder.encode(userPasswordRequestDto.newPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void resign(Long userId, UserPasswordRequestDto userPasswordRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(userPasswordRequestDto.currentPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_NOT_MATCH);
        }

        user.deactivate();
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public UserInfoResponseDto getUserInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        return UserInfoResponseDto.toDto(user);
    }
}
