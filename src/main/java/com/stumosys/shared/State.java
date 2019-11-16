package com.stumosys.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the state database table.
 * 
 */
@Entity
@Table(name="state")
@NamedQuery(name="State.findAll", query="SELECT s FROM State s")
public class State implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="state_code")
	private String stateCode;

	@Column(name="state_name")
	private String stateName;
	
	@Column(name="country")
	private String country;
	
	

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	//bi-directional many-to-one association to Postcode
	@OneToMany(mappedBy="state")
	private List<Postcode> postcodes;

	public State() {
	}

	public String getStateCode() {
		return this.stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getStateName() {
		return this.stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public List<Postcode> getPostcodes() {
		return this.postcodes;
	}

	public void setPostcodes(List<Postcode> postcodes) {
		this.postcodes = postcodes;
	}

	public Postcode addPostcode(Postcode postcode) {
		getPostcodes().add(postcode);
		postcode.setState(this);

		return postcode;
	}

	public Postcode removePostcode(Postcode postcode) {
		getPostcodes().remove(postcode);
		postcode.setState(null);

		return postcode;
	}

}