package com.jk.module_user.auth.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jk.module_user.auth.jwt.JwtUtil;
import com.jk.module_user.common.dto.ApiResponseDto;
import com.jk.module_user.user.dto.request.UserLoginRequestDto;
import com.jk.module_user.user.dto.response.UserLoginResponseDto;
import com.jk.module_user.user.entity.UserRoleEnum;
import com.jk.module_user.user.security.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/v1/users/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserLoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), UserLoginRequestDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getUsername(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        UserRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();

        String token = jwtUtil.createToken(username, role);
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);

        // 로그인 성공 시 HTTP 상태 코드 201(CREATED)으로 설정
        response.setStatus(HttpStatus.CREATED.value());

        // 로그인 응답 DTO 생성
        UserLoginResponseDto loginResponseDto = new UserLoginResponseDto(username, token);

        // ApiResponseDto로 응답
        ApiResponseDto<UserLoginResponseDto> apiResponse = new ApiResponseDto<>(
                HttpStatus.CREATED,
                "로그인 성공",
                loginResponseDto
        );

        try {
            response.setContentType("application/json");
            response.getWriter().write(new ObjectMapper().writeValueAsString(apiResponse));
        } catch (IOException e) {
            log.error("Failed to write login response to output stream", e);
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }
}
