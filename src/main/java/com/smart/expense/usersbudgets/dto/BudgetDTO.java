package com.smart.expense.usersbudgets.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BudgetDTO {
    private String budgetId; // UUID как строка
    private String userId;   // Long как строка
    private String category;
    private Double amount;
    private String month;
    private String currency;
}
