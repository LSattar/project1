package com.skillstorm.taxtracker.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import com.skillstorm.taxtracker.models.Client;
import com.skillstorm.taxtracker.models.Cpa;
import com.skillstorm.taxtracker.models.EmploymentSector;

public record TaxReturnDTO(Client client, Cpa cpa, int year, String status, BigDecimal amountOwed, BigDecimal amountPaid, BigDecimal cost,
		LocalDate creationDate, LocalDate updateDate, EmploymentSector employmentSector) {

}
