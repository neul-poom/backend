package com.jk.module_api_gateway.config;

import com.jk.module_api_gateway.filter.AuthorizationHeaderFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 라우트와 필터 설정
 */
@Configuration
public class FilterConfig {

    /**
     * Gateway의 라우트 설정
     *
     * @param builder RouteLocatorBuilder 인스턴스
     * @param authorizationHeaderFilter AuthorizationHeaderFilter 인스턴스
     * @return RouteLocator 설정된 라우트
     */
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder, AuthorizationHeaderFilter authorizationHeaderFilter) {
        return builder.routes()
                .route(r -> r.path("/api/v1/users/**")
                        .filters(f -> f.filter(authorizationHeaderFilter.apply(new AuthorizationHeaderFilter.Config())))
                        .uri("http://localhost:8081"))
                .route(r -> r.path("/api/v1/coupons/**")
                        .filters(f -> f.filter(authorizationHeaderFilter.apply(new AuthorizationHeaderFilter.Config())))
                        .uri("http://localhost:8083"))
                .route(r -> r.path("/api/v1/coupon-issued/**")
                        .filters(f -> f.filter(authorizationHeaderFilter.apply(new AuthorizationHeaderFilter.Config())))
                        .uri("http://localhost:8083"))
                .route(r -> r.path("/api/v1/payments/**")
                        .filters(f -> f.filter(authorizationHeaderFilter.apply(new AuthorizationHeaderFilter.Config())))
                        .uri("http://localhost:8084"))
                .route(r -> r.path("/api/v1/lectures/notes/**")
                        .filters(f -> f.filter(authorizationHeaderFilter.apply(new AuthorizationHeaderFilter.Config())))
                        .uri("http://localhost:8085"))
                .route(r -> r.path("/api/v1/lectures/multimedia/**")
                        .filters(f -> f.filter(authorizationHeaderFilter.apply(new AuthorizationHeaderFilter.Config())))
                        .uri("http://localhost:8085"))
                .route(r -> r.path("/api/v1/lectures/**")
                        .filters(f -> f.filter(authorizationHeaderFilter.apply(new AuthorizationHeaderFilter.Config())))
                        .uri("http://localhost:8082"))
                .route(r -> r.path("/module_user/auth/**")
                        .filters(f -> f.filter(authorizationHeaderFilter.apply(new AuthorizationHeaderFilter.Config()))
                                .rewritePath("/module_user/auth/(?<segment>.*)", "/api/v1/${segment}"))
                        .uri("lb://USER-SERVICE"))
                .build();
    }
}