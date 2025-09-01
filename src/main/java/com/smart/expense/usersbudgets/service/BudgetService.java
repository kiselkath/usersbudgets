package com.smart.expense.usersbudgets.service;

import com.smart.expense.usersbudgets.entity.Budget;
import com.smart.expense.usersbudgets.entity.User;
import com.smart.expense.usersbudgets.exception.ResourceNotFoundException;
import com.smart.expense.usersbudgets.repository.BudgetRepository;
import com.smart.expense.usersbudgets.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;

    public Budget createBudget(UUID userId, Budget budget) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));
        budget.setUser(user);
        return budgetRepository.save(budget);
    }

    public List<Budget> getBudgetsByUserAndMonth(UUID userId, String month) {
        return budgetRepository.findByUserIdAndMonth(userId, month);
    }
}
