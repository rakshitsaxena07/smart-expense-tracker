package com.diligent.expense_tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TotalExpenseResponse {

    private BigDecimal total;
}
