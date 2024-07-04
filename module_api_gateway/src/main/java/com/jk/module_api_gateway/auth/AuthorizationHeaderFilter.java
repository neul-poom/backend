package com.jk.module_api_gateway.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 필터 클래스로, 요청 헤더에 포함된 JWT 토큰을 검증하고 사용자 ID를 추출하여
 * 수정된 요청에 사용자 ID를 추가하는 역할을 한다.
 */
@Slf4j
@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    private final TokenValidator tokenValidator;

    /**
     * 생성자
     * @param tokenValidator JWT 토큰을 검증하는 클래스
     */
    @Autowired
    public AuthorizationHeaderFilter(TokenValidator tokenValidator) {
        super(Config.class);
        this.tokenValidator = tokenValidator;
    }

    /**
     * 필터를 적용하는 메서드
     * @param config 필터 구성 설정
     * @return GatewayFilter 필터 체인
     */
    @Override
    public GatewayFilter apply(Config config) {
        log.info("AuthorizationHeaderFilter 적용 시작");
        return (exchange, chain) -> {
            final ServerHttpRequest request = exchange.getRequest();
            String path = request.getURI().getPath();

            // 화이트리스트에 있는 경로는 필터 적용하지 않음
            if (config.getWhiteList() != null && config.getWhiteList().stream().anyMatch(path::startsWith)) {
                return chain.filter(exchange);
            }

            // 요청 헤더에 Authorization 토큰이 존재하지 않을 때
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                log.warn("Authorization 헤더에 토큰이 존재하지 않음");
                return handleUnAuthorized(exchange);
            }

            // 요청 헤더에서 토큰 문자열 받아오기
            String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            log.info("추출된 토큰: {}", token);

            // Bearer 접두사를 제거
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            // 토큰 유효성 검사 및 사용자 ID 가져오기
            try {
                if (!tokenValidator.validateToken(token)) {
                    return handleUnAuthorized(exchange);
                }
                log.info("토큰 유효성 검사 성공");
                String userId = tokenValidator.getUserId(token);
                log.info("추출된 사용자 ID: {}", userId);

                // 사용자 ID를 포함한 새로운 요청 생성
                ServerHttpRequest modifiedRequest = request.mutate()
                        .header("X-USER-ID", userId)
                        .build();

                log.info("수정된 요청 URI: {}", modifiedRequest.getURI());
                log.info("수정된 요청 헤더: {}", modifiedRequest.getHeaders());

                // 수정된 요청으로 필터 체인 계속
                return chain.filter(exchange.mutate()
                                .request(modifiedRequest)
                                .build())
                        .doOnSuccess(aVoid -> log.info("요청 성공적으로 처리됨"))
                        .doOnError(throwable -> log.error("요청 처리 중 오류 발생", throwable));
            } catch (Exception e) {
                log.warn("유효하지 않은 토큰");
                return handleUnAuthorized(exchange);
            }
        };
    }

    /**
     * 인증되지 않은 요청 처리 메서드
     * @param exchange ServerWebExchange 객체
     * @return Mono<Void> 응답 완료 신호
     */
    private Mono<Void> handleUnAuthorized(ServerWebExchange exchange) {
        log.warn("미인증 요청 처리");

        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);

        return response.setComplete();
    }

    /**
     * 필터 구성 설정 클래스
     */
    public static class Config {
        private List<String> whiteList;

        public List<String> getWhiteList() {
            return whiteList;
        }

        public Config setWhiteList(List<String> whiteList) {
            this.whiteList = whiteList;
            return this;  // 'this'를 반환하여 메서드 체이닝을 가능하게 함
        }
    }
}
