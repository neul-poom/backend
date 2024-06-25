package com.jk.module_user.user.service;

import com.jk.module_user.auth.jwt.JwtUtil;
import com.jk.module_user.user.dto.request.SignupRequestDto;
import com.jk.module_user.user.dto.request.UserPasswordRequestDto;
import com.jk.module_user.user.dto.request.UserUpdateRequestDto;
import com.jk.module_user.user.entity.User;
import com.jk.module_user.user.entity.UserRoleEnum;
import com.jk.module_user.user.repository.UserRepository;
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

    // ADMIN_TOKEN
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    // 회원 가입 로직
    public void signup(SignupRequestDto requestDto) {
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

        // 상태
        String status = requestDto.getStatus();

        // 프로필 이미지
        String profileImg = requestDto.getProfileImg();

        // 밸런스
        String balance = requestDto.getBalance();

        // 사용자 등록
        User user = new User(username, password, email, role, status, profileImg, balance);
        userRepository.save(user);
    }

    // 회원 탈퇴 로직
    @Transactional
    public void resign(User user, UserPasswordRequestDto userPasswordRequestDto) {
        if (passwordEncoder.matches(userPasswordRequestDto.getCurrentPassword(), user.getPassword())) {
            user.setStatus("N"); // 비밀번호 일치 시, 회원 상태를 N으로 변경
            userRepository.save(user); // 변경 사항 저장
        } else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    // 회원 정보 수정 로직
    @Transactional
    public void update(User user, UserUpdateRequestDto userUpdateRequestDto) {
        // 비밀번호 확인
        if (!passwordEncoder.matches(userUpdateRequestDto.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }

        if (userUpdateRequestDto.getUsername() != null && !userUpdateRequestDto.getUsername().isEmpty()) {
            user.setUsername(userUpdateRequestDto.getUsername());
        }
        if (userUpdateRequestDto.getEmail() != null && !userUpdateRequestDto.getEmail().isEmpty()) {
            user.setEmail(userUpdateRequestDto.getEmail());
        }
        if (userUpdateRequestDto.getProfileImg() != null && !userUpdateRequestDto.getProfileImg().isEmpty()) {
            user.setProfileImg(userUpdateRequestDto.getProfileImg());
        }
        if (userUpdateRequestDto.getBalance() != null && !userUpdateRequestDto.getBalance().isEmpty()) {
            user.setBalance(userUpdateRequestDto.getBalance());
        }
        userRepository.save(user); // 변경 사항 저장
    }

    // 비밀번호 수정 로직
    @Transactional
    public void updatePassword(User user, UserPasswordRequestDto updatePasswordRequestDto) {
        // 현재 비밀번호 확인
        if (!passwordEncoder.matches(updatePasswordRequestDto.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }

        // 새로운 비밀번호 암호화 및 업데이트
        String encodedNewPassword = passwordEncoder.encode(updatePasswordRequestDto.getNewPassword());
        user.setPassword(encodedNewPassword);
        userRepository.save(user); // 변경 사항 저장
    }
}

