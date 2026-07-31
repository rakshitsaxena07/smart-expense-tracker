package com.diligent.expense_tracker.repository;

import com.diligent.expense_tracker.model.Expense;
import org.springframework.stereotype.Repository;

import java.util.*;


@Repository
public class ExpenseRepository {
    private final Map<UUID, Expense> expenses = new HashMap<>();

    public Expense save(Expense expense) {
        expenses.put(expense.getId(), expense);
        return expense;
    }

    public List<Expense> findAll() {
        return new ArrayList<>(expenses.values());
    }
}