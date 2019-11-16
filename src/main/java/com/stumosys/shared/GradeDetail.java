package com.stumosys.shared;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the grade_details database table.
 * 
 */
@Entity
@Table(name="grade_details")
@NamedQuery(name="GradeDetail.findAll", query="SELECT g FROM GradeDetail g")
public class GradeDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int grade_details_ID;

	@ManyToOne
	@JoinColumn(name="exam_table_ID")
	private ExamTable examTableId;

	@Column(name="from_mark")
	private String fromMark;

	private String grade;

	@Column(name="to_mark")
	private String toMark;

	private String year;
	
	private String status;
	
	private String examResult;
	
	@Column(name="school_ID")
	private String schoolID;

	public String getExamResult() {
		return examResult;
	}
	public void setExamResult(String examResult) {
		this.examResult = examResult;
	}	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public GradeDetail() {
	}

	public String getSchoolID() {
		return schoolID;
	}


	public void setSchoolID(String schoolID) {
		this.schoolID = schoolID;
	}
	public int getGrade_details_ID() {
		return this.grade_details_ID;
	}

	public void setGrade_details_ID(int grade_details_ID) {
		this.grade_details_ID = grade_details_ID;
	}

	public ExamTable getExamTableId() {
		return examTableId;
	}

	public void setExamTableId(ExamTable examTableId) {
		this.examTableId = examTableId;
	}

	public String getFromMark() {
		return this.fromMark;
	}

	public void setFromMark(String fromMark) {
		this.fromMark = fromMark;
	}

	public String getGrade() {
		return this.grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getToMark() {
		return this.toMark;
	}

	public void setToMark(String toMark) {
		this.toMark = toMark;
	}

	public String getYear() {
		return this.year;
	}

	public void setYear(String year) {
		this.year = year;
	}

}