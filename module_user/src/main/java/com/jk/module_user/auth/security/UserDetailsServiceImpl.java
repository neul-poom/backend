package com.jk.module_user.auth.security;

import com.jk.module_user.user.entity.User;
import com.jk.module_user.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 사용자 세부 정보 서비스를 구현하는 클래스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * 이메일을 사용하여 사용자 로드를 시도
     * @param email 이메일 주소
     * @return UserDetails 객체
     * @throws UsernameNotFoundException 사용자를 찾을 수 없는 경우 예외 발생
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("UserDetailsServiceImpl - 이메일로 사용자 로드: {}", email);

        // 이메일을 사용하여 사용자 검색
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없음: " + email));

        log.info("UserDetailsServiceImpl - 사용자 로드 성공: {}", user.getEmail());

        // UserDetailsImpl 객체 반환
        return new UserDetailsImpl(user);
    }
}
