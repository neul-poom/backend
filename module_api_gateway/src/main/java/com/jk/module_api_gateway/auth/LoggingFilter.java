package com.jk.module_api_gateway.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * 요청 및 응답 로깅을 위한 Gateway 필터
 */
@Slf4j
@Component
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {

    /**
     * Config 클래스를 사용하는 기본 생성자
     */
    public LoggingFilter() {
        super(Config.class);
    }

    /**
     * 필터를 적용하는 메서드
     * @param config 필터 설정
     * @return GatewayFilter 객체
     */
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            logRequest(exchange.getRequest());
            return chain.filter(exchange).then(Mono.fromRunnable(() -> logResponse(exchange.getResponse())));
        };
    }

    /**
     * 요청 정보를 로깅하는 메서드
     * @param request ServerHttpRequest 객체
     */
    private void logRequest(ServerHttpRequest request) {
        log.info("Request: method={}, uri={}", request.getMethod(), request.getURI());
    }

    /**
     * 응답 정보를 로깅하는 메서드
     * @param response ServerHttpResponse 객체
     */
    private void logResponse(ServerHttpResponse response) {
        log.info("Response: status={}", response.getStatusCode());
    }

    /**
     * 필터 설정을 위한 Config 클래스
     */
    public static class Config {
        // 설정 속성이 있을 경우 추가
    }
}
