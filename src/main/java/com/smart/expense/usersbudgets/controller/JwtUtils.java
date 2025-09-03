package com.smart.expense.usersbudgets.controller;

import org.springframework.security.oauth2.jwt.Jwt;

/**
 * Утилита для извлечения userId из JWT токена.
 * ⚠️ Google возвращает "sub" как String.
 * Если нужно хранить Long userId, нужно маппить sub → userId в БД.
 */
public class JwtUtils {

    public static Long extractUserId(Jwt jwt) {
        if (jwt.containsClaim("user_id")) {
            return jwt.getClaim("user_id"); // если в токене есть кастомный user_id
        }
        // по умолчанию Google кладет уникальный ID в "sub"
        try {
            return Long.valueOf(jwt.getSubject()); // если удастся привести к Long
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("JWT subject is not a valid Long: " + jwt.getSubject());
        }
    }
}
