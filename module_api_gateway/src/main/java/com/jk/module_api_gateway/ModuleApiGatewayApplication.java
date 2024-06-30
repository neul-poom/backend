package com.jk.module_api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients // Feign Client 활성화
public class ModuleApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModuleApiGatewayApplication.class, args);
    }
}