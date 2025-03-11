package com.skillstorm.taxtracker.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.skillstorm.taxtracker.models.DocumentCategory;

@Repository
public interface DocumentCategoryRepository extends CrudRepository<DocumentCategory, Integer> {

	Iterable<DocumentCategory> findByDocumentTypeStartingWith(String startsWith);

	
}
