package com.smart.expense.usersbudgets.controller;

import com.smart.expense.usersbudgets.dto.BudgetDTO;
import com.smart.expense.usersbudgets.entity.Budget;
import com.smart.expense.usersbudgets.service.BudgetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Публичный REST API для работы с бюджетами конкретного пользователя.
 * Аутентификация через Google OAuth2 (Bearer Token).
 * userId извлекается из JWT токена.
 */
@RestController
@RequestMapping("/budgets")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    @PostMapping
    public ResponseEntity<BudgetDTO> createBudget(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody BudgetDTO budgetDTO
    ) {
        Long userId = JwtUtils.extractUserId(jwt);
        Budget created = budgetService.createBudget(userId, Budget.fromDTO(budgetDTO));
        return ResponseEntity
                .created(URI.create("/budgets/" + created.getBudgetId()))
                .body(created.toDTO());
    }

    @GetMapping
    public ResponseEntity<List<BudgetDTO>> getBudgetsByUserAndMonth(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam String month
    ) {
        Long userId = JwtUtils.extractUserId(jwt);
        List<BudgetDTO> response = budgetService.getBudgetsByUserAndMonth(userId, month)
                .stream()
                .map(Budget::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BudgetDTO> getBudgetById(@PathVariable UUID id) {
        return ResponseEntity.ok(budgetService.getBudgetById(id).toDTO());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BudgetDTO> updateBudget(
            @PathVariable UUID id,
            @Valid @RequestBody BudgetDTO dto
    ) {
        return ResponseEntity.ok(budgetService.updateBudget(id, dto).toDTO());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudget(@PathVariable UUID id) {
        budgetService.deleteBudget(id);
        return ResponseEntity.noContent().build();
    }
}
