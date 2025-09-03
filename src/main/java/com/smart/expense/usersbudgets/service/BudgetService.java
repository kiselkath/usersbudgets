package com.smart.expense.usersbudgets.service;

import com.smart.expense.usersbudgets.dto.BudgetDTO;
import com.smart.expense.usersbudgets.entity.Budget;
import com.smart.expense.usersbudgets.exception.ResourceNotFoundException;
import com.smart.expense.usersbudgets.repository.BudgetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;

    public Budget createBudget(String userId, Budget budget) {
        budget.setUserId(userId);
        return budgetRepository.save(budget);
    }

    public List<Budget> getBudgetsByUserAndMonth(String userId, String month) {
        return budgetRepository.findByUserIdAndMonth(userId, month);
    }

    public Budget getBudgetById(UUID budgetId) {
        return budgetRepository.findById(budgetId)
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found: " + budgetId));
    }

    public Budget updateBudget(UUID budgetId, BudgetDTO dto) {
        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found: " + budgetId));

        budget.setCategory(dto.getCategory());
        budget.setAmount(dto.getAmount());
        budget.setMonth(dto.getMonth());
        budget.setCurrency(dto.getCurrency());

        return budgetRepository.save(budget);
    }

    public void deleteBudget(UUID budgetId) {
        if (!budgetRepository.existsById(budgetId)) {
            throw new ResourceNotFoundException("Budget not found: " + budgetId);
        }
        budgetRepository.deleteById(budgetId);
    }
}
