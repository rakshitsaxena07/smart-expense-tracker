package com.diligent.expense_tracker.controller;

import com.diligent.expense_tracker.dto.ExpenseRequest;
import com.diligent.expense_tracker.dto.TotalExpenseResponse;
import com.diligent.expense_tracker.model.Category;
import com.diligent.expense_tracker.model.Expense;
import com.diligent.expense_tracker.service.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @Operation(summary = "Create a new expense", description = "Creates a new expense and stores it in the in-memory")
    @PostMapping
    public ResponseEntity<Expense> addExpense(@Valid @RequestBody ExpenseRequest request) {

        Expense expense = expenseService.addExpense(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(expense);
    }

    @Operation(summary = "Get all expenses", description = "Returns a list of all expenses currently stored in the in-memory repository.")
    @GetMapping
    public ResponseEntity<List<Expense>> getAllExpenses() {

        List<Expense> expenses = expenseService.getAllExpenses();

        return ResponseEntity.ok(expenses);
    }

    @Operation(summary = "Get expenses by category", description = "Returns all expenses that belong to the specified category.")
    @GetMapping(params = "category")
    public ResponseEntity<List<Expense>> getExpenses(@RequestParam Category category) {

        return ResponseEntity.ok(expenseService.getExpensesByCategory(category));
    }

    @Operation(summary = "Deletes expense by ID", description = "Deletes the expense associated with the given ID. Returns 404 if expense does not exist.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable UUID id) {

        expenseService.deleteExpense(id);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Calculates total expense", description = "Calculates and returns the total amount of all stored expenses")
    @GetMapping("/total")
    public ResponseEntity<TotalExpenseResponse> getTotalExpenses() {

        BigDecimal total = expenseService.getTotalExpenses();

        return ResponseEntity.ok(new TotalExpenseResponse(total));
    }

    @Operation(summary = "Calculates total expense by category", description = "Calculates and returns the total amount of stored expenses for the specified category.")
    @GetMapping(value = "/total", params = "category")
    public ResponseEntity<TotalExpenseResponse> getTotalExpensesByCategory(@RequestParam Category category) {

        BigDecimal total = expenseService.getTotalExpensesByCategory(category);

        return ResponseEntity.ok(new TotalExpenseResponse(total));
    }
}
