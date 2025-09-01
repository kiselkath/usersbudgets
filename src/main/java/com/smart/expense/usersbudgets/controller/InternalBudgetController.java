package com.smart.expense.usersbudgets.controller;

import com.smart.expense.usersbudgets.entity.Budget;
import com.smart.expense.usersbudgets.service.BudgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/internal")
@RequiredArgsConstructor
public class InternalBudgetController {

    private final BudgetService budgetService;

    /**
     * Внутренний эндпоинт для получения бюджетов конкретного пользователя.
     * Используется другими сервисами (например, Report Service).
     *
     * Пример запроса:
     * GET /internal/budgets-by-user/42?month=2025-09
     *
     * Ответ:
     * [
     *   { "category": "Groceries", "amount": 500.00 },
     *   { "category": "Transport", "amount": 100.00 }
     * ]
     *
     * Логика:
     * 1. Сервис получает все бюджеты пользователя за месяц.
     * 2. На выход отдаются только category + amount,
     *    т.к. для внутреннего API остальная информация (budgetId, currency) не нужна.
     */
    @GetMapping("/budgets-by-user/{userId}")
    public ResponseEntity<List<Map<String, Object>>> getBudgetsByUser(
            @PathVariable Long userId,
            @RequestParam String month
    ) {
        List<Budget> budgets = budgetService.getBudgetsByUserAndMonth(userId, month);

        List<Map<String, Object>> response = budgets.stream()
                .map(b -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("category", b.getCategory());
                    map.put("amount", b.getAmount());
                    return map;
                })
                .collect(Collectors.toList());


        return ResponseEntity.ok(response);
    }
}
