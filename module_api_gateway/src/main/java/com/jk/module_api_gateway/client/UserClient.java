package com.jk.module_api_gateway.client;

import com.jk.module_api_gateway.client.dto.InternalUserIdResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * User 서비스의 API를 호출하는 Feign 클라이언트
 */
@FeignClient(name = "userClient", url = "${feign.userClient.url}")
public interface UserClient {

    /**
     * 주어진 토큰의 유효성 확인
     *
     * @param token 검증할 JWT 토큰
     * @return InternalUserIdResponseDto 토큰의 유효성 및 사용자 ID를 포함한 응답 DTO
     */
    @GetMapping("/internal/validateToken")
    InternalUserIdResponseDto validateToken(@RequestParam("token") String token);
}