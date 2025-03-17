package com.skillstorm.taxtracker.services;

import java.net.URI;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.*;
import org.springframework.web.server.ResponseStatusException;

import com.skillstorm.taxtracker.repositories.PaymentRepository;
import com.skillstorm.taxtracker.repositories.TaxReturnRepository;
import com.skillstorm.taxtracker.dtos.PaymentDTO;
import com.skillstorm.taxtracker.models.Payment;
import com.skillstorm.taxtracker.models.TaxReturn;

@Service
public class PaymentService {

	@Value("${base-url}")
	private String baseURL;

	private PaymentRepository repo;
	private TaxReturnRepository taxReturnRepo;

	public PaymentService(PaymentRepository repo, TaxReturnRepository taxReturnRepo) {
		this.repo = repo;
		this.taxReturnRepo = taxReturnRepo;
	}

	// Find payments by client last name (optional)
	public ResponseEntity<Iterable<Payment>> findAll(String startsWith) {

		Iterable<Payment> payments;

		try {
			if (startsWith == null)
				payments = repo.findAll();
			else
				payments = repo.findByClientLastName(startsWith);

			if (!payments.iterator().hasNext())
				return ResponseEntity.noContent().build();
			else
				return ResponseEntity.ok(payments);

		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

	// Create a new payment
	public ResponseEntity<Payment> createPayment(PaymentDTO dto) {
		try {

			TaxReturn taxReturn = taxReturnRepo.findById(dto.taxReturn().getId())
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
							"Tax return not found with ID: " + dto.taxReturn().getId()));

			Payment saved = repo.save(new Payment(0, dto.amount(), dto.date(), dto.taxReturn(), dto.method()));
			return ResponseEntity.created(new URI(this.baseURL + "payment/" + saved.getId())).body(saved);
		}catch (ResponseStatusException e) {
	        return ResponseEntity.status(e.getStatusCode()).body(null);
	    }  catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

	// Update a payment
	public ResponseEntity<Payment> updatePayment(int id, PaymentDTO dto) {
		try {

			if (repo.existsById(id)) {

				TaxReturn taxReturn = taxReturnRepo.findById(dto.taxReturn().getId())
						.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
								"Tax return not found with ID: " + dto.taxReturn().getId()));
				return ResponseEntity
						.ok(repo.save(new Payment(id, dto.amount(), dto.date(), dto.taxReturn(), dto.method())));

			}
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
		} catch (ResponseStatusException e) {
	        return ResponseEntity.status(e.getStatusCode()).body(null);
	    } catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

	// Delete a payment
	public ResponseEntity<Void> deleteById(int id) {
		try {
			if (repo.existsById(id)) {
				repo.deleteById(id);
				return ResponseEntity.noContent().build();
			} else
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}
}
