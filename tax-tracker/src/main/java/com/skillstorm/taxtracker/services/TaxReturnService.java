package com.skillstorm.taxtracker.services;

import java.net.URI;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.*;
import com.skillstorm.taxtracker.repositories.TaxReturnRepository;
import com.skillstorm.taxtracker.dtos.TaxReturnDTO;
import com.skillstorm.taxtracker.models.TaxReturn;

@Service
public class TaxReturnService {

	@Value("${base-url}")
	private String baseURL;

	private TaxReturnRepository repo;

	public TaxReturnService(TaxReturnRepository repo) {
		this.repo = repo;
	}

	// Find document categories by category name (optional)
	public ResponseEntity<Iterable<TaxReturn>> findAll(String startsWith) {

		Iterable<TaxReturn> returns;

		try {
			if (startsWith == null)
				returns = repo.findAll();
			else
				returns = repo.findByFilters(startsWith, null, startsWith);

			if (!returns.iterator().hasNext())
				return ResponseEntity.noContent().build();
			else
				return ResponseEntity.ok(returns);
			
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

	// Create tax return
	public ResponseEntity<TaxReturn> createTaxReturn(TaxReturnDTO dto) {
		try {
			TaxReturn saved = repo.save
					(new TaxReturn(0,dto.client(),dto.cpa(), dto.year(), dto.status(),dto.amountOwed(),dto.amountPaid(),dto.cost(), dto.creationDate(),dto.updateDate()));
			return ResponseEntity.created(new URI(this.baseURL + "tax-return/" + saved.getId())).body(saved);
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

	// Update tax return
	public ResponseEntity<TaxReturn> updateTaxReturn(int id, TaxReturnDTO dto) {
		try {
			if (repo.existsById(id))
				return ResponseEntity.ok(repo.save(new TaxReturn(0,dto.client(),dto.cpa(), dto.year(), dto.status(),dto.amountOwed(),dto.amountPaid(),dto.cost(), dto.creationDate(),dto.updateDate())));
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
