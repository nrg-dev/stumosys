package com.stumosys.shared;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the indexs database table.
 * 
 */
@Entity
@Table(name="activities")
@NamedQuery(name="Activities.findAll", query="SELECT i FROM Activities i")
public class Activities implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int activities_id;

	private String activity;
	
	@ManyToOne
	@JoinColumn(name="school_ID")
	private School school;

	private String status;

	public Activities() {
	}

	public int getActivities_id() {
		return activities_id;
	}

	public void setActivities_id(int activities_id) {
		this.activities_id = activities_id;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	

}