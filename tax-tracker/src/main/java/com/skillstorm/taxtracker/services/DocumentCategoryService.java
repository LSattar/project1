package com.skillstorm.taxtracker.services;

import java.net.URI;
import org.springframework.beans.factory.annotation.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.*;
import com.skillstorm.taxtracker.repositories.DocumentCategoryRepository;
import com.skillstorm.taxtracker.repositories.TaxDocumentRepository;
import com.skillstorm.taxtracker.dtos.DocumentCategoryDTO;
import com.skillstorm.taxtracker.models.DocumentCategory;

@Service
public class DocumentCategoryService {

	@Value("${base-url}")
	private String baseURL;

	private DocumentCategoryRepository repo;
	private TaxDocumentRepository taxDocumentRepo;

	public DocumentCategoryService(DocumentCategoryRepository repo, TaxDocumentRepository taxDocumentRepo) {
		this.repo = repo;
		this.taxDocumentRepo = taxDocumentRepo;
	}

	// Find document categories by category name (optional)
	public ResponseEntity<Iterable<DocumentCategory>> findAll(String startsWith) {

		Iterable<DocumentCategory> categories;

		try {
			if (startsWith == null)
				categories = repo.findAll();
			else
				categories = repo.findByDocumentTypeStartingWith(startsWith);

			if (!categories.iterator().hasNext())
				return ResponseEntity.noContent().build();
			else
				return ResponseEntity.ok(categories);

		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

	// Create document category
	public ResponseEntity<DocumentCategory> createDocumentCategory(DocumentCategoryDTO dto) {
		try {
			DocumentCategory saved = repo.save(new DocumentCategory(0, dto.documentType(), dto.documentDescription()));
			return ResponseEntity.created(new URI(this.baseURL + "document-category/" + saved.getId())).body(saved);

		} catch (DataIntegrityViolationException e) {
			// Duplicate documentType detected
			return ResponseEntity.status(HttpStatus.CONFLICT).body(null);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	// Update document category
	public ResponseEntity<DocumentCategory> updateDocumentCategory(int id, DocumentCategoryDTO dto) {
		try {
			if (repo.existsById(id))
				return ResponseEntity
						.ok(repo.save(new DocumentCategory(id, dto.documentType(), dto.documentDescription())));
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
		} catch (DataIntegrityViolationException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
		} catch (Exception e) {
		}
		return ResponseEntity.status(500).build();
	}

	// Delete document category
	public ResponseEntity<Void> deleteById(int id) {
		try {
			if (!repo.existsById(id)) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			else if (taxDocumentRepo.existsById(id)) {
				return ResponseEntity.status(HttpStatus.CONFLICT).build();
			} else {
				repo.deleteById(id);
				return ResponseEntity.noContent().build();
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
