package com.stumosys.shared;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the sup_product database table.
 * 
 */
@Entity
@Table(name="sup_product")
@NamedQuery(name="SupProduct.findAll", query="SELECT s FROM SupProduct s")
public class SupProduct implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int sup_product_ID;

	@Column(name="product_code")
	private String productCode;

	@Column(name="product_name")
	private String productName;

	private String status;

	//bi-directional many-to-one association to Product
	@ManyToOne
	@JoinColumn(name="product_ID")
	private Product product;

	public SupProduct() {
	}

	public int getSup_product_ID() {
		return this.sup_product_ID;
	}

	public void setSup_product_ID(int sup_product_ID) {
		this.sup_product_ID = sup_product_ID;
	}

	public String getProductCode() {
		return this.productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}