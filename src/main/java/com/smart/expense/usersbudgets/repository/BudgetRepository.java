package com.smart.expense.usersbudgets.repository;

import com.smart.expense.usersbudgets.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BudgetRepository extends JpaRepository<Budget, UUID> {

    // ищем по user.id (Long) и месяцу
    List<Budget> findByUserIdAndMonth(Long userId, String month);
}
