package com.stumosys.shared;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the user_product database table.
 * 
 */
@Entity
@Table(name="user_product")
@NamedQuery(name="UserProduct.findAll", query="SELECT u FROM UserProduct u")
public class UserProduct implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int has_product_ID;

	private String status;

	//bi-directional many-to-one association to Product
	@ManyToOne
	@JoinColumn(name="product_ID")
	private Product product;

	//bi-directional many-to-one association to Person
	@ManyToOne
	@JoinColumn(name="person_ID")
	private Person person;
	
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

		//bi-directional many-to-one association to Person
		@ManyToOne
		@JoinColumn(name="has_user_ID")
		private User user;


	public UserProduct() {
	}

	public int getHas_product_ID() {
		return this.has_product_ID;
	}

	public void setHas_product_ID(int has_product_ID) {
		this.has_product_ID = has_product_ID;
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

	public Person getPerson() {
		return this.person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

}