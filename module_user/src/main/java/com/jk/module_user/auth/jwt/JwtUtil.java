package com.jk.module_user.auth.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * JWT 토큰 생성 및 검증 담당
 */
@Slf4j(topic = "JwtUtil")
@Component
public class JwtUtil {
    // 토큰 만료시간
    private long expiration = 60 * 60 * 1000L; // 60분

    @Value("${jwt.secret.key}") // Base64 Encode 한 SecretKey
    private String secret;

    /**
     * JWT 토큰 생성
     *
     * @param username 사용자 이름
     * @return 생성된 JWT 토큰
     */
    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * JWT 토큰 검증
     *
     * @param token 검증할 JWT 토큰
     * @return 토큰의 유효성 여부
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * JWT 토큰에서 사용자 정보 추출
     *
     * @param token JWT 토큰
     * @return 토큰에서 추출된 사용자 정보
     */
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
}