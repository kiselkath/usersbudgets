package com.smart.expense.usersbudgets.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    private String userId; // уникальный идентификатор пользователя, берется из auth-service

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    private String defaultCurrency;

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
    private List<Budget> budgets = new ArrayList<>();

}
