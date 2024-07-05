package com.jk.module_user.auth.security;

import com.jk.module_user.user.entity.User;
import com.jk.module_user.user.entity.UserRoleEnum;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 인증된 사용자 정보를 나타내는 클래스
 */
@Getter
public class UserDetailsImpl implements UserDetails {
    private final User user;

    /**
     * UserDetailsImpl 생성자
     * @param user 인증된 사용자 객체
     */
    public UserDetailsImpl(User user) {
        this.user = user;
    }

    /**
     * 사용자 비밀번호 반환
     * @return User 객체의 비밀번호
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * 사용자 이메일 반환
     * @return User 객체의 이메일
     */
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    /**
     * 사용자의 권한을 반환
     * @return User 객체의 권한 목록
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        UserRoleEnum role = user.getRole();
        String authority = role.getAuthority();

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority);

        return authorities;
    }

    /**
     * 계정 만료 여부
     * @return 항상 true (계정이 만료되지 않음)
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 계정 잠김 여부
     * @return 항상 true (계정이 잠겨 있지 않음)
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 자격 증명의 만료 여부
     * @return 항상 true (자격 증명이 만료되지 않음)
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 계정의 활성화 여부
     * @return 항상 true (계정이 활성화 상태)
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
