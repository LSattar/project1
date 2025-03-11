package com.skillstorm.taxtracker.repositories;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.skillstorm.taxtracker.models.Payment;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, Integer> {

	@Query("SELECT p FROM Payment p " +
	           "JOIN p.taxReturn t " +
	           "JOIN t.client c " +
	           "WHERE LOWER(c.lastName) = LOWER(:lastName)")
	    Iterable<Payment> findByClientLastName(@Param("lastName") String lastName);
	
}
