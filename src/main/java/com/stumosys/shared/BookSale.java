package com.stumosys.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the book_sales database table.
 * 
 */
@Entity
@Table(name="book_sales")
@NamedQuery(name="BookSale.findAll", query="SELECT b FROM BookSale b")
public class BookSale implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int sales_ID;

	@Temporal(TemporalType.DATE)
	@Column(name="order_date")
	private Date orderDate;

	@Column(name="order_number")
	private String orderNumber;

	@Column(name="order_time")
	private Timestamp orderTime;

	@ManyToOne
	@JoinColumn(name="school_id")
	private School school;

	private String status;
	
	private String path_name;
	
	private String approvalstatus;
	@ManyToOne
	@JoinColumn(name="student_ID")
	private Student student;
	

	@Column(name="total_price")
	private String totalPrice;

	
	public String getApprovalstatus() {
		return approvalstatus;
	}

	public void setApprovalstatus(String approvalstatus) {
		this.approvalstatus = approvalstatus;
	}

	public BookSale() {
	}

	public int getSales_ID() {
		return this.sales_ID;
	}

	public void setSales_ID(int sales_ID) {
		this.sales_ID = sales_ID;
	}

	public Date getOrderDate() {
		return this.orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderNumber() {
		return this.orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Timestamp getOrderTime() {
		return this.orderTime;
	}

	public void setOrderTime(Timestamp orderTime) {
		this.orderTime = orderTime;
	}

	
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getTotalPrice() {
		return this.totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getPath_name() {
		return path_name;
	}

	public void setPath_name(String path_name) {
		this.path_name = path_name;
	}

}