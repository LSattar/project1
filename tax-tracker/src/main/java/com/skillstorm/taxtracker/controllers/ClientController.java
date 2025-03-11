package com.skillstorm.taxtracker.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.skillstorm.taxtracker.dtos.ClientDTO;
import com.skillstorm.taxtracker.models.Client;
import com.skillstorm.taxtracker.services.ClientService;

@RestController
@RequestMapping("/client")
public class ClientController {

	private ClientService service;

	public ClientController(ClientService service) {
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<Iterable<Client>> findAll(@RequestParam(required = false) String startsWith) {
		return service.findAll(startsWith);
	}

	@PostMapping
	public ResponseEntity<Client> createClient(@RequestBody ClientDTO dto) {
		return service.createClient(dto);
	}

	@PutMapping
	public ResponseEntity<Client> udpateClient(@PathVariable int id, @RequestBody ClientDTO dto) {
		return service.updateClient(id, dto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable int id) {
		return service.deleteById(id);
	}

}
