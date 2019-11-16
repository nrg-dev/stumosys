package com.stumosys.shared;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the teacher_attendance database table.
 * 
 */
@Entity
@Table(name="teacher_attendance")
@NamedQuery(name="TeacherAttendance.findAll", query="SELECT t FROM TeacherAttendance t")
public class TeacherAttendance implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="teacher_attendance_id")
	private int teacherAttendanceId;

	@Temporal(TemporalType.DATE)
	@Column(name="created_date")
	private Date createdDate;

	@Temporal(TemporalType.DATE)
	private Date date;

	private String inTime;

	@Column(name="leave_type")
	private String leaveType;

	private int lunch_Break;

	private String out_Time;

	private String status;

	
	@Temporal(TemporalType.DATE)
	@Column(name="updated_date")
	private Date updatedDate;
	
	//bi-directional many-to-one association to Teacher
	@ManyToOne
	@JoinColumn(name="teacher_ID")
	private Teacher teacher;

	//bi-directional many-to-one association to School
		@ManyToOne
		@JoinColumn(name="school_ID")
		private School school;
	
	
	public School getSchool() {
			return school;
		}


		public void setSchool(School school) {
			this.school = school;
		}


	public TeacherAttendance() {
	}

	
	public Teacher getTeacher() {
		return teacher;
	}


	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}


	public int getTeacherAttendanceId() {
		return this.teacherAttendanceId;
	}

	public void setTeacherAttendanceId(int teacherAttendanceId) {
		this.teacherAttendanceId = teacherAttendanceId;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getLeaveType() {
		return this.leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public int getLunch_Break() {
		return this.lunch_Break;
	}

	public void setLunch_Break(int lunch_Break) {
		this.lunch_Break = lunch_Break;
	}



	public String getInTime() {
		return inTime;
	}


	public void setInTime(String inTime) {
		this.inTime = inTime;
	}


	public String getOut_Time() {
		return out_Time;
	}


	public void setOut_Time(String out_Time) {
		this.out_Time = out_Time;
	}


	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

}