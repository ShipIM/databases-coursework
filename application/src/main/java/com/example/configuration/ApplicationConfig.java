package com.example.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class ApplicationConfig {

    @Bean
    public Map<String, String> violationsMap() {
        return Map.of(
                "_user_pkey", "A user with such an email already exists"
        );
    }
}
