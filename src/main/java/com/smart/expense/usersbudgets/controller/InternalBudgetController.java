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

    // GET /internal/budgets-by-user/{userId}?month=YYYY-MM
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
