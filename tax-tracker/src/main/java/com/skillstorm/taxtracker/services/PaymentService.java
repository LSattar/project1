package com.skillstorm.taxtracker.services;

import java.net.URI;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.*;
import com.skillstorm.taxtracker.repositories.PaymentRepository;
import com.skillstorm.taxtracker.dtos.PaymentDTO;
import com.skillstorm.taxtracker.models.Payment;

@Service
public class PaymentService {

	@Value("${base-url}")
	private String baseURL;

	private PaymentRepository repo;

	public PaymentService(PaymentRepository repo) {
		this.repo = repo;
	}

	// Find payments by client last name (optional)
	public ResponseEntity<Iterable<Payment>> findAll(String startsWith) {

		Iterable<Payment> categories;

		try {
			if (startsWith == null)
				categories = repo.findAll();
			else
				categories = repo.findByClientLastName(startsWith);

			if (!categories.iterator().hasNext())
				return ResponseEntity.noContent().build();
			else
				return ResponseEntity.ok(categories);
			
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

	// Create a new payment
	public ResponseEntity<Payment> createPayment(PaymentDTO dto) {
		try {
			Payment saved = repo.save(new Payment(0,dto.amount(),dto.date(),dto.taxReturn(),dto.method()));
			return ResponseEntity.created(new URI(this.baseURL + "payment/" + saved.getId())).body(saved);
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

	// Update document category
	public ResponseEntity<Payment> updatePayment(int id, PaymentDTO dto) {
		try {
			if (repo.existsById(id))
				return ResponseEntity.ok(repo.save(new Payment(0,dto.amount(),dto.date(),dto.taxReturn(),dto.method())));
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

	// Delete document category
	public ResponseEntity<Void> deleteById(int id) {
		try {
			repo.deleteById(id);
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

}
