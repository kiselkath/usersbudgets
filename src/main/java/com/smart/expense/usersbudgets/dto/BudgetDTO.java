package com.smart.expense.usersbudgets.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BudgetDTO {

    private UUID budgetId; // только для ответов
    private String userId; // только для ответов

    @NotBlank(message = "Category is required")
    private String category;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
    private BigDecimal amount;

    @NotBlank(message = "Month is required")
    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])$", message = "Month must be in format YYYY-MM")
    private String month;

    @NotBlank(message = "Currency is required")
    private String currency;
}
