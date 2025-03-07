package com.skillstorm.taxtracker.dtos;

import com.skillstorm.taxtracker.models.Client;
import com.skillstorm.taxtracker.models.DocumentCategory;
import com.skillstorm.taxtracker.models.TaxReturn;

public record TaxDocumentDTO(Client client, TaxReturn taxReturn, DocumentCategory documentCategory) {

}
