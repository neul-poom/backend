package com.jk.module_user.auth.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jk.module_user.auth.dto.UserLoginRequestDto;
import com.jk.module_user.auth.security.UserDetailsImpl;
import com.jk.module_user.auth.security.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.jk.module_user.common.exception.CustomException;
import com.jk.module_user.common.exception.ErrorCode;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import java.io.IOException;

/**
 * JWT 인증 필터
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsServiceImpl userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
        setFilterProcessesUrl("/api/v1/users/login");
    }

    /**
     * 인증 시도
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserLoginRequestDto userLoginRequestDto = new ObjectMapper().readValue(request.getInputStream(), UserLoginRequestDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLoginRequestDto.email(),
                            userLoginRequestDto.password(),
                            null
                    )
            );
        } catch (IOException e) {
            throw new CustomException(ErrorCode.INVALID_REQUEST);
        }
    }

    /**
     * 인증 성공 시
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String email = authResult.getName();
        Long userId = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getUserId();

        String accessToken = jwtTokenProvider.generateAccessToken(email, userId);
        String refreshToken = jwtTokenProvider.generateRefreshToken(email, userId);

        response.setHeader("Authorization", "Bearer " + accessToken);
        response.setHeader("Refresh-Token", refreshToken);

        SecurityContextHolder.getContext().setAuthentication(authResult);

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write("JWT 토큰 발급 성공");
        response.getWriter().flush();
    }

    /**
     * 인증 실패 시
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        throw new CustomException(ErrorCode.LOGIN_NOT_FOUND);
    }

    /**
     * JWT 토큰을 이용한 인증 처리
     */
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = jwtTokenProvider.resolveAccessToken(request);
        if (token != null && jwtTokenProvider.validateToken(token)) {
            String email = jwtTokenProvider.getEmail(token);
            UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(email);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}
