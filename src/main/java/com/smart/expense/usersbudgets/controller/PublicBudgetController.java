package com.smart.expense.usersbudgets.controller;

import com.smart.expense.usersbudgets.dto.BudgetDTO;
import com.smart.expense.usersbudgets.dto.BudgetResponse;
import com.smart.expense.usersbudgets.entity.Budget;
import com.smart.expense.usersbudgets.service.BudgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/budgets")
@RequiredArgsConstructor
public class PublicBudgetController {

    private final BudgetService budgetService;

    @PostMapping("/{userId}")
    public ResponseEntity<BudgetDTO> createBudget(
            @PathVariable UUID userId,
            @Valid @RequestBody BudgetDTO budgetDTO,
            Authentication authentication
    ) {
        // Проверяем, что пользователь создает бюджет для себя
        String authenticatedUserId = authentication.getName();
        if (!authenticatedUserId.equals(userId.toString())) {
            throw new SecurityException("Access Denied: Cannot create budget for another user");
        }
        
        Budget created = budgetService.createBudget(userId, budgetDTO);
        return ResponseEntity
                .created(URI.create("/budgets/" + created.getId()))
                .body(created.toDTO());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<BudgetResponse> getBudgets(
            @PathVariable UUID userId,
            @RequestParam String month,
            Authentication authentication
    ) {
        // Проверяем, что пользователь запрашивает свои бюджеты
        String authenticatedUserId = authentication.getName();
        if (!authenticatedUserId.equals(userId.toString())) {
            throw new SecurityException("Access Denied: Cannot access another user's budgets");
        }
        
        List<BudgetDTO> budgets = budgetService.getBudgetsByUserAndMonth(userId, month)
                .stream().map(Budget::toDTO).toList();
        return ResponseEntity.ok(new BudgetResponse(budgets));
    }
}
