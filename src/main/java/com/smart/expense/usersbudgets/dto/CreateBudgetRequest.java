package com.smart.expense.usersbudgets.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBudgetRequest {
    private String category;
    private Double amount;
    private String month;      // YYYY-MM
    private String currency;
}
