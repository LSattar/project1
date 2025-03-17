package com.skillstorm.taxtracker.services;

import java.net.URI;
import java.time.LocalDate;

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

	public ResponseEntity<Iterable<TaxReturn>> findAllByYear(Integer year) {
	    try {
	        Iterable<TaxReturn> returns;

	        if (year == null)
	            returns = repo.findAll(); 
	        else
	            returns = repo.findByYear(year);

	        if (!returns.iterator().hasNext())
	            return ResponseEntity.noContent().build();
	        else
	            return ResponseEntity.ok(returns);

	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

	
	// Find tax return by employment sector
	public ResponseEntity<Iterable<TaxReturn>> findByEmploymentSector(Integer employmentSectorId) {
	    if (employmentSectorId == null || !employmentRepo.existsById(employmentSectorId)) {
	        return ResponseEntity.badRequest().body(null);
	    }

	    try {
	        Iterable<TaxReturn> returns = repo.findByEmploymentSectorId(employmentSectorId);
	        return returns.iterator().hasNext() ? ResponseEntity.ok(returns) : ResponseEntity.noContent().build();
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}
	
	// Find tax return by CPA
	public ResponseEntity<Iterable<TaxReturn>> findByCpa(Integer cpaId) {
	    if (cpaId == null || !cpaRepo.existsById(cpaId)) {
	        return ResponseEntity.badRequest().body(null);
	    }

	    try {
	        Iterable<TaxReturn> returns = repo.findByCpaId(cpaId);
	        return returns.iterator().hasNext() ? ResponseEntity.ok(returns) : ResponseEntity.noContent().build();
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}
	
	// Find tax return by client
	public ResponseEntity<Iterable<TaxReturn>> findByClient(Integer clientId) {
	    if (clientId == null || !clientRepo.existsById(clientId)) {
	        return ResponseEntity.badRequest().body(null);
	    }

	    try {
	        Iterable<TaxReturn> returns = repo.findByClientId(clientId);
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
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .header("X-Error-Message", "A tax return already exists for this client in the specified year.")
                        .body(null);
            }

            if (cpa != null) {
                int returnCount = repo.countByCpaIdAndYear(cpa.getId(), dto.year());
                if (returnCount >= MAX_RETURNS_PER_CPA) {
                    return ResponseEntity.status(HttpStatus.CONFLICT)
                            .header("X-Error-Message", "The CPA has reached the maximum allowed tax returns for this year.")
                            .body(null);
                }
            }

            LocalDate now = LocalDate.now();
            TaxReturn newTaxReturn = new TaxReturn(
                    0, client, cpa, dto.year(), dto.status(),
                    dto.amountOwed(), dto.amountPaid(), dto.cost(),
                    employmentSector,
                    dto.totalIncome(), dto.adjustments(), dto.filingStatus(),
                    now, null
            );

            TaxReturn saved = repo.save(newTaxReturn);

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

	        TaxReturn existingTaxReturn = repo.findById(id)
	                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

	        if (repo.existsByClientIdAndYear(client.getId(), dto.year()) && id != existingTaxReturn.getId()) {
	            return ResponseEntity.status(HttpStatus.CONFLICT)
	                    .header("X-Error-Message", "The client already has a tax return for this year")
	                    .body(null);
	        }
	        
	        int returnCount = repo.countByCpaIdAndYear(cpa.getId(), dto.year());
	        
	        if (returnCount >= MAX_RETURNS_PER_CPA) {
	            return ResponseEntity.status(HttpStatus.CONFLICT).header("X-Error-Message", "The CPA has reached the maximum allowed tax returns for this year.")
	                    .body(null);
	        }

	        TaxReturn updated = new TaxReturn(
	                id, client, cpa, dto.year(), dto.status(),
	                dto.amountOwed(), dto.amountPaid(), dto.cost(),
	                existingTaxReturn.getCreationDate(), null, employmentSector
	        );

	        repo.save(updated);
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
