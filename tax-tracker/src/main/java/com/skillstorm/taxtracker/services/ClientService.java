package com.skillstorm.taxtracker.services;

import java.net.URI;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.*;
import com.skillstorm.taxtracker.repositories.ClientRepository;
import com.skillstorm.taxtracker.dtos.ClientDTO;
import com.skillstorm.taxtracker.models.Client;

@Service
public class ClientService {

	@Value("${base-url}")
	private String baseURL;

	private ClientRepository repo;

	public ClientService(ClientRepository repo) {
		this.repo = repo;
	}

	// Find clients by last name (optional)
	public ResponseEntity<Iterable<Client>> findAll(String startsWith) {

		Iterable<Client> clients;

		try {
			if (startsWith == null)
				clients = repo.findAll();
			else
				clients = repo.findByLastNameStartingWith(startsWith);

			if (!clients.iterator().hasNext())
				return ResponseEntity.noContent().build();
			else
				return ResponseEntity.ok(clients);
			
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

	// Create client
	public ResponseEntity<Client> createClient(ClientDTO dto) {
		try {
			Client saved = repo.save(new Client(0, dto.firstName(), dto.lastName(), dto.ssn(), dto.dob(), dto.phone(),
					dto.address1(), dto.address2(), dto.city(), dto.state(), dto.zip(), dto.employmentSector()));
			return ResponseEntity.created(new URI(this.baseURL + "cook/" + saved.getId())).body(saved);
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

	// Update client
	public ResponseEntity<Client> updateClient(int id, ClientDTO dto) {
		try {
			if (repo.existsById(id))
				return ResponseEntity.ok(repo.save(new Client(id, dto.firstName(), dto.lastName(), dto.ssn(), dto.dob(),
						dto.phone(), dto.address1(), dto.address2(), dto.city(), dto.state(), dto.zip(),
						dto.employmentSector())));
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

	// Delete client
	public ResponseEntity<Void> deleteById(int id) {
		try {
			repo.deleteById(id);
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

}
