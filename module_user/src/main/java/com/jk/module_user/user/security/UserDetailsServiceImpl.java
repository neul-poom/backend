package com.jk.module_user.user.security;

import com.jk.module_user.user.entity.User;
import com.jk.module_user.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService { // security의 default 로그인 기능을 사용하지 않겠다

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)     // 해당 유저가 있는지 없는지 확인
                .orElseThrow(() -> new UsernameNotFoundException("Not Found " + username));
        System.out.println("user :" + user);
        return new UserDetailsImpl(user, user.getUsername());   // 사용자 정보를 UserDetails로 반환
    }
}