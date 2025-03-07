package com.skillstorm.taxtracker.models;

import jakarta.persistence.*;
import com.skillstorm.taxtracker.models.*;
import java.time.*;

@Entity
@Table(name = "tax_return")
public class TaxReturn {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "client_id")
	private Client client;
	
	@Column(name = "cpa_id")
	private Cpa cpa;
	
	@Column(name = "year")
	private int year;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "amount_owed")
	private int amountOwed;
	
	@Column(name = "amount_paid")
	private int amountPaid;
	
	@Column(name = "cost")
	private int cost;
	
	@Column(name = "creation_date")
	private LocalDate creationDate;
	
	@Column(name = "update_date")
	private LocalDate updateDate;

	public TaxReturn() {
		super();
	}

	public TaxReturn(int id, Client client, Cpa cpa, int year, String status, int amountOwed, int amountPaid, int cost,
			LocalDate creationDate, LocalDate updateDate) {
		super();
		this.id = id;
		this.client = client;
		this.cpa = cpa;
		this.year = year;
		this.status = status;
		this.amountOwed = amountOwed;
		this.amountPaid = amountPaid;
		this.cost = cost;
		this.creationDate = creationDate;
		this.updateDate = updateDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Cpa getCpa() {
		return cpa;
	}

	public void setCpa(Cpa cpa) {
		this.cpa = cpa;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getAmountOwed() {
		return amountOwed;
	}

	public void setAmountOwed(int amountOwed) {
		this.amountOwed = amountOwed;
	}

	public int getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(int amountPaid) {
		this.amountPaid = amountPaid;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public LocalDate getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDate updateDate) {
		this.updateDate = updateDate;
	}
	
}
