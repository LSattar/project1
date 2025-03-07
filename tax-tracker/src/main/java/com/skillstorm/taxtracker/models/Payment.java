package com.skillstorm.taxtracker.models;

import java.time.LocalDate;

import jakarta.persistence.*;


@Entity
@Table(name = "payment")
public class Payment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "amount")
	private int amount;
	
	@Column(name = "date")
	private LocalDate date;
	
	@Column(name = "return_id")
	private TaxReturn taxReturn;
	
	@Column(name = "method")
	private String method;

}
