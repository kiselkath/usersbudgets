package com.smart.expense.usersbudgets.entity;

import com.smart.expense.usersbudgets.dto.UserDTO;
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
    public UserDTO toDTO() {
        return UserDTO.builder()
                .userId(this.id)
                .email(this.email)
                .password(null) // пароль лучше не возвращать наружу
                .name(this.name)
                .defaultCurrency(this.defaultCurrency)
                .build();
    }

    public static User fromDTO(UserDTO dto) {
        return User.builder()
                .id(dto.getUserId())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .name(dto.getName())
                .defaultCurrency(dto.getDefaultCurrency())
                .build();
    }

}
