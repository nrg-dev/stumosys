package com.stumosys.shared;

import java.io.Serializable;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the class_table database table.
 * 
 */
@Entity
@Table(name="class_table")
@NamedQuery(name="ClassTable.findAll", query="SELECT c FROM ClassTable c")
public class ClassTable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int class_table_ID;

	private String day;

	private String status;

	private String year;
	
	private String month;

	//bi-directional many-to-one association to School
	@ManyToOne
	@JoinColumn(name="school_ID")
	private School school;
	
	//bi-directional many-to-one association to Class
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="class_ID")
	private Class classz;
	//EAGER
	//bi-directional many-to-one association to ClassTimeTable
	@OneToMany(mappedBy="classTable",fetch=FetchType.EAGER,cascade = CascadeType.ALL)
	private List<ClassTimeTable> classTimeTables = new ArrayList<ClassTimeTable>();


	public ClassTable() {
	}

	public int getClass_table_ID() {
		return this.class_table_ID;
	}

	public void setClass_table_ID(int class_table_ID) {
		this.class_table_ID = class_table_ID;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDay() {
		return this.day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getYear() {
		return this.year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public List<ClassTimeTable> getClassTimeTables() {
		return this.classTimeTables;
	}

	public void setClassTimeTables(List<ClassTimeTable> classTimeTables) {
		this.classTimeTables = classTimeTables;
	}

	public ClassTimeTable addClassTimeTable(ClassTimeTable classTimeTable) {
		getClassTimeTables().add(classTimeTable);
		classTimeTable.setClassTable(this);

		return classTimeTable;
	}

	public ClassTimeTable removeClassTimeTable(ClassTimeTable classTimeTable) {
		getClassTimeTables().remove(classTimeTable);
		classTimeTable.setClassTable(null);

		return classTimeTable;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public Class getClassz() {
		return classz;
	}

	public void setClassz(Class classz) {
		this.classz = classz;
	}

}