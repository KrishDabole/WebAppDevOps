package com.example.taxcalculator.security;

import org.springframework.context.annotation.Bean; import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity; import org.springframework.security.web.SecurityFilterChain; import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    private final JwtUtil jwtUtil; public SecurityConfig(JwtUtil jwtUtil){this.jwtUtil=jwtUtil;}
    @Bean public SecurityFilterChain filterChain(HttpSecurity http)throws Exception{
        http.csrf().disable().authorizeHttpRequests(auth->auth.requestMatchers("/api/auth/**").permitAll().anyRequest().authenticated()).addFilterBefore(new JwtFilter(jwtUtil),UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
