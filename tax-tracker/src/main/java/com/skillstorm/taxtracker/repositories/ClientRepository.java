package com.skillstorm.taxtracker.repositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.skillstorm.taxtracker.models.Client;

@Repository
public interface ClientRepository extends CrudRepository<Client, Integer> {

	Iterable<Client> findByLastNameStartingWith(String startsWith);
	
}
