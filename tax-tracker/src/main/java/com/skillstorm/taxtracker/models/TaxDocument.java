package com.skillstorm.taxtracker.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tax_document")
public class TaxDocument {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "client_id")
	private Client client;
	
	@ManyToOne
	@JoinColumn(name = "tax_return_id")
	private TaxReturn taxReturn;
	
	@ManyToOne
	@JoinColumn(name = "document_category_id")
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
