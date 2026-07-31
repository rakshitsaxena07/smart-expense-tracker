package com.diligent.expense_tracker.controller;

import com.diligent.expense_tracker.dto.ExpenseRequest;
import com.diligent.expense_tracker.model.Expense;
import com.diligent.expense_tracker.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public ResponseEntity<Expense> addExpense(@Valid @RequestBody ExpenseRequest request) {

        Expense expense = expenseService.addExpense(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(expense);
    }

    @GetMapping
    public ResponseEntity<List<Expense>> getAllExpenses() {

        List<Expense> expenses = expenseService.getAllExpenses();

        return ResponseEntity.ok(expenses);
    }
}
