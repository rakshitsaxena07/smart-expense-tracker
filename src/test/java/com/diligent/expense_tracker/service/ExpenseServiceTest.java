package com.diligent.expense_tracker.service;

import com.diligent.expense_tracker.dto.ExpenseRequest;
import com.diligent.expense_tracker.model.Category;
import com.diligent.expense_tracker.model.Expense;
import com.diligent.expense_tracker.repository.ExpenseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private ExpenseService expenseService;

    @Test
    void addExpense_ShouldSaveExpenseSuccessfully() {

        ExpenseRequest request = new ExpenseRequest();
        request.setTitle("Coffee");
        request.setAmount(new BigDecimal("4.50"));
        request.setCategory(Category.FOOD);
        request.setDate(LocalDate.now());


        Expense mockSavedExpense = new Expense(UUID.randomUUID(), "Coffee", new BigDecimal("4.50"), Category.FOOD,
                LocalDate.now());

        when(expenseRepository.save(any(Expense.class))).thenReturn(mockSavedExpense);


        Expense result = expenseService.addExpense(request);

        assertNotNull(result);
        assertEquals("Coffee", result.getTitle());
        assertEquals(new BigDecimal("4.50"), result.getAmount());
        assertEquals(Category.FOOD, result.getCategory());

        verify(expenseRepository).save(any(Expense.class));
    }
}