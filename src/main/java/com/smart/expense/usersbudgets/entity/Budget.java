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
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private String month;

    public BudgetDTO toDTO() {
        return BudgetDTO.builder()
                .id(this.id)
                .userId(this.user.getId())
                .category(this.category)
                .amount(this.amount)
                .month(this.month)
                .build();
    }

    public static Budget fromDTO(BudgetDTO dto, User user) {
        return Budget.builder()
                .id(dto.getId())
                .user(user)
                .category(dto.getCategory())
                .amount(dto.getAmount())
                .month(dto.getMonth())
                .build();
    }
}