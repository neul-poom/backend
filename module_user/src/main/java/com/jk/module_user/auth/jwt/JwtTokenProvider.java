package com.jk.module_user.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import com.jk.module_user.common.exception.CustomException;
import com.jk.module_user.common.exception.ErrorCode;

/**
 * JWT 토큰 제공 클래스
 */
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    public static final String AUTHORIZATION_HEADER = "Authorization"; // HTTP Authorization 헤더 이름
    public static final String REFRESH_HEADER = "Refresh"; // HTTP Refresh 헤더 이름
    public static final String BEARER_PREFIX = "Bearer "; // Bearer 접두사

    @Getter
    @Value("${spring.jwt.access-token-valid-time}")
    private long accessTokenValidationTime; // accessToken 만료시간

    @Value("${spring.jwt.refresh-token-valid-time}")
    private long refreshTokenValidationTime; // refreshToken 만료시간

    @Value("${spring.jwt.secret}")
    private String secretKey;
    private SecretKey key; // secretKey를 Key 객체로 변환

    /**
     * SecretKey 초기화
     */
    @PostConstruct
    protected void init() {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * AccessToken 생성
     */
    public String generateAccessToken(String email, Long userId) {
        Date now = new Date();
        return createToken(now, email, userId, accessTokenValidationTime);
    }

    /**
     * RefreshToken 생성
     */
    public String generateRefreshToken(String email, Long userId) {
        Date now = new Date();
        return createToken(now, email, userId, refreshTokenValidationTime);
    }

    /**
     * JWT 토큰 생성
     */
    private String createToken(Date now, String email, Long userId, long validationTime) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId)) // 사용자 ID 설정
                .claim("email", email) // 이메일 클레임 추가
                .setIssuedAt(now) // 발행 시간 설정
                .setExpiration(new Date(now.getTime() + validationTime)) // 만료 시간 설정
                .signWith(key, SignatureAlgorithm.HS256) // 서명 알고리즘과 키 설정
                .compact();
    }

    /**
     * 토큰에서 이메일 추출
     */
    public String getEmail(String token) {
        if (!validateToken(token)) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
        return parseClaims(token).get("email").toString();
    }

    /**
     * 토큰 유효성 검증
     */
    public boolean validateToken(String token) {
        try {
            Claims claims = parseClaims(token);
            return claims.getExpiration().after(new Date()); // 만료 시간 확인
        } catch (ExpiredJwtException e) {
            throw new CustomException(ErrorCode.EXPIRED_TOKEN);
        } catch (JwtException | IllegalArgumentException e) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
    }

    /**
     * 토큰 만료 시간 반환
     */
    public Long getExpiredTime(String token) {
        Claims claims = parseClaims(token);
        Date expiration = claims.getExpiration();
        Date now = new Date();
        return expiration.getTime() - now.getTime(); // 남은 유효 시간 반환
    }

    /**
     * 토큰에서 Claims 추출
     */
    public Claims parseClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * AccessToken을 응답 헤더에 설정
     */
    public void accessTokenSetHeader(String accessToken, HttpServletResponse response) {
        response.setHeader(AUTHORIZATION_HEADER, accessToken);
    }

    /**
     * RefreshToken을 응답 헤더에 설정
     */
    public void refreshTokenSetHeader(String refreshToken, HttpServletResponse response) {
        response.setHeader(REFRESH_HEADER, refreshToken);
    }

    /**
     * 요청 헤더에서 AccessToken 추출
     */
    public String resolveAccessToken(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION_HEADER);
        if (header != null) {
            return header;
        }
        return null;
    }

    /**
     * 요청 헤더에서 RefreshToken 추출
     */
    public String resolveRefreshToken(HttpServletRequest request) {
        String header = request.getHeader(REFRESH_HEADER);
        if (StringUtils.hasText(header)) {
            return header;
        }
        return null;
    }
}
