package com.stumosys.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the product database table.
 * 
 */
@Entity
@Table(name="product")
@NamedQuery(name="Product.findAll", query="SELECT p FROM Product p")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int product_ID;

	@Column(name="product_code")
	private String productCode;

	@Column(name="product_name")
	private String productName;

	private String status;
	
	private String roll;

	public String getRoll() {
		return roll;
	}

	public void setRoll(String roll) {
		this.roll = roll;
	}

	//bi-directional many-to-one association to SupProduct
	@OneToMany(mappedBy="product")
	private List<SupProduct> supProducts;

	//bi-directional many-to-one association to UserProduct
	@OneToMany(mappedBy="product")
	private List<UserProduct> userProducts;

	public Product() {
	}

	public int getProduct_ID() {
		return this.product_ID;
	}

	public void setProduct_ID(int product_ID) {
		this.product_ID = product_ID;
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

	public List<SupProduct> getSupProducts() {
		return this.supProducts;
	}

	public void setSupProducts(List<SupProduct> supProducts) {
		this.supProducts = supProducts;
	}

	public SupProduct addSupProduct(SupProduct supProduct) {
		getSupProducts().add(supProduct);
		supProduct.setProduct(this);

		return supProduct;
	}

	public SupProduct removeSupProduct(SupProduct supProduct) {
		getSupProducts().remove(supProduct);
		supProduct.setProduct(null);

		return supProduct;
	}

	public List<UserProduct> getUserProducts() {
		return this.userProducts;
	}

	public void setUserProducts(List<UserProduct> userProducts) {
		this.userProducts = userProducts;
	}

	public UserProduct addUserProduct(UserProduct userProduct) {
		getUserProducts().add(userProduct);
		userProduct.setProduct(this);

		return userProduct;
	}

	public UserProduct removeUserProduct(UserProduct userProduct) {
		getUserProducts().remove(userProduct);
		userProduct.setProduct(null);

		return userProduct;
	}

}