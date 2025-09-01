package com.recom.bot.conf;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Bean
    RequestInterceptor acceptJsonHeaderInterceptor() {
        return requestTemplate -> requestTemplate.header("Accept", "application/json");
    }
}
