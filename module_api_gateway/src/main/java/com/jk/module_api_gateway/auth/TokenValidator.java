package com.jk.module_api_gateway.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * JWT 토큰을 검증하는 클래스
 */
@Slf4j
@Component
public class TokenValidator {

    @Value("${spring.jwt.secret}")
    private String secretKey;

    private SecretKey key; // secretKey를 Key 객체로 해싱

    /**
     * 객체 초기화 후 SecretKey를 설정하는 메서드
     */
    @PostConstruct
    protected void init() {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 토큰에서 사용자 ID를 추출하는 메서드
     * @param token JWT 토큰
     * @return 사용자 ID
     */
    public String getUserId(String token) {
        log.info("getUserId 메서드 진입, 토큰: {}", token);
        return parseClaims(token).getSubject();
    }

    /**
     * 토큰이 유효한지 확인하는 메서드
     * @param token JWT 토큰
     * @return 토큰 유효 여부
     */
    public boolean validateToken(String token) {
        log.info("validateToken 메서드 진입, 토큰: {}", token);
        Claims claims = parseClaims(token);
        log.info("추출된 claims: {}", claims);
        return claims.getExpiration().after(new Date());
    }

    /**
     * 토큰에서 Claims를 추출하는 메서드
     * @param token JWT 토큰
     * @return Claims 객체
     */
    private Claims parseClaims(String token) {
        log.info("parseClaims 메서드 진입, 토큰: {}", token);
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody();
    }
}
