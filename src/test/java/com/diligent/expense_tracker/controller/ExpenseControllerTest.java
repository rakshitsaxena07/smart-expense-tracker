package com.diligent.expense_tracker.controller;


import com.diligent.expense_tracker.dto.ExpenseRequest;
import com.diligent.expense_tracker.exception.ResourceNotFoundException;
import com.diligent.expense_tracker.model.Category;
import com.diligent.expense_tracker.model.Expense;
import com.diligent.expense_tracker.service.ExpenseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExpenseController.class)
class ExpenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ExpenseService expenseService;

    @Test
    void shouldCreateExpenseAndReturn201() throws Exception {

        ExpenseRequest request = new ExpenseRequest("Coffee", new BigDecimal("4.50"), Category.FOOD,
                LocalDate.now()
        );

        Expense mockSavedExpense = new Expense(UUID.randomUUID(), "Coffee", new BigDecimal("4.50"), Category.FOOD,
                LocalDate.now()
        );

        when(expenseService.addExpense(any(ExpenseRequest.class))).thenReturn(mockSavedExpense);

        mockMvc.perform(post("/expenses").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.title").value("Coffee"))
                        .andExpect(jsonPath("$.amount").value(4.50))
                        .andExpect(jsonPath("$.category").value("FOOD"))
                        .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void shouldReturnAllExpensesAndStatus200() throws Exception {
        Expense expense1 = new Expense(
                UUID.randomUUID(), "Coffee", new BigDecimal("4.50"), Category.FOOD, LocalDate.now());
        Expense expense2 = new Expense(
                UUID.randomUUID(), "Bus", new BigDecimal("2.00"), Category.TRANSPORT, LocalDate.now());

        List<Expense> mockExpenses = List.of(expense1, expense2);

        when(expenseService.getAllExpenses()).thenReturn(mockExpenses);

        mockMvc.perform(get("/expenses").contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())

                .andExpect(jsonPath("$.size()").value(2))

                .andExpect(jsonPath("$[0].title").value("Coffee"))
                .andExpect(jsonPath("$[0].amount").value(4.50))
                .andExpect(jsonPath("$[1].title").value("Bus"))
                .andExpect(jsonPath("$[1].category").value("TRANSPORT"));
    }

    @Test
    void shouldReturnExpensesByCategoryAndStatus200() throws Exception {

        Expense foodExpense = new Expense(
                UUID.randomUUID(),
                "Coffee",
                new BigDecimal("4.50"),
                Category.FOOD,
                LocalDate.now()
        );

        List<Expense> mockExpenses = List.of(foodExpense);

        when(expenseService.getExpensesByCategory(Category.FOOD)).thenReturn(mockExpenses);

        mockMvc.perform(get("/expenses").param("category", "FOOD").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].title").value("Coffee"))
                .andExpect(jsonPath("$[0].category").value("FOOD"));
    }

    @Test
    void shouldDeleteExpenseAndReturn204() throws Exception {
        UUID id = UUID.randomUUID();

        doNothing().when(expenseService).deleteExpense(id);

        mockMvc.perform(delete("/expenses/" + id)).andExpect(status().isNoContent());

        verify(expenseService).deleteExpense(id);
    }

    @Test
    void shouldReturn404_WhenDeletingNonExistingExpense() throws Exception {

        UUID id = UUID.randomUUID();
        String errorMessage = "Expense not found with id: " + id;

        doThrow(new ResourceNotFoundException(errorMessage))
                .when(expenseService).deleteExpense(id);

        mockMvc.perform(delete("/expenses/" + id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(errorMessage))
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void shouldReturnTotalExpensesAndStatus200() throws Exception {
        when(expenseService.getTotalExpenses()).thenReturn(new BigDecimal("150.75"));

        mockMvc.perform(get("/expenses/total").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(150.75));

        verify(expenseService).getTotalExpenses();
    }

    @Test
    void shouldReturnTotalExpensesByCategoryAndStatus200() throws Exception {
        when(expenseService.getTotalExpensesByCategory(Category.FOOD)).thenReturn(new BigDecimal("45.50"));

        mockMvc.perform(get("/expenses/total").param("category", "FOOD").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(45.50));

        verify(expenseService).getTotalExpensesByCategory(Category.FOOD);
    }
}