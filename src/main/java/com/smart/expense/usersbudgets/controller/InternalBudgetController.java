package com.smart.expense.usersbudgets.controller;

import com.smart.expense.usersbudgets.entity.Budget;
import com.smart.expense.usersbudgets.service.BudgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/internal")
@RequiredArgsConstructor
public class InternalBudgetController {

    private final BudgetService budgetService;

    @GetMapping("/budgets-by-user/{userId}")
    public ResponseEntity<List<Object>> getBudgetsByUser(
            @PathVariable UUID userId,
            @RequestParam String month
    ) {
        List<Budget> budgets = budgetService.getBudgetsByUserAndMonth(userId, month);
        List<Object> response = budgets.stream()
                .map(b -> new Object() {
                    public final String category = b.getCategory();
                    public final Double amount = b.getAmount();
                }).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
}
