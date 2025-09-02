package com.smart.expense.usersbudgets.controller;

import com.smart.expense.usersbudgets.dto.BudgetDTO;
import com.smart.expense.usersbudgets.entity.Budget;
import com.smart.expense.usersbudgets.service.BudgetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/budgets")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    /**
     * POST /budgets
     * Создание нового бюджета для пользователя.
     * userId пока передаётся через заголовок, потом будет заменён на JWT.
     */
    @PostMapping
    public ResponseEntity<BudgetDTO> createBudget(
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody BudgetDTO budgetDTO
    ) {
        Budget created = budgetService.createBudget(userId, Budget.fromDTO(budgetDTO));
        return ResponseEntity
                .created(URI.create("/budgets/" + created.getBudgetId())) // ✅ используем getBudgetId()
                .body(created.toDTO());
    }

    /**
     * GET /budgets?month=YYYY-MM
     * Получение всех бюджетов пользователя за конкретный месяц.
     */
    @GetMapping
    public ResponseEntity<List<BudgetDTO>> getBudgetsByUserAndMonth(
            @RequestHeader("X-User-Id") Long userId,
            @RequestParam String month
    ) {
        List<BudgetDTO> response = budgetService.getBudgetsByUserAndMonth(userId, month)
                .stream()
                .map(Budget::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    /**
     * GET /budgets/{id}
     * Получение конкретного бюджета по UUID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BudgetDTO> getBudgetById(@PathVariable UUID id) {
        return ResponseEntity.ok(budgetService.getBudgetById(id).toDTO());
    }

    /**
     * PUT /budgets/{id}
     * Обновление бюджета.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BudgetDTO> updateBudget(
            @PathVariable UUID id,
            @Valid @RequestBody BudgetDTO dto
    ) {
        return ResponseEntity.ok(budgetService.updateBudget(id, dto).toDTO());
    }

    /**
     * DELETE /budgets/{id}
     * Удаление бюджета.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudget(@PathVariable UUID id) {
        budgetService.deleteBudget(id);
        return ResponseEntity.noContent().build();
    }
}
