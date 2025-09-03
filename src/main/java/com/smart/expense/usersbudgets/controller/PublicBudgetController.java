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

@RestController
@RequestMapping("/budgets")
@RequiredArgsConstructor
public class PublicBudgetController {

    private final BudgetService budgetService;

    @PostMapping
    public ResponseEntity<BudgetDTO> createBudget(
            @Valid @RequestBody BudgetDTO budgetDTO,
            Authentication authentication
    ) {
        String userId = authentication.getName();
        Budget created = budgetService.createBudget(userId, Budget.fromDTO(budgetDTO));
        return ResponseEntity
                .created(URI.create("/budgets/" + created.getBudgetId()))
                .body(created.toDTO());
    }

    @GetMapping
    public ResponseEntity<BudgetResponse> getBudgets(
            @RequestParam String month,
            Authentication authentication
    ) {
        String userId = authentication.getName();
        List<BudgetDTO> budgets = budgetService.getBudgetsByUserAndMonth(userId, month)
                .stream().map(Budget::toDTO).toList();
        return ResponseEntity.ok(new BudgetResponse(budgets));
    }
}
