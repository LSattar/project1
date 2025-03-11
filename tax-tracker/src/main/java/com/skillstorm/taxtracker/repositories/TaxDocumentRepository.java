package com.skillstorm.taxtracker.repositories;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.skillstorm.taxtracker.models.TaxDocument;

@Repository
public interface TaxDocumentRepository extends CrudRepository<TaxDocument, Integer> {

	//Find document by document type
	@Query("SELECT td FROM TaxDocument td " +
	           "JOIN td.documentCategory dc " +
	           "WHERE LOWER(dc.documentType) = LOWER(:documentType)")
	    Iterable<TaxDocument> findByDocumentCategoryType(@Param("documentType") String documentType);

}
