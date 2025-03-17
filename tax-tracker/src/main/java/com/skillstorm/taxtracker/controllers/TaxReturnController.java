package com.skillstorm.taxtracker.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.skillstorm.taxtracker.dtos.TaxReturnDTO;
import com.skillstorm.taxtracker.models.TaxReturn;
import com.skillstorm.taxtracker.services.TaxReturnService;

@RestController
@RequestMapping("/tax-return")
public class TaxReturnController {

	private TaxReturnService service;

	public TaxReturnController(TaxReturnService service) {
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<Iterable<TaxReturn>> findAll(@RequestParam(required = false) String startsWith) {
		return service.findAll(startsWith);
	}
	
    // Find tax returns by employment sector
    @GetMapping("/employment-sector/{sectorId}")
    public ResponseEntity<Iterable<TaxReturn>> findByEmploymentSector(@PathVariable Integer sectorId) {
        return service.findByEmploymentSector(sectorId);
    }


	@PostMapping
	public ResponseEntity<TaxReturn> createTaxReturn(@RequestBody TaxReturnDTO dto) {
		return service.createTaxReturn(dto);
	}

	@PutMapping("/{id}")
	public ResponseEntity<TaxReturn> udpateTaxReturn(@PathVariable int id, @RequestBody TaxReturnDTO dto) {
		return service.updateTaxReturn(id, dto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable int id) {
		return service.deleteById(id);
	}

}
