package com.smart.expense.usersbudgets.service;

import com.smart.expense.usersbudgets.entity.Budget;
import com.smart.expense.usersbudgets.entity.User;
import com.smart.expense.usersbudgets.exception.ResourceNotFoundException;
import com.smart.expense.usersbudgets.repository.BudgetRepository;
import com.smart.expense.usersbudgets.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;

    /**
     * Создание бюджета для конкретного пользователя.
     * @param userId идентификатор пользователя (Long)
     * @param budget объект бюджета (без привязки к пользователю)
     * @return сохранённый бюджет с привязкой к User
     *
     * Логика:
     * 1. Проверяем, существует ли пользователь с данным userId.
     * 2. Если пользователь найден — устанавливаем его в Budget.
     * 3. Сохраняем Budget в базе.
     */
    public Budget createBudget(Long userId, Budget budget) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));

        budget.setUser(user); // привязываем бюджет к пользователю
        return budgetRepository.save(budget);
    }

    /**
     * Получение всех бюджетов пользователя за указанный месяц.
     * @param userId идентификатор пользователя (Long)
     * @param month месяц в формате YYYY-MM
     * @return список бюджетов
     *
     * Логика:
     * 1. Запрашиваем все записи Budget по userId и месяцу.
     * 2. Если бюджета нет — возвращается пустой список.
     */
    public List<Budget> getBudgetsByUserAndMonth(Long userId, String month) {
        return budgetRepository.findByUserIdAndMonth(userId, month);
    }

    /**
     * Получение конкретного бюджета по его UUID.
     * @param budgetId идентификатор бюджета (UUID)
     * @return найденный бюджет
     *
     * Логика:
     * 1. Проверяем, существует ли Budget с данным budgetId.
     * 2. Если нет — выбрасываем ResourceNotFoundException.
     */
    public Budget getBudgetById(UUID budgetId) {
        return budgetRepository.findById(budgetId)
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found with id " + budgetId));
    }
}
