package com.playstore.ownerservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

// Spring Security configuration class
@Configuration
public class SecurityConfig {

    // Defines the security filter chain (Spring Security rules)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF protection (useful for APIs / testing, but not recommended in production without alternatives)
            .csrf(csrf -> csrf.disable())

            // Authorization rules
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()  // Allow all requests without authentication
            )

            // Disable frame options to allow H2 database console access
            .headers(headers -> headers.frameOptions().disable());

        // Build and return the configured security filter chain
        return http.build();
    }
}
