package com.smart.expense.usersbudgets.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String userId;          // идентификатор пользователя
    private String email;
    private String name;
    private String defaultCurrency;
}
