package com.skillstorm.taxtracker.models;

import jakarta.persistence.*;
import com.skillstorm.taxtracker.models.*;
import java.time.*;


@Entity
@Table(name = "client")
public class Client {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "ssn")
	private String ssn;
	
	@Column(name = "dob")
	private LocalDate dob;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "address1")
	private String address1;
	
	@Column(name = "address2")
	private String address2;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "state")
	private String state;
	
	@Column(name = "zip")
	private String zip;
	
	@ManyToOne
	@JoinColumn(name = "employment_sector_id")
	private EmploymentSector employmentSector;

	public Client() {
		super();
	}

	public Client(int id, String firstName, String lastName, String ssn, LocalDate dob, String phone, String address1,
			String address2, String city, String state, String zip, EmploymentSector employmentSector) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.ssn = ssn;
		this.dob = dob;
		this.phone = phone;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.employmentSector = employmentSector;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public EmploymentSector getEmploymentSector() {
		return employmentSector;
	}

	public void setEmploymentSector(EmploymentSector employmentSector) {
		this.employmentSector = employmentSector;
	}
	
}
