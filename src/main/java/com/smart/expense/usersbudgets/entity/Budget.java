package com.smart.expense.usersbudgets.entity;

import com.smart.expense.usersbudgets.dto.BudgetDTO;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "budgets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID budgetId;

    private String userId; // теперь String, берется из JWT

    private String category;
    private BigDecimal amount;
    private String month;
    private String currency;

    public BudgetDTO toDTO() {
        return BudgetDTO.builder()
                .budgetId(budgetId)
                .userId(userId)
                .category(category)
                .amount(amount)
                .month(month)
                .currency(currency)
                .build();
    }

    public static Budget fromDTO(BudgetDTO dto) {
        return Budget.builder()
                .budgetId(dto.getBudgetId())
                .userId(dto.getUserId())
                .category(dto.getCategory())
                .amount(dto.getAmount())
                .month(dto.getMonth())
                .currency(dto.getCurrency())
                .build();
    }
}