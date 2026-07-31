package com.diligent.expense_tracker.controller;


import com.diligent.expense_tracker.dto.ExpenseRequest;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
}