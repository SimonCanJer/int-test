package com.intuit.test.rest.spring;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configs security temporary while is not defined a concrete rules
 */
@Configuration
@EnableWebSecurity
public class SecuriryConfig {
    /** common api uri mapping property */
    public static final String API_URI = "api.uri";

    /** player uri mapping property */
    public static final String API_PLAYERS_URI = "api.players.uri";

    /**
     * creates, configures and exports security filter chain bean {@link SecurityFilterChain}
     * @param http - security bean object
     * @param env  - {@link Environment} instance
     * @return       the bean announces
     * @throws Exception - and exception can be thrown while configuring
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, Environment env) throws Exception {

        String api = env.getProperty(API_URI, "/api");
        String players = env.getProperty(API_PLAYERS_URI, "/players");
        String playersFull = String.format("%s%s", api, players);
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests((c) -> {
            c.requestMatchers(String.format("%s/*", playersFull), playersFull).permitAll().anyRequest().authenticated();
        });
        return http.build();

    }
}
