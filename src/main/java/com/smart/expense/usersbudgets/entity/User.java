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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // внутреннее поле БД

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String name;

    private String defaultCurrency;

    // связи
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Budget> budgets = new ArrayList<>();

    /**
     * Удобный метод для API:
     * возвращает id как userId
     */
    public Long getUserId() {
        return this.id;
    }
}
