package com.diligent.expense_tracker.service;

import com.diligent.expense_tracker.dto.ExpenseRequest;
import com.diligent.expense_tracker.model.Category;
import com.diligent.expense_tracker.model.Expense;
import com.diligent.expense_tracker.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ExpenseService {

    private final ExpenseRepository repository;

    public ExpenseService(ExpenseRepository repository) {
        this.repository = repository;
    }

    public Expense addExpense(ExpenseRequest request) {

        Expense expense = new Expense(
                UUID.randomUUID(),
                request.getTitle(),
                request.getAmount(),
                request.getCategory(),
                request.getDate()
        );

        return repository.save(expense);
    }

    public List<Expense> getAllExpenses() {
        return repository.findAll();
    }

    public List<Expense> getExpensesByCategory(Category category) {
        return repository.findByCategory(category);
    }
}