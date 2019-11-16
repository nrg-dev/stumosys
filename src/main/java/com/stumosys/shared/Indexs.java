package com.stumosys.shared;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the indexs database table.
 * 
 */
@Entity
@Table(name="indexs")
@NamedQuery(name="Indexs.findAll", query="SELECT i FROM Indexs i")
public class Indexs implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String module;

	private String value;

	public Indexs() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getModule() {
		return this.module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}