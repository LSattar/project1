package com.skillstorm.taxtracker.models;

import jakarta.persistence.*;
import com.skillstorm.taxtracker.models.*;

import java.math.BigDecimal;
import java.time.*;

@Entity
@Table(name = "tax_return")
public class TaxReturn {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "client_id")
	private Client client;
	
	@ManyToOne
	@JoinColumn(name = "cpa_id", nullable = true)
	private Cpa cpa;
	
	@Column(name = "year")
	private int year;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "amount_owed")
	private BigDecimal amountOwed;
	
	@Column(name = "amount_paid")
	private BigDecimal amountPaid;
	
	@Column(name = "cost")
	private BigDecimal cost;
	
    @Column(name = "creation_date", updatable = false)
    private LocalDate creationDate;

    @Column(name = "update_date")
    private LocalDate updateDate;
    
	@ManyToOne
	@JoinColumn(name = "employment_sector_id", nullable = true)
	private EmploymentSector employmentSector;

    @PreUpdate
    protected void onUpdate() {
        this.updateDate = LocalDate.now();
    }

    @PrePersist
    protected void onCreate() {
        this.creationDate = LocalDate.now();
        this.updateDate = LocalDate.now();
    }

	public TaxReturn() {
		super();
	}

	public TaxReturn(int id, Client client, Cpa cpa, int year, String status, BigDecimal amountOwed, BigDecimal amountPaid, BigDecimal cost,
			LocalDate creationDate, LocalDate updateDate, EmploymentSector employmentSector) {
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
		this.employmentSector = employmentSector;
	}
	
	public TaxReturn(int id, Client client, Cpa cpa, int year, String status, BigDecimal amountOwed, BigDecimal amountPaid, BigDecimal cost,
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

	public BigDecimal getAmountOwed() {
		return amountOwed;
	}

	public void setAmountOwed(BigDecimal amountOwed) {
		this.amountOwed = amountOwed;
	}

	public BigDecimal getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(BigDecimal amountPaid) {
		this.amountPaid = amountPaid;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
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
