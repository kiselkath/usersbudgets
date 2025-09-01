package com.smart.expense.usersbudgets.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // userId in API

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String name;

    private String defaultCurrency;

    // связи
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Budget> budgets = new ArrayList<>();
}
