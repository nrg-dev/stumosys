package com.stumosys.shared;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the exam_table database table.
 * 
 */
@Entity
@Table(name="exam_table")
@NamedQuery(name="ExamTable.findAll", query="SELECT e FROM ExamTable e")
public class ExamTable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int exam_table_ID;

	@Column(name="class")
	private String class_name;

	@Column(name="exam_title")
	private String examTitle;
	
	@Temporal(TemporalType.DATE)
	@Column(name="start_date")
	private Date startDate;
	
	//bi-directional many-to-one association to School
		@ManyToOne
		@JoinColumn(name="school_ID")
		private School school;
		
	private String status;

	@Column(name="total_time")
	private String totalTime;

	//bi-directional many-to-one association to TimeTable
	@OneToMany(mappedBy="examTable")
	private List<TimeTable> timeTables;

	public ExamTable() {
	}

	public int getExam_table_ID() {
		return this.exam_table_ID;
	}

	public void setExam_table_ID(int exam_table_ID) {
		this.exam_table_ID = exam_table_ID;
	}


	
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getExamTitle() {
		return this.examTitle;
	}

	public void setExamTitle(String examTitle) {
		this.examTitle = examTitle;
	}


	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTotalTime() {
		return this.totalTime;
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}

	public List<TimeTable> getTimeTables() {
		return this.timeTables;
	}

	public void setTimeTables(List<TimeTable> timeTables) {
		this.timeTables = timeTables;
	}

	public TimeTable addTimeTable(TimeTable timeTable) {
		getTimeTables().add(timeTable);
		timeTable.setExamTable(this);

		return timeTable;
	}

	public TimeTable removeTimeTable(TimeTable timeTable) {
		getTimeTables().remove(timeTable);
		timeTable.setExamTable(null);

		return timeTable;
	}

	public String getClass_name() {
		return class_name;
	}

	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}

}