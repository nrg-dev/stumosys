package com.stumosys.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the time_table database table.
 * 
 */
@Entity
@Table(name="time_table")
@NamedQuery(name="TimeTable.findAll", query="SELECT t FROM TimeTable t")
public class TimeTable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int time_table_ID;

	@Column(name="end_time")
	private String endTime;

	@Temporal(TemporalType.DATE)
	@Column(name="exam_date")
	private Date examDate;

	@Column(name="exam_day")
	private String examDay;

	@Column(name="exam_shift")
	private String examShift;

	@Column(name="room_no")
	private String roomNo;

	@Column(name="start_time")
	private String startTime;

	private String subject;

	@Column(name="subject_code")
	private String subjectCode;

	//bi-directional many-to-one association to ExamTable
	@ManyToOne
	@JoinColumn(name="exam_table_ID")
	private ExamTable examTable;

	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public TimeTable() {
	}

	public int getTime_table_ID() {
		return this.time_table_ID;
	}

	public void setTime_table_ID(int time_table_ID) {
		this.time_table_ID = time_table_ID;
	}

	public String getEndTime() {
		return this.endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Date getExamDate() {
		return this.examDate;
	}

	public void setExamDate(Date examDate) {
		this.examDate = examDate;
	}

	public String getExamDay() {
		return this.examDay;
	}

	public void setExamDay(String examDay) {
		this.examDay = examDay;
	}

	public String getExamShift() {
		return this.examShift;
	}

	public void setExamShift(String examShift) {
		this.examShift = examShift;
	}

	public String getRoomNo() {
		return this.roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public String getStartTime() {
		return this.startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSubjectCode() {
		return this.subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public ExamTable getExamTable() {
		return this.examTable;
	}

	public void setExamTable(ExamTable examTable) {
		this.examTable = examTable;
	}

}