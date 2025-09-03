package com.smart.expense.usersbudgets.controller;

import com.smart.expense.usersbudgets.service.BudgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/internal/budgets-by-user")
@RequiredArgsConstructor
public class InternalBudgetController {

    private final BudgetService budgetService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<Map<String, Object>>> getBudgetsByUser(
            @PathVariable String userId,
            @RequestParam String month,
            Authentication authentication
    ) {
        String authenticatedUserId = authentication.getName();
        if (!authenticatedUserId.equals(userId)) {
            throw new SecurityException("Access Denied: Cannot access another user's budgets");
        }

        var response = budgetService.getBudgetsByUserAndMonth(userId, month)
                .stream()
                .map(b -> {
                    Map<String, Object> map = new java.util.HashMap<>();
                    map.put("category", b.getCategory());
                    map.put("amount", b.getAmount());
                    return map;
                })
                .toList();

        return ResponseEntity.<List<Map<String, Object>>>ok(response);
    }
}