package com.smart.expense.usersbudgets.controller;

import com.smart.expense.usersbudgets.dto.BudgetDTO;
import com.smart.expense.usersbudgets.dto.CreateBudgetRequest;
import com.smart.expense.usersbudgets.entity.Budget;
import com.smart.expense.usersbudgets.service.BudgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/budgets")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    @GetMapping
    public ResponseEntity<List<BudgetDTO>> getBudgets(
            Principal principal,
            @RequestParam String month
    ) {
        Long userId = Long.parseLong(principal.getName());
        List<Budget> budgets = budgetService.getBudgetsByUserAndMonth(userId, month);

        List<BudgetDTO> dtoList = budgets.stream()
                .map(b -> BudgetDTO.builder()
                        .budgetId(b.getId().toString())              // UUID
                        .userId(b.getUser().getId().toString())      // Long
                        .category(b.getCategory())
                        .amount(b.getAmount())
                        .month(b.getMonth())
                        .currency(b.getCurrency())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtoList);
    }

    @PostMapping
    public ResponseEntity<BudgetDTO> createBudget(
            Principal principal,
            @RequestBody CreateBudgetRequest request
    ) {
        Long userId = Long.parseLong(principal.getName());

        Budget budget = Budget.builder()
                .category(request.getCategory())
                .amount(request.getAmount())
                .month(request.getMonth())
                .currency(request.getCurrency())
                .build();

        Budget saved = budgetService.createBudget(userId, budget);

        BudgetDTO dto = BudgetDTO.builder()
                .budgetId(saved.getId().toString())              // UUID
                .userId(saved.getUser().getId().toString())      // Long
                .category(saved.getCategory())
                .amount(saved.getAmount())
                .month(saved.getMonth())
                .currency(saved.getCurrency())
                .build();

        return ResponseEntity.status(201).body(dto);
    }
}

