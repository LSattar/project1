package com.skillstorm.taxtracker.dtos;

import java.time.LocalDate;
import com.skillstorm.taxtracker.models.Client;
import com.skillstorm.taxtracker.models.Cpa;
import com.skillstorm.taxtracker.models.EmploymentSector;

public record TaxReturnDTO(Client client, Cpa cpa, int year, String status, int amountOwed, int amountPaid, int cost,
		LocalDate creationDate, LocalDate updateDate, EmploymentSector employmentSector) {

}
