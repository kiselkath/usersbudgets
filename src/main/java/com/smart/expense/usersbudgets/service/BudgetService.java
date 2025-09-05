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

    public Budget createBudget(UUID userId, BudgetDTO budgetDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId, "id", userId.toString()));
        Budget budget = Budget.fromDTO(budgetDTO, user);
        return budgetRepository.save(budget);
    }

    public List<Budget> getBudgetsByUserAndMonth(UUID userId, String month) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId, "id", userId.toString()));
        return budgetRepository.findByUserAndMonth(user, month);
    }

    public Budget getBudgetById(UUID budgetId) {
        return budgetRepository.findById(budgetId)
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found: " + budgetId, "id", budgetId.toString()));
    }

    public Budget updateBudget(UUID budgetId, BudgetDTO dto) {
        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found: " + budgetId, "id", budgetId.toString()));

        budget.setCategory(dto.getCategory());
        budget.setAmount(dto.getAmount());
        budget.setMonth(dto.getMonth());

        return budgetRepository.save(budget);
    }

    public void deleteBudget(UUID budgetId) {
        if (!budgetRepository.existsById(budgetId)) {
            throw new ResourceNotFoundException("Budget not found: " + budgetId, "id", budgetId.toString());
        }
        budgetRepository.deleteById(budgetId);
    }
}