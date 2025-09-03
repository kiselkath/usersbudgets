package com.smart.expense.usersbudgets.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BudgetResponse {
    private List<BudgetDTO> budgets;
}
