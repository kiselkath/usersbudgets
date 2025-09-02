package com.smart.expense.usersbudgets.service;

import com.smart.expense.usersbudgets.dto.BudgetDTO;
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

    /**
     * Создание бюджета для конкретного пользователя.
     */
    public Budget createBudget(Long userId, Budget budget) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));

        budget.setUser(user); // привязываем бюджет к пользователю
        return budgetRepository.save(budget);
    }

    /**
     * Получение всех бюджетов пользователя за указанный месяц.
     */
    public List<Budget> getBudgetsByUserAndMonth(Long userId, String month) {
        return budgetRepository.findByUserIdAndMonth(userId, month);
    }

    /**
     * Получение конкретного бюджета по его UUID.
     */
    public Budget getBudgetById(UUID budgetId) {
        return budgetRepository.findById(budgetId)
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found with id " + budgetId));
    }

    /**
     * Обновление бюджета.
     */
    public Budget updateBudget(UUID budgetId, BudgetDTO dto) {
        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found: " + budgetId));

        budget.setCategory(dto.getCategory());
        budget.setAmount(dto.getAmount());
        budget.setMonth(dto.getMonth());
        budget.setCurrency(dto.getCurrency());

        return budgetRepository.save(budget);
    }

    /**
     * Удаление бюджета.
     */
    public void deleteBudget(UUID budgetId) {
        if (!budgetRepository.existsById(budgetId)) {
            throw new ResourceNotFoundException("Budget not found: " + budgetId);
        }
        budgetRepository.deleteById(budgetId);
    }
}
