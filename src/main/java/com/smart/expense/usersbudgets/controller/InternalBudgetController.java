package com.smart.expense.usersbudgets.controller;

import com.smart.expense.usersbudgets.service.BudgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Внутренний API для общения между сервисами.
 * Не требует userId из токена, он приходит параметром.
 */
@RestController
@RequestMapping("/internal")
@RequiredArgsConstructor
public class InternalBudgetController {

    private final BudgetService budgetService;

    @GetMapping("/budgets-by-user/{userId}")
    public ResponseEntity<List<Map<String, Object>>> getBudgetsByUser(
            @PathVariable Long userId,
            @RequestParam String month
    ) {
        List<Map<String, Object>> response = budgetService.getBudgetsByUserAndMonth(userId, month)
                .stream()
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
