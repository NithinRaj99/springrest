package com.springrest.springrest.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.springrest.springrest.security.jwt.JwtAuthenticationFilter;


@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // 1. ALLOW VALIDATE ENDPOINT (Critical Fix)
                        .requestMatchers("/validate/**").permitAll()

                        // 2. ALLOW ERROR PAGES (Fixes the second error in your logs)
                        .requestMatchers("/error").permitAll()

                        // 3. ALLOW SWAGGER (Optional but recommended if you use it)
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()

                        // 4. Everything else requires a Token
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}