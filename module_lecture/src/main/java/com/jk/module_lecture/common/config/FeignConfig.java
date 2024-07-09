package com.jk.module_lecture.common.config;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class FeignConfig {

    @Bean
    @Primary
    public Encoder feignFormEncoder() {
        return new SpringFormEncoder(new SpringEncoder(() -> new HttpMessageConverters()));
    }
}