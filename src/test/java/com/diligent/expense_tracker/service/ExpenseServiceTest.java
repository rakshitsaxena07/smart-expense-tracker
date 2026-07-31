package com.diligent.expense_tracker.service;

import com.diligent.expense_tracker.dto.ExpenseRequest;
import com.diligent.expense_tracker.exception.ResourceNotFoundException;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    void shouldReturnAllExpenses() {

        Expense expense1 = new Expense(UUID.randomUUID(), "Coffee", new BigDecimal("4.50"), Category.FOOD, LocalDate.now());
        Expense expense2 = new Expense(UUID.randomUUID(), "Bus", new BigDecimal("2.00"), Category.TRANSPORT, LocalDate.now());

        List<Expense> mockExpenses = List.of(expense1, expense2);

        when(expenseRepository.findAll()).thenReturn(mockExpenses);

        List<Expense> result = expenseService.getAllExpenses();

        assertEquals(2, result.size());
        assertEquals("Coffee", result.get(0).getTitle());
        assertEquals("Bus", result.get(1).getTitle());

        verify(expenseRepository).findAll();
    }

    @Test
    void shouldReturnExpensesByCategory() {

        Expense foodExpense = new Expense(UUID.randomUUID(), "Coffee", new BigDecimal("4.50"), Category.FOOD,
                LocalDate.now());

        List<Expense> mockFoodExpenses = List.of(foodExpense);

        when(expenseRepository.findByCategory(Category.FOOD)).thenReturn(mockFoodExpenses);

        List<Expense> result = expenseService.getExpensesByCategory(Category.FOOD);

        assertEquals(1, result.size());
        assertEquals("Coffee", result.get(0).getTitle());
        assertEquals(Category.FOOD, result.get(0).getCategory());

        verify(expenseRepository).findByCategory(Category.FOOD);
    }

    @Test
    void deleteExpense_ShouldDelete_WhenExpenseExists() {

        UUID id = UUID.randomUUID();
        Expense mockExpense = new Expense();
        mockExpense.setId(id);

        when(expenseRepository.findById(id)).thenReturn(Optional.of(mockExpense));

        expenseService.deleteExpense(id);

        verify(expenseRepository).findById(id);
        verify(expenseRepository).deleteById(id);
    }

    @Test
    void deleteExpense_ShouldThrowException_WhenExpenseDoesNotExist() {

        UUID id = UUID.randomUUID();

        when(expenseRepository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {expenseService.deleteExpense(id);});

        assertEquals("Expense not found with id: " + id, exception.getMessage());

        verify(expenseRepository).findById(id);
        verify(expenseRepository, never()).deleteById(any(UUID.class));
    }
}