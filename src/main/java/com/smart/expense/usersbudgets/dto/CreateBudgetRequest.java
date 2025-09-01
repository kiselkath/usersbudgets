package com.smart.expense.usersbudgets.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateBudgetRequest {
    private String category;
    private Double amount;
    private String month;
    private String currency;
}

