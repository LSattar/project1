package com.skillstorm.taxtracker.services;

import java.net.URI;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.*;
import com.skillstorm.taxtracker.repositories.DocumentCategoryRepository;
import com.skillstorm.taxtracker.dtos.DocumentCategoryDTO;
import com.skillstorm.taxtracker.models.DocumentCategory;

@Service
public class DocumentCategoryService {

	@Value("${base-url}")
	private String baseURL;

	private DocumentCategoryRepository repo;

	public DocumentCategoryService(DocumentCategoryRepository repo) {
		this.repo = repo;
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
			DocumentCategory saved = repo.save(new DocumentCategory(0,dto.documentType(),dto.documentDescription()));
			return ResponseEntity.created(new URI(this.baseURL + "document-category/" + saved.getId())).body(saved);
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

	// Update document category
	public ResponseEntity<DocumentCategory> updateDocumentCategory(int id, DocumentCategoryDTO dto) {
		try {
			if (repo.existsById(id))
				return ResponseEntity.ok(repo.save(new DocumentCategory(0,dto.documentType(),dto.documentDescription())));
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
