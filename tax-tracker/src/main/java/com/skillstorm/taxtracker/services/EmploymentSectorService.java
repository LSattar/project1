package com.skillstorm.taxtracker.services;

import java.net.URI;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.*;
import com.skillstorm.taxtracker.repositories.EmploymentSectorRepository;
import com.skillstorm.taxtracker.dtos.EmploymentSectorDTO;
import com.skillstorm.taxtracker.models.EmploymentSector;

@Service
public class EmploymentSectorService {

	@Value("${base-url}")
	private String baseURL;

	private EmploymentSectorRepository repo;

	public EmploymentSectorService(EmploymentSectorRepository repo) {
		this.repo = repo;
	}

	// Find employment sectors by sector name (optional)
	public ResponseEntity<Iterable<EmploymentSector>> findAll(String startsWith) {

		Iterable<EmploymentSector> sectors;

		try {
			if (startsWith == null)
				sectors = repo.findAll();
			else
				sectors = repo.findByEmploymentSectorNameStartingWith(startsWith);

			if (!sectors.iterator().hasNext())
				return ResponseEntity.noContent().build();
			else
				return ResponseEntity.ok(sectors);
			
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

	// Create employment sector
	public ResponseEntity<EmploymentSector> createEmploymentSector(EmploymentSectorDTO dto) {
		try {
			EmploymentSector saved = repo.save(new EmploymentSector(0,dto.employmentSectorName()));
			return ResponseEntity.created(new URI(this.baseURL + "employment-sector/" + saved.getId())).body(saved);
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

	// Update employment sector
	public ResponseEntity<EmploymentSector> updateEmploymentSector(int id, EmploymentSectorDTO dto) {
		try {
			if (repo.existsById(id))
				return ResponseEntity.ok(repo.save(new EmploymentSector(0,dto.employmentSectorName())));
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

	// Delete employment sector
	public ResponseEntity<Void> deleteById(int id) {
		try {
			repo.deleteById(id);
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

}
