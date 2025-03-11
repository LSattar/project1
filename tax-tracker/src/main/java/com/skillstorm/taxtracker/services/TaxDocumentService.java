package com.skillstorm.taxtracker.services;

import java.net.URI;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.*;
import com.skillstorm.taxtracker.repositories.TaxDocumentRepository;
import com.skillstorm.taxtracker.dtos.TaxDocumentDTO;
import com.skillstorm.taxtracker.models.TaxDocument;

@Service
public class TaxDocumentService {

	@Value("${base-url}")
	private String baseURL;

	private TaxDocumentRepository repo;

	public TaxDocumentService(TaxDocumentRepository repo) {
		this.repo = repo;
	}

	// Find documents by category (optional)
	public ResponseEntity<Iterable<TaxDocument>> findAll(String startsWith) {

		Iterable<TaxDocument> documents;

		try {
			if (startsWith == null)
				documents = repo.findAll();
			else
				documents = repo.findByDocumentCategoryType(startsWith);

			if (!documents.iterator().hasNext())
				return ResponseEntity.noContent().build();
			else
				return ResponseEntity.ok(documents);
			
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

	// Create document
	public ResponseEntity<TaxDocument> createTaxDocument(TaxDocumentDTO dto) {
		try {
			TaxDocument saved = repo.save(new TaxDocument(0,dto.client(),dto.taxReturn(),dto.documentCategory()));
			return ResponseEntity.created(new URI(this.baseURL + "tax-document/" + saved.getId())).body(saved);
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

	// Update document
	public ResponseEntity<TaxDocument> updateTaxDocument(int id, TaxDocumentDTO dto) {
		try {
			if (repo.existsById(id))
				return ResponseEntity.ok(repo.save(new TaxDocument(0,dto.client(),dto.taxReturn(),dto.documentCategory())));
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

	// Delete document
	public ResponseEntity<Void> deleteById(int id) {
		try {
			repo.deleteById(id);
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

}
