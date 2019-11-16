package com.stumosys.shared;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the parent_details database table.
 * 
 */
@Entity
@Table(name="parent_details")
@NamedQuery(name="ParentDetail.findAll", query="SELECT p FROM ParentDetail p")
public class ParentDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int has_parent_ID;

	@Column(name="emai_id")
	private String emaiId;

	@Column(name="first_name")
	private String firstName;

	@Column(name="last_name")
	private String lastName;

	@Column(name="phone_number")
	private String phoneNumber;
	
	@Column(name="country_code")
	private String countrycode;
	
	private String relation;

	//bi-directional one-to-one association to Parent
	@OneToOne
	@JoinColumn(name="parent_ID")
	private Parent parent;

	

	public ParentDetail() {
	}

	public String getCountrycode() {
		return countrycode;
	}
	public void setCountrycode(String countrycode) {
		this.countrycode = countrycode;
	}

	public String getEmaiId() {
		return this.emaiId;
	}

	public void setEmaiId(String emaiId) {
		this.emaiId = emaiId;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getRelation() {
		return this.relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}





	/**
	 * @return the has_parent_ID
	 */
	public int getHas_parent_ID() {
		return has_parent_ID;
	}



	/**
	 * @param has_parent_ID the has_parent_ID to set
	 */
	public void setHas_parent_ID(int has_parent_ID) {
		this.has_parent_ID = has_parent_ID;
	}



	/**
	 * @return the parent
	 */
	public Parent getParent() {
		return parent;
	}



	/**
	 * @param parent the parent to set
	 */
	public void setParent(Parent parent) {
		this.parent = parent;
	}

	

}