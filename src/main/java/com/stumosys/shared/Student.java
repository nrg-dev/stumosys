package com.stumosys.shared;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the student database table.
 * 
 */
@Entity
@Table(name="student")
@NamedQuery(name="Student.findAll", query="SELECT s FROM Student s")
public class Student implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int student_ID;

	@Temporal(TemporalType.DATE)
	@Column(name="created_date")
	private Date createdDate;

	@Column(name="created_time")
	private Timestamp createdTime;

	@Column(name="roll_number")
	private String rollNumber;

	private String status;

	//bi-directional many-to-one association to BookSale
	@OneToMany(mappedBy="student")
	private List<BookSale> bookSales;

	//bi-directional many-to-one association to ImagePath
	@OneToMany(mappedBy="student")
	private List<ImagePath> imagePaths;

	//bi-directional many-to-one association to School
	@ManyToOne
	@JoinColumn(name="school_ID")
	private School school;

	//bi-directional many-to-one association to Person
	@ManyToOne
	@JoinColumn(name="person_ID")
	private Person person;

	//bi-directional many-to-one association to StudentClass
	@OneToMany(mappedBy="student")
	private List<StudentClass> studentClasses;
	
	//bi-directional many-to-one association to Reportcard
	@OneToMany(mappedBy="student")
	private List<Reportcard> reportcards;

	/*//bi-directional one-to-one association to StudentDetail
	@OneToOne(mappedBy="student1")
	private StudentDetail studentDetail1;

	//bi-directional one-to-one association to StudentDetail
	@OneToOne(mappedBy="student2")
	private StudentDetail studentDetail2;
*/
	//bi-directional many-to-one association to StudentParent
	@OneToMany(mappedBy="student")
	private List<StudentParent> studentParents;
	
	//bi-directional many-to-one association to Browerbook
		@OneToMany(mappedBy="student")
		private List<Browerbook> browerbooks;
		

	/**
		 * @return the browerbooks
		 */
		public List<Browerbook> getBrowerbooks() {
			return browerbooks;
		}

		/**
		 * @param browerbooks the browerbooks to set
		 */
		public void setBrowerbooks(List<Browerbook> browerbooks) {
			this.browerbooks = browerbooks;
		}
		public Browerbook addBrowerbooks1(Browerbook browerbooks) {
			getBrowerbooks().add(browerbooks);
			browerbooks.setStudent(this);

			return browerbooks;
		}

		public Browerbook removeBrowerbooks1(Browerbook browerbooks) {
			getBrowerbooks().remove(browerbooks);
			browerbooks.setStudent(null);

			return browerbooks;
		}

	public Student() {
	}

	public int getStudent_ID() {
		return this.student_ID;
	}

	public void setStudent_ID(int student_ID) {
		this.student_ID = student_ID;
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

	public String getRollNumber() {
		return this.rollNumber;
	}

	public void setRollNumber(String rollNumber) {
		this.rollNumber = rollNumber;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<BookSale> getBookSales() {
		return this.bookSales;
	}

	public void setBookSales(List<BookSale> bookSales) {
		this.bookSales = bookSales;
	}

	public BookSale addBookSale(BookSale bookSale) {
		getBookSales().add(bookSale);
		bookSale.setStudent(this);

		return bookSale;
	}

	public BookSale removeBookSale(BookSale bookSale) {
		getBookSales().remove(bookSale);
		bookSale.setStudent(null);

		return bookSale;
	}

	public List<ImagePath> getImagePaths() {
		return this.imagePaths;
	}

	public void setImagePaths(List<ImagePath> imagePaths) {
		this.imagePaths = imagePaths;
	}

	public ImagePath addImagePath(ImagePath imagePath) {
		getImagePaths().add(imagePath);
		imagePath.setStudent(this);

		return imagePath;
	}

	public ImagePath removeImagePath(ImagePath imagePath) {
		getImagePaths().remove(imagePath);
		imagePath.setStudent(null);

		return imagePath;
	}

	public School getSchool() {
		return this.school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public Person getPerson() {
		return this.person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public List<StudentClass> getStudentClasses() {
		return this.studentClasses;
	}

	public void setStudentClasses(List<StudentClass> studentClasses) {
		this.studentClasses = studentClasses;
	}

	public StudentClass addStudentClass(StudentClass studentClass) {
		getStudentClasses().add(studentClass);
		studentClass.setStudent(this);

		return studentClass;
	}

	public StudentClass removeStudentClass(StudentClass studentClass) {
		getStudentClasses().remove(studentClass);
		studentClass.setStudent(null);

		return studentClass;
	}
	//bi-directional many-to-one association to StudentDetail
		@OneToMany(mappedBy="studentid")
		private List<StudentDetail> studentDetails1;

	/*public StudentDetail getStudentDetail1() {
		return this.studentDetail1;
	}

	public void setStudentDetail1(StudentDetail studentDetail1) {
		this.studentDetail1 = studentDetail1;
	}

	public StudentDetail getStudentDetail2() {
		return this.studentDetail2;
	}

	public void setStudentDetail2(StudentDetail studentDetail2) {
		this.studentDetail2 = studentDetail2;
	}
*/
	public List<StudentParent> getStudentParents() {
		return this.studentParents;
	}

	public List<StudentDetail> getStudentDetails1() {
		return studentDetails1;
	}

	public void setStudentDetails1(List<StudentDetail> studentDetails1) {
		this.studentDetails1 = studentDetails1;
	}

	public void setStudentParents(List<StudentParent> studentParents) {
		this.studentParents = studentParents;
	}

	public StudentParent addStudentParent(StudentParent studentParent) {
		getStudentParents().add(studentParent);
		studentParent.setStudent(this);

		return studentParent;
	}

	public StudentParent removeStudentParent(StudentParent studentParent) {
		getStudentParents().remove(studentParent);
		studentParent.setStudent(null);

		return studentParent;
	}

	public List<Reportcard> getReportcards() {
		return this.reportcards;
	}

	public void setReportcards(List<Reportcard> reportcards) {
		this.reportcards = reportcards;
	}

	public Reportcard addReportcard(Reportcard reportcard) {
		getReportcards().add(reportcard);
		reportcard.setStudent(this);

		return reportcard;
	}

	public Reportcard removeReportcard(Reportcard reportcard) {
		getReportcards().remove(reportcard);
		reportcard.setStudent(null);

		return reportcard;
	}
}