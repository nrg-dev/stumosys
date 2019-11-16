package com.stumosys.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the admin database table.
 * 
 */
@Entity
@Table(name="admin")
@NamedQuery(name="Admin.findAll", query="SELECT a FROM Admin a")
public class Admin implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int admin_ID;

	@Column(name="admin_name")
	private String adminName;

	@Temporal(TemporalType.DATE)
	@Column(name="created_date")
	private Date createdDate;

	@Column(name="created_time")
	private Timestamp createdTime;

	private String status;

	//bi-directional many-to-one association to Person
	@ManyToOne
	@JoinColumn(name="person_ID")
	private Person person;

	public Admin() {
	}

	public int getAdmin_ID() {
		return this.admin_ID;
	}

	public void setAdmin_ID(int admin_ID) {
		this.admin_ID = admin_ID;
	}

	public String getAdminName() {
		return this.adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Person getPerson() {
		return this.person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

}