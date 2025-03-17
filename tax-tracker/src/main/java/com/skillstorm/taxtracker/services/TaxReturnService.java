package com.skillstorm.taxtracker.services;

import java.net.URI;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.*;
import org.springframework.web.server.ResponseStatusException;
import com.skillstorm.taxtracker.models.EmploymentSector;
import com.skillstorm.taxtracker.repositories.ClientRepository;
import com.skillstorm.taxtracker.repositories.CpaRepository;
import com.skillstorm.taxtracker.repositories.EmploymentSectorRepository;
import com.skillstorm.taxtracker.repositories.TaxReturnRepository;
import com.skillstorm.taxtracker.dtos.TaxReturnDTO;
import com.skillstorm.taxtracker.models.Client;
import com.skillstorm.taxtracker.models.Cpa;
import com.skillstorm.taxtracker.models.TaxReturn;

@Service
public class TaxReturnService {

	@Value("${base-url}")
	private String baseURL;

	private TaxReturnRepository repo;
	private ClientRepository clientRepo;
	private CpaRepository cpaRepo;
	private EmploymentSectorRepository employmentRepo;
	
    final int MAX_RETURNS_PER_CPA = 5;

	public TaxReturnService(TaxReturnRepository repo, ClientRepository clientRepo, CpaRepository cpaRepo, EmploymentSectorRepository employmentRepo) {
		this.repo = repo;
		this.clientRepo = clientRepo;
		this.cpaRepo = cpaRepo; 
		this.employmentRepo = employmentRepo;
	}

	// Find tax returns by client last name
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
	
	public ResponseEntity<Iterable<TaxReturn>> findByEmploymentSector(Integer employmentSectorId) {
	    try {
	        Iterable<TaxReturn> returns = repo.findByEmploymentSectorId(employmentSectorId);
	        return returns.iterator().hasNext() ? ResponseEntity.ok(returns) : ResponseEntity.noContent().build();
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}
	
	public ResponseEntity<Iterable<TaxReturn>> findByCpa(Integer cpaId) {
	    try {
	        Iterable<TaxReturn> returns = repo.findByCpaId(cpaId);
	        return returns.iterator().hasNext() ? ResponseEntity.ok(returns) : ResponseEntity.noContent().build();
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}
	
	// Find tax return by ID
    public ResponseEntity<TaxReturn> findTaxReturnById(int id) {
        return repo.findById(id)
                   .map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }


	// Create tax return
	public ResponseEntity<TaxReturn> createTaxReturn(TaxReturnDTO dto) {
	    try {
	        Client client = clientRepo.findById(dto.client().getId())
	                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

	        Cpa cpa = null;
	        if (dto.cpa() != null) {
	            cpa = cpaRepo.findById(dto.cpa().getId())
	                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
	        }

	        EmploymentSector employmentSector = employmentRepo.findById(dto.employmentSector().getId())
	                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
	        
	        if (repo.existsByClientIdAndYear(client.getId(), dto.year())) {
	            return ResponseEntity.status(HttpStatus.CONFLICT).body(null); 
	        }
	        
	        int returnCount = repo.countByCpaIdAndYear(cpa.getId(), dto.year());
	        
	        if (returnCount >= MAX_RETURNS_PER_CPA) {
	            return ResponseEntity.status(HttpStatus.CONFLICT)
	                    .body(null);
	        }
	        
	        TaxReturn saved = repo.save(new TaxReturn(
	                0, client, cpa, dto.year(), dto.status(),
	                dto.amountOwed(), dto.amountPaid(), dto.cost(),
	                null, null, employmentSector));

	        return ResponseEntity.created(new URI(this.baseURL + "tax-return/" + saved.getId())).body(saved);

	    } catch (ResponseStatusException e) {
	        return ResponseEntity.status(e.getStatusCode()).body(null);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}
	
	// Update a tax return
	public ResponseEntity<TaxReturn> updateTaxReturn(int id, TaxReturnDTO dto) {
	    try {
	        if (!repo.existsById(id)) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	        }

	        Client client = clientRepo.findById(dto.client().getId())
	                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

	        Cpa cpa = null;
	        if (dto.cpa() != null) {
	            cpa = cpaRepo.findById(dto.cpa().getId())
	                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
	        }

	        EmploymentSector employmentSector = employmentRepo.findById(dto.employmentSector().getId())
	                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
	        
	        if (repo.existsByClientIdAndYear(client.getId(), dto.year())) {
	            return ResponseEntity.status(HttpStatus.CONFLICT).body(null); 
	        }

	        TaxReturn updated = repo.save(new TaxReturn(
	                id, client, cpa, dto.year(), dto.status(),
	                dto.amountOwed(), dto.amountPaid(), dto.cost(),
	                dto.creationDate(), dto.updateDate(), employmentSector));

	        return ResponseEntity.ok(updated);

	    } catch (ResponseStatusException e) {
	        return ResponseEntity.status(e.getStatusCode()).body(null);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

	// Delete tax return
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
