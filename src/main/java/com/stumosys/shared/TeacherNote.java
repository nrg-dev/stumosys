package com.stumosys.shared;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the teacher_notes database table.
 * 
 */
@Entity
@Table(name="teacher_notes")
@NamedQuery(name="TeacherNote.findAll", query="SELECT t FROM TeacherNote t")
public class TeacherNote implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="teacher_notes_id")
	private int teacherNotesId;

	@Temporal(TemporalType.DATE)
	@Column(name="created_date")
	private Date createdDate;

	private String notes;

	@Column(name="notes_number")
	private String notesNumber;

	@Column(name="notes_title")
	private String notesTitle;

	@Temporal(TemporalType.DATE)
	private Date date;
	
	private String status;

	@ManyToOne
	@JoinColumn(name="teacher_ID")
	private Teacher teacher;
	
	@Temporal(TemporalType.DATE)
	@Column(name="updated_date")
	private Date updatedDate;

	public TeacherNote() {
	}

	public int getTeacherNotesId() {
		return this.teacherNotesId;
	}

	public void setTeacherNotesId(int teacherNotesId) {
		this.teacherNotesId = teacherNotesId;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getNotesNumber() {
		return this.notesNumber;
	}

	public void setNotesNumber(String notesNumber) {
		this.notesNumber = notesNumber;
	}

	public String getNotesTitle() {
		return this.notesTitle;
	}

	public void setNotesTitle(String notesTitle) {
		this.notesTitle = notesTitle;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

}