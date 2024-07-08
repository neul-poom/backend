package com.jk.module_user.auth.security;

import com.jk.module_user.common.exception.CustomException;
import com.jk.module_user.common.exception.ErrorCode;
import com.jk.module_user.user.entity.User;
import com.jk.module_user.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * 사용자 세부 정보 서비스를 구현하는 클래스
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * 이메일을 사용하여 사용자 로드를 시도
     * @param email 이메일 주소
     * @return UserDetails 객체
     * @throws CustomException 사용자를 찾을 수 없는 경우 예외 발생
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws CustomException {
        // 이메일을 사용하여 사용자 검색
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // UserDetailsImpl 객체 반환
        return new UserDetailsImpl(user);
    }
}
