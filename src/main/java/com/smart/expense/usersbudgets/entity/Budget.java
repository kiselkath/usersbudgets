package com.smart.expense.usersbudgets.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "budgets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Budget {

    @Id
    @GeneratedValue
    private UUID id;

    private String category;

    private Double amount;

    private String month;  // YYYY-MM

    private String currency;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
