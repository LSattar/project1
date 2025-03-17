package com.skillstorm.taxtracker.repositories;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.skillstorm.taxtracker.models.TaxReturn;

@Repository
public interface TaxReturnRepository extends CrudRepository<TaxReturn, Integer> {

	// Search for tax return by client last name, employment sector, or status
    @Query("SELECT t FROM TaxReturn t " +
            "JOIN t.client c " +
            "JOIN c.employmentSector e " +
            "WHERE (:lastName IS NULL OR LOWER(c.lastName) LIKE LOWER(CONCAT(:lastName, '%'))) " +
            "AND (:employmentSectorId IS NULL OR e.id = :employmentSectorId) " +
            "AND (:status IS NULL OR LOWER(t.status) = LOWER(:status))")
     Iterable<TaxReturn> findByFilters(
             @Param("lastName") String lastName,
             @Param("employmentSectorId") Integer employmentSectorId,
             @Param("status") String status);
    
    @Query("SELECT t FROM TaxReturn t WHERE t.employmentSector.id = :employmentSectorId")
    Iterable<TaxReturn> findByEmploymentSectorId(@Param("employmentSectorId") Integer employmentSectorId);
	
}
