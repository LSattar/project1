package com.skillstorm.taxtracker.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tax_document")
public class TaxDocument {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "client_id")
	private Client client;
	
	@Column(name = "tax_return_id")
	private TaxReturn taxReturn;
	
	@Column(name = "document_category_id")
	private DocumentCategory documentCategory;

	public TaxDocument() {
		super();
	}

	public TaxDocument(int id, Client client, TaxReturn taxReturn, DocumentCategory documentCategory) {
		super();
		this.id = id;
		this.client = client;
		this.taxReturn = taxReturn;
		this.documentCategory = documentCategory;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public TaxReturn getTaxReturn() {
		return taxReturn;
	}

	public void setTaxReturn(TaxReturn taxReturn) {
		this.taxReturn = taxReturn;
	}

	public DocumentCategory getDocumentCategory() {
		return documentCategory;
	}

	public void setDocumentCategory(DocumentCategory documentCategory) {
		this.documentCategory = documentCategory;
	}
	
}
