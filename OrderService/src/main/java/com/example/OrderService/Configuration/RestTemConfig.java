package com.example.OrderService.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
