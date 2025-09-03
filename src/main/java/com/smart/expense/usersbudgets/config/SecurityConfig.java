package com.smart.expense.usersbudgets.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration for Users & Budgets Service.
 *
 * ✅ Все запросы требуют Bearer Token (JWT).
 * ✅ Приложение работает как Resource Server.
 * ✅ JWT проверяются с помощью spring.security.oauth2.resourceserver.jwt.issuer-uri или jwk-set-uri.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF отключаем (REST API без форм)
                .csrf(AbstractHttpConfigurer::disable)

                // Все запросы требуют авторизации
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()
                )

                // Сервис работает как OAuth2 Resource Server (JWT)
                .oauth2ResourceServer(oauth2 -> oauth2.jwt());

        return http.build();
    }
}
