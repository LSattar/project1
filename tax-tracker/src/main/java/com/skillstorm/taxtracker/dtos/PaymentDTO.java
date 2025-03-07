package com.skillstorm.taxtracker.dtos;

import java.time.LocalDate;
import com.skillstorm.taxtracker.models.TaxReturn;

public record PaymentDTO(int amount, LocalDate date, TaxReturn taxReturn, String method) {

}
