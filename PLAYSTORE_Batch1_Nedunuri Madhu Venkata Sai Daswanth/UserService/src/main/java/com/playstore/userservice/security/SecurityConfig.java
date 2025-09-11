package com.playstore.userservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

// Spring Security configuration for User Service
@Configuration
public class SecurityConfig {

    // Defines the security filter chain (Spring Security rules)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF protection (common for stateless APIs, but not ideal for forms in production)
            .csrf(csrf -> csrf.disable())

            // Allow all requests without authentication (currently no restrictions)
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
            )

            // Disable frame options to allow H2 console access
            .headers(headers -> headers.frameOptions().disable());

        // Build and return the configured filter chain
        return http.build();
    }
}
