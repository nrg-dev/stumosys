package com.stumosys.shared;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the book_sales_record database table.
 * 
 */
@Entity
@Table(name="book_sales_record")
@NamedQuery(name="BookSalesRecord.findAll", query="SELECT b FROM BookSalesRecord b")
public class BookSalesRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int sales_record_ID;

	@Column(name="book_name")
	private String bookName;

	@Column(name="net_amount")
	private String netAmount;

	private String price;

	private String quantity;

	private String imagePath;
	
	private String status;

	public BookSalesRecord() {
	}

	public int getSales_record_ID() {
		return this.sales_record_ID;
	}

	public void setSales_record_ID(int sales_record_ID) {
		this.sales_record_ID = sales_record_ID;
	}

	public String getBookName() {
		return this.bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
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

	public String getNetAmount() {
		return this.netAmount;
	}

	public void setNetAmount(String netAmount) {
		this.netAmount = netAmount;
	}

	public String getPrice() {
		return this.price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getQuantity() {
		return this.quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	
}