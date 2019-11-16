package com.stumosys.shared;

import java.io.Serializable;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the person database table.
 * 
 */
@Entity
@Table(name="person")
@NamedQuery(name="Person.findAll", query="SELECT p FROM Person p")
public class Person implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int person_ID;

	@Temporal(TemporalType.DATE)
	@Column(name="created_date")
	private Date createdDate;

	@Column(name="created_time")
	private Timestamp createdTime;

	@Column(name="person_role")
	private String personRole;

	@Column(name="person_role_number")
	private String personRoleNumber;

	private String status;

	//bi-directional many-to-one association to Staff
	@OneToMany(mappedBy="person")
	private List<Staff> staffs;
	
	//bi-directional many-to-one association to Admin
	@OneToMany(mappedBy="person")
	private List<Admin> admins;

	//bi-directional many-to-one association to Parent
	@OneToMany(mappedBy="person")
	private List<Parent> parents;

	//bi-directional many-to-one association to School
	@ManyToOne
	@JoinColumn(name="school_ID")
	private School school;

	//bi-directional many-to-one association to Student
	@OneToMany(mappedBy="person")
	private List<Student> students;

	//bi-directional many-to-one association to Teacher
	//@OneToMany(mappedBy="person")
	@OneToMany(mappedBy="person",fetch=FetchType.EAGER,cascade = CascadeType.ALL)
	//private List<Teacher> teachers;
	private List<Teacher> teachers = new ArrayList<Teacher>();


	//bi-directional many-to-one association to User
	@OneToMany(mappedBy="person")
	private List<User> users;

	//bi-directional many-to-one association to UserProduct
	@OneToMany(mappedBy="person")
	private List<UserProduct> userProducts;

	public Person() {
	}

	public int getPerson_ID() {
		return this.person_ID;
	}

	public void setPerson_ID(int person_ID) {
		this.person_ID = person_ID;
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

	public String getPersonRole() {
		return this.personRole;
	}

	public void setPersonRole(String personRole) {
		this.personRole = personRole;
	}

	public String getPersonRoleNumber() {
		return this.personRoleNumber;
	}

	public void setPersonRoleNumber(String personRoleNumber) {
		this.personRoleNumber = personRoleNumber;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Admin> getAdmins() {
		return this.admins;
	}

	public void setAdmins(List<Admin> admins) {
		this.admins = admins;
	}

	public Admin addAdmin(Admin admin) {
		getAdmins().add(admin);
		admin.setPerson(this);

		return admin;
	}

	public Admin removeAdmin(Admin admin) {
		getAdmins().remove(admin);
		admin.setPerson(null);

		return admin;
	}

	public List<Parent> getParents() {
		return this.parents;
	}

	public void setParents(List<Parent> parents) {
		this.parents = parents;
	}

	public Parent addParent(Parent parent) {
		getParents().add(parent);
		parent.setPerson(this);

		return parent;
	}

	public Parent removeParent(Parent parent) {
		getParents().remove(parent);
		parent.setPerson(null);

		return parent;
	}

	public School getSchool() {
		return this.school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public List<Student> getStudents() {
		return this.students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public Student addStudent(Student student) {
		getStudents().add(student);
		student.setPerson(this);

		return student;
	}

	public Student removeStudent(Student student) {
		getStudents().remove(student);
		student.setPerson(null);

		return student;
	}

	public List<Teacher> getTeachers() {
		return this.teachers;
	}

	public void setTeachers(List<Teacher> teachers) {
		this.teachers = teachers;
	}

	public Teacher addTeacher(Teacher teacher) {
		getTeachers().add(teacher);
		teacher.setPerson(this);

		return teacher;
	}

	public Teacher removeTeacher(Teacher teacher) {
		getTeachers().remove(teacher);
		teacher.setPerson(null);

		return teacher;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public User addUser(User user) {
		getUsers().add(user);
		user.setPerson(this);

		return user;
	}

	public User removeUser(User user) {
		getUsers().remove(user);
		user.setPerson(null);

		return user;
	}

	public List<UserProduct> getUserProducts() {
		return this.userProducts;
	}

	public void setUserProducts(List<UserProduct> userProducts) {
		this.userProducts = userProducts;
	}

	public UserProduct addUserProduct(UserProduct userProduct) {
		getUserProducts().add(userProduct);
		userProduct.setPerson(this);

		return userProduct;
	}

	public UserProduct removeUserProduct(UserProduct userProduct) {
		getUserProducts().remove(userProduct);
		userProduct.setPerson(null);

		return userProduct;
	}
	public List<Staff> getStaffs() {
		return this.staffs;
	}

	public void setStaffs(List<Staff> staffs) {
		this.staffs = staffs;
	}

	public Staff addStaff(Staff staff) {
		getStaffs().add(staff);
		staff.setPerson(this);

		return staff;
	}

	public Staff removeStaff(Staff staff) {
		getStaffs().remove(staff);
		staff.setPerson(null);

		return staff;
	}
}