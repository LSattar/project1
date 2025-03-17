package com.skillstorm.taxtracker.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.skillstorm.taxtracker.dtos.TaxDocumentDTO;
import com.skillstorm.taxtracker.models.TaxDocument;
import com.skillstorm.taxtracker.services.TaxDocumentService;

@RestController
@RequestMapping("/tax-document")
public class TaxDocumentController {

	private TaxDocumentService service;

	public TaxDocumentController(TaxDocumentService service) {
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<Iterable<TaxDocument>> findAll(@RequestParam(required = false) String startsWith) {
		return service.findAll(startsWith);
	}

	@PostMapping
	public ResponseEntity<TaxDocument> createTaxDocument(@RequestBody TaxDocumentDTO dto) {
		return service.createTaxDocument(dto);
	}

	@PutMapping("/{id}")
	public ResponseEntity<TaxDocument> udpateTaxDocument(@PathVariable int id, @RequestBody TaxDocumentDTO dto) {
		return service.updateTaxDocument(id, dto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable int id) {
		return service.deleteById(id);
	}

}
