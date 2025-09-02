package com.smart.expense.usersbudgets.entity;

import com.smart.expense.usersbudgets.dto.BudgetDTO;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "budgets")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Budget {

    @Id
    @GeneratedValue
    private UUID budgetId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String month; // YYYY-MM

    @Column(nullable = false)
    private String currency;

    /**
     * Конвертация Budget → BudgetDTO
     */
    public BudgetDTO toDTO() {
        return BudgetDTO.builder()
                .budgetId(this.budgetId)
                .userId(this.user != null ? this.user.getUserId() : null)
                .category(this.category)
                .amount(this.amount)
                .month(this.month)
                .currency(this.currency)
                .build();
    }

    /**
     * Конвертация BudgetDTO → Budget
     * (без user, user привязывается в сервисе)
     */
    public static Budget fromDTO(BudgetDTO dto) {
        return Budget.builder()
                .budgetId(dto.getBudgetId())
                .category(dto.getCategory())
                .amount(dto.getAmount())
                .month(dto.getMonth())
                .currency(dto.getCurrency())
                .build();
    }
}
