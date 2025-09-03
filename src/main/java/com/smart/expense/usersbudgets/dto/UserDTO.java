package com.smart.expense.usersbudgets.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * DTO для передачи данных о пользователях через REST API.
 * Используется в контроллерах вместо сущности User.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long userId; // используется только в ответах

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    private String name;

    @NotBlank(message = "Default currency is required")
    private String defaultCurrency;
}
