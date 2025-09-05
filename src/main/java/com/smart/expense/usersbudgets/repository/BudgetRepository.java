package com.smart.expense.usersbudgets.repository;

import com.smart.expense.usersbudgets.entity.Budget;
import com.smart.expense.usersbudgets.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, UUID> {
    List<Budget> findByUserAndMonth(User user, String month);
}
