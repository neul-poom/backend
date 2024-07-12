package com.jk.module_api_gateway.config;

import com.jk.module_api_gateway.auth.AuthorizationHeaderFilter;
import com.jk.module_api_gateway.auth.TokenValidator;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class FilterConfig {

    @Bean
    public AuthorizationHeaderFilter authorizationHeaderFilter(TokenValidator tokenValidator) {
        return new AuthorizationHeaderFilter(tokenValidator);
    }

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder, AuthorizationHeaderFilter authorizationHeaderFilter) {
        return builder.routes()
                .route(r -> r.path("/api/v1/users/**")
                        .filters(f -> f.filter(authorizationHeaderFilter.apply(
                                        new AuthorizationHeaderFilter.Config()
                                                .setWhiteList(Arrays.asList("/api/v1/users/signup", "/api/v1/users/login", "/api/v1/users/verification"))))
                                .rewritePath("/api/v1/users/(?<segment>.*)", "/api/v1/users/${segment}"))
                        .uri("http://localhost:8081"))

                .route(r -> r.path("/api/v1/coupons/**")
                        .filters(f -> f.filter(authorizationHeaderFilter.apply(new AuthorizationHeaderFilter.Config()))
                                .rewritePath("/api/v1/coupons/(?<segment>.*)", "/api/v1/coupons/${segment}"))
                        .uri("http://localhost:8083"))

                .route(r -> r.path("/api/v1/coupon-issued/**")
                        .filters(f -> f.filter(authorizationHeaderFilter.apply(new AuthorizationHeaderFilter.Config()))
                                .rewritePath("/api/v1/coupon-issued/(?<segment>.*)", "/api/v1/coupon-issued/${segment}"))
                        .uri("http://localhost:8083"))

                .route(r -> r.path("/api/v1/payments/**")
                        .filters(f -> f.filter(authorizationHeaderFilter.apply(new AuthorizationHeaderFilter.Config()))
                                .rewritePath("/api/v1/payments/(?<segment>.*)", "/api/v1/payments/${segment}"))
                        .uri("http://localhost:8084"))

                .route(r -> r.path("/api/v1/lectures/notes/**")
                        .filters(f -> f.filter(authorizationHeaderFilter.apply(new AuthorizationHeaderFilter.Config()))
                                .rewritePath("/api/v1/lectures/notes/(?<segment>.*)", "/api/v1/lectures/notes/${segment}"))
                        .uri("http://localhost:8085"))

                .route(r -> r.path("/api/v1/lectures/multimedia/**")
                        .filters(f -> f.filter(authorizationHeaderFilter.apply(new AuthorizationHeaderFilter.Config()))
                                .rewritePath("/api/v1/lectures/multimedia/(?<segment>.*)", "/api/v1/lectures/multimedia/${segment}"))
                        .uri("http://localhost:8085"))

                .route(r -> r.path("/api/v1/lectures/**")
                        .filters(f -> f.filter(authorizationHeaderFilter.apply(new AuthorizationHeaderFilter.Config()))
                                .rewritePath("/api/v1/lectures/(?<segment>.*)", "/api/v1/lectures/${segment}"))
                        .uri("http://localhost:8082"))

                .route(r -> r.path("/api/v1/orders/**")
                        .filters(f -> f.filter(authorizationHeaderFilter.apply(new AuthorizationHeaderFilter.Config()))
                                .rewritePath("/api/v1/orders/(?<segment>.*)", "/api/v1/orders/${segment}"))
                        .uri("http://localhost:8086"))

                .route(r -> r.path("/module_user/auth/**")
                        .filters(f -> f.filter(authorizationHeaderFilter.apply(new AuthorizationHeaderFilter.Config()))
                                .rewritePath("/module_user/auth/(?<segment>.*)", "/api/v1/${segment}"))
                        .uri("lb://USER-SERVICE"))

                .build();
    }
}
