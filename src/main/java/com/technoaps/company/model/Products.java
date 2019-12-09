package com.technoaps.company.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Setter;

@Entity
@Table(name = "Products", schema="technoaps")
@Setter
public class Products {


	private long productId;
	private String productName;
	private String productDescription;
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "product_id", columnDefinition = "uniqueidentifier")
	public long getProductId() {
		return productId;
	}
	
	@Basic
	@Column(name = "product_name")
	public String getProductName() {
		return productName;
	}
	
	@Basic
	@Column(name = "product_desc")
	public String getProductDescription() {
		return productDescription;
	}
	

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	
}
