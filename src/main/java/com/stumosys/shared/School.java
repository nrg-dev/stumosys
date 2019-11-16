package com.stumosys.shared;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the school database table.
 * 
 */
@Entity
@Table(name="school")
@NamedQuery(name="School.findAll", query="SELECT s FROM School s")
public class School implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int school_ID;

	private String address;

	@Temporal(TemporalType.DATE)
	@Column(name="created_date")
	private Date createdDate;

	@Column(name="created_time")
	private Timestamp createdTime;

	@Column(name="email_id")
	private String emailId;

	@Column(name="fax_number")
	private String faxNumber;

	private String name;
	
	private String username;

	@Column(name="phone_number")
	private String phoneNumber;

	private String status;
	
	private String logopath;
	
	private String alternativeemail;

	private String contactpersonname;

	private String country;

	private String landlinenumber;

	private String mobilenumber;
	
	private String state;

	public String getAlternativeemail() {
		return alternativeemail;
	}

	public void setAlternativeemail(String alternativeemail) {
		this.alternativeemail = alternativeemail;
	}

	public String getContactpersonname() {
		return contactpersonname;
	}

	public void setContactpersonname(String contactpersonname) {
		this.contactpersonname = contactpersonname;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getLandlinenumber() {
		return landlinenumber;
	}

	public void setLandlinenumber(String landlinenumber) {
		this.landlinenumber = landlinenumber;
	}

	public String getMobilenumber() {
		return mobilenumber;
	}

	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getLogopath() {
		return logopath;
	}

	public void setLogopath(String logopath) {
		this.logopath = logopath;
	}

	//bi-directional many-to-one association to BookSale
	@OneToMany(mappedBy="school")
	private List<BookSale> bookSales;

	//bi-directional many-to-one association to Class
	@OneToMany(mappedBy="school")
	private List<Class> clazzs;

	//bi-directional many-to-one association to Library
	@OneToMany(mappedBy="school")
	private List<Library> libraries;
	
	//bi-directional many-to-one association to Library
		@OneToMany(mappedBy="school")
		private List<Staff> staff;
		
	//bi-directional many-to-one association to Noticeboard
	@OneToMany(mappedBy="school")
	private List<Noticeboard> noticeboards;
	
	//bi-directional many-to-one association to Noticeboard
		@OneToMany(mappedBy="school")
		private List<ExamTable> examTables;
		
	//bi-directional many-to-one association to Parent
	@OneToMany(mappedBy="school")
	private List<Parent> parents;

	//bi-directional many-to-one association to Person
	@OneToMany(mappedBy="school")
	private List<Person> persons;

	//bi-directional many-to-one association to Postcode
	@ManyToOne
	@JoinColumn(name="postcode_ID")
	private Postcode postcode;

	//bi-directional many-to-one association to Student
	@OneToMany(mappedBy="school")
	private List<Student> students;
	
	//bi-directional many-to-one association to Library
			@OneToMany(mappedBy="school")
			private List<BookSalesRecord> bookSalesRecord;
			
	//bi-directional many-to-one association to Subject
	@OneToMany(mappedBy="school")
	private List<Subject> subjects;

	//bi-directional many-to-one association to Teacher
	@OneToMany(mappedBy="school")
	private List<Teacher> teachers;

	//bi-directional many-to-one association to TeacherSubject
	@OneToMany(mappedBy="school")
	private List<TeacherSubject> teacherSubjects;
	
	//bi-directional many-to-one association to Reportcard
		@OneToMany(mappedBy="school")
		private List<Reportcard> reportcards;
		
		//bi-directional many-to-one association to Browerbook
		@OneToMany(mappedBy="school")
		private List<Browerbook> browerbooks;
		
	public School() {
	}

	public int getSchool_ID() {
		return this.school_ID;
	}

	public void setSchool_ID(int school_ID) {
		this.school_ID = school_ID;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getEmailId() {
		return this.emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getFaxNumber() {
		return this.faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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
		bookSale.setSchool(this);

		return bookSale;
	}

	public BookSale removeBookSale(BookSale bookSale) {
		getBookSales().remove(bookSale);
		bookSale.setSchool(null);

		return bookSale;
	}

	public List<Class> getClazzs() {
		return this.clazzs;
	}

	public void setClazzs(List<Class> clazzs) {
		this.clazzs = clazzs;
	}

	public Class addClazz(Class clazz) {
		getClazzs().add(clazz);
		clazz.setSchool(this);

		return clazz;
	}

	public Class removeClazz(Class clazz) {
		getClazzs().remove(clazz);
		clazz.setSchool(null);

		return clazz;
	}

	public List<Library> getLibraries() {
		return this.libraries;
	}

	public void setLibraries(List<Library> libraries) {
		this.libraries = libraries;
	}

	public Library addLibrary(Library library) {
		getLibraries().add(library);
		library.setSchool(this);

		return library;
	}

	public Library removeLibrary(Library library) {
		getLibraries().remove(library);
		library.setSchool(null);

		return library;
	}





	public List<Staff> getStaff() {
		return staff;
	}

	public void setStaff(List<Staff> staff) {
		this.staff = staff;
	}
	
	public Staff addStaff(Staff staff) {
		getStaff().add(staff);
		staff.setSchool(this);

		return staff;
	}

	public Staff removeStaff(Staff staff) {
		getStaff().remove(staff);
		staff.setSchool(null);

		return staff;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the examTables
	 */
	public List<ExamTable> getExamTables() {
		return examTables;
	}

	/**
	 * @param examTables the examTables to set
	 */
	public void setExamTables(List<ExamTable> examTables) {
		this.examTables = examTables;
	}
	public ExamTable addExamTable(ExamTable examTable) {
		getExamTables().add(examTable);
		examTable.setSchool(this);

		return examTable;
	}

	public ExamTable removeExamTable(ExamTable examTable) {
		getExamTables().remove(examTable);
		examTable.setSchool(null);

		return examTable;
	}
	
	public List<Noticeboard> getNoticeboards() {
		return this.noticeboards;
	}

	public void setNoticeboards(List<Noticeboard> noticeboards) {
		this.noticeboards = noticeboards;
	}

	public Noticeboard addNoticeboard(Noticeboard noticeboard) {
		getNoticeboards().add(noticeboard);
		noticeboard.setSchool(this);

		return noticeboard;
	}

	public Noticeboard removeNoticeboard(Noticeboard noticeboard) {
		getNoticeboards().remove(noticeboard);
		noticeboard.setSchool(null);

		return noticeboard;
	}

	
	public List<Parent> getParents() {
		return this.parents;
	}

	public void setParents(List<Parent> parents) {
		this.parents = parents;
	}

	public Parent addParent(Parent parent) {
		getParents().add(parent);
		parent.setSchool(this);

		return parent;
	}

	public Parent removeParent(Parent parent) {
		getParents().remove(parent);
		parent.setSchool(null);

		return parent;
	}

	public List<Person> getPersons() {
		return this.persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	public Person addPerson(Person person) {
		getPersons().add(person);
		person.setSchool(this);

		return person;
	}

	public Person removePerson(Person person) {
		getPersons().remove(person);
		person.setSchool(null);

		return person;
	}

	public Postcode getPostcode() {
		return this.postcode;
	}

	public void setPostcode(Postcode postcode) {
		this.postcode = postcode;
	}

	public List<Student> getStudents() {
		return this.students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public Student addStudent(Student student) {
		getStudents().add(student);
		student.setSchool(this);

		return student;
	}

	public Student removeStudent(Student student) {
		getStudents().remove(student);
		student.setSchool(null);

		return student;
	}

	public List<Subject> getSubjects() {
		return this.subjects;
	}

	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}

	public Subject addSubject(Subject subject) {
		getSubjects().add(subject);
		subject.setSchool(this);

		return subject;
	}

	public Subject removeSubject(Subject subject) {
		getSubjects().remove(subject);
		subject.setSchool(null);

		return subject;
	}

	public List<Teacher> getTeachers() {
		return this.teachers;
	}

	public void setTeachers(List<Teacher> teachers) {
		this.teachers = teachers;
	}

	public Teacher addTeacher(Teacher teacher) {
		getTeachers().add(teacher);
		teacher.setSchool(this);

		return teacher;
	}

	public Teacher removeTeacher(Teacher teacher) {
		getTeachers().remove(teacher);
		teacher.setSchool(null);

		return teacher;
	}

	public List<TeacherSubject> getTeacherSubjects() {
		return this.teacherSubjects;
	}

	public void setTeacherSubjects(List<TeacherSubject> teacherSubjects) {
		this.teacherSubjects = teacherSubjects;
	}

	public TeacherSubject addTeacherSubject(TeacherSubject teacherSubject) {
		getTeacherSubjects().add(teacherSubject);
		teacherSubject.setSchool(this);

		return teacherSubject;
	}

	public TeacherSubject removeTeacherSubject(TeacherSubject teacherSubject) {
		getTeacherSubjects().remove(teacherSubject);
		teacherSubject.setSchool(null);

		return teacherSubject;
	}
	public List<Reportcard> getReportcards() {
		return this.reportcards;
	}

	public void setReportcards(List<Reportcard> reportcards) {
		this.reportcards = reportcards;
	}

	public Reportcard addReportcard(Reportcard reportcard) {
		getReportcards().add(reportcard);
		reportcard.setSchool(this);

		return reportcard;
	}

	public Reportcard removeReportcard(Reportcard reportcard) {
		getReportcards().remove(reportcard);
		reportcard.setSchool(null);

		return reportcard;
	}
	public List<Browerbook> getBrowerbooks() {
		return this.browerbooks;
	}

	public void setBrowerbooks(List<Browerbook> browerbooks) {
		this.browerbooks = browerbooks;
	}

	public Browerbook addBrowerbook(Browerbook browerbook) {
		getBrowerbooks().add(browerbook);
		browerbook.setSchool(this);

		return browerbook;
	}

	public Browerbook removeBrowerbook(Browerbook browerbook) {
		getBrowerbooks().remove(browerbook);
		browerbook.setSchool(null);

		return browerbook;
	}

	public List<BookSalesRecord> getBookSalesRecord() {
		return bookSalesRecord;
	}

	public void setBookSalesRecord(List<BookSalesRecord> bookSalesRecord) {
		this.bookSalesRecord = bookSalesRecord;
	}
	
	public BookSalesRecord addBookSalesRecord(BookSalesRecord bookSalesRecord) {
		getBookSalesRecord().add(bookSalesRecord);
		bookSalesRecord.setSchool(this);

		return bookSalesRecord;
	}

	public BookSalesRecord removeBookSalesRecord(BookSalesRecord bookSalesRecord) {
		getBookSalesRecord().remove(bookSalesRecord);
		bookSalesRecord.setSchool(null);

		return bookSalesRecord;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
}