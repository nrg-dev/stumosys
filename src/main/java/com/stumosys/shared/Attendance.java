package com.stumosys.shared;

import java.io.Serializable;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the attendance database table.
 * 
 */
@Entity
@Table(name="attendance")
@NamedQuery(name="Attendance.findAll", query="SELECT a FROM Attendance a")
public class Attendance implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int attendance_ID;

	@Column(name="class")
	private String class_;

	@Temporal(TemporalType.DATE)
	private Date date;

	private String section;

	// 	@OneToMany(mappedBy="userbooking",cascade = CascadeType.ALL)

	//bi-directional many-to-one association to Attendanceclass
	//@OneToMany(mappedBy="attendance")
	@OneToMany(mappedBy="attendance",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Attendanceclass> attendanceclasses = new ArrayList<Attendanceclass>();

	//bi-directional many-to-one association to School
	@ManyToOne
	@JoinColumn(name="school_ID")
	private School school;
	
	//bi-directional many-to-one association to Person
	@ManyToOne
	@JoinColumn(name="person_ID")
	private Person person;
	
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public Attendance() {
	}

	public int getAttendance_ID() {
		return this.attendance_ID;
	}

	public void setAttendance_ID(int attendance_ID) {
		this.attendance_ID = attendance_ID;
	}

	public String getClass_() {
		return this.class_;
	}

	public void setClass_(String class_) {
		this.class_ = class_;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getSection() {
		return this.section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public List<Attendanceclass> getAttendanceclasses() {
		return this.attendanceclasses;
	}

	public void setAttendanceclasses(List<Attendanceclass> attendanceclasses) {
		this.attendanceclasses = attendanceclasses;
	}

	public Attendanceclass addAttendanceclass(Attendanceclass attendanceclass) {
		getAttendanceclasses().add(attendanceclass);
		attendanceclass.setAttendance(this);

		return attendanceclass; 
	}

	public Attendanceclass removeAttendanceclass(Attendanceclass attendanceclass) {
		getAttendanceclasses().remove(attendanceclass);
		attendanceclass.setAttendance(null);

		return attendanceclass;
	}

}