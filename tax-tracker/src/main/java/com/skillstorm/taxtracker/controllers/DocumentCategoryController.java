package com.skillstorm.taxtracker.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.skillstorm.taxtracker.dtos.DocumentCategoryDTO;
import com.skillstorm.taxtracker.models.DocumentCategory;
import com.skillstorm.taxtracker.services.DocumentCategoryService;

@RestController
@RequestMapping("/document-category")
public class DocumentCategoryController {

	private DocumentCategoryService service;

	public DocumentCategoryController(DocumentCategoryService service) {
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<Iterable<DocumentCategory>> findAll(@RequestParam(required = false) String startsWith) {
		return service.findAll(startsWith);
	}

	@PostMapping
	public ResponseEntity<DocumentCategory> createDocumentCategory(@RequestBody DocumentCategoryDTO dto) {
		return service.createDocumentCategory(dto);
	}

	@PutMapping("/{id}")
	public ResponseEntity<DocumentCategory> udpateDocumentCategory(@PathVariable int id, @RequestBody DocumentCategoryDTO dto) {
		return service.updateDocumentCategory(id, dto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable int id) {
		return service.deleteById(id);
	}

}
