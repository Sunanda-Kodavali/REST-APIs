package com.example.a6_rest_auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(csrfConfigurer -> {
            csrfConfigurer.ignoringRequestMatchers("/api/**");
        });

        httpSecurity.authorizeHttpRequests(auth -> {
            auth.anyRequest().permitAll();
        });

        return httpSecurity.build();
    }
}