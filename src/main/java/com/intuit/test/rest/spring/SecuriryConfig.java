package com.intuit.test.rest.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configs security while is not
 */
@Configuration
@EnableWebSecurity
public class SecuriryConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       http.csrf((c)->{c.disable();});
       http.authorizeHttpRequests((c)->{c.requestMatchers("/api/players/*","/api/players").permitAll().anyRequest().authenticated();});
       return http.build();
    }
}
