package com.stumosys.shared;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the book_order_record database table.
 * 
 */
@Entity
@Table(name="book_order_record")
@NamedQuery(name="BookOrderRecord.findAll", query="SELECT b FROM BookOrderRecord b")
public class BookOrderRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int order_record_ID;

	@Column(name="book_name")
	private String bookName;

	@Column(name="net_amount")
	private String netAmount;

	private String price;

	private String quantity;

	@ManyToOne
	@JoinColumn(name="sales_ID")
	private BookSale booksale;

	public BookOrderRecord() {
	}

	public int getOrder_record_ID() {
		return this.order_record_ID;
	}

	public void setOrder_record_ID(int order_record_ID) {
		this.order_record_ID = order_record_ID;
	}

	public String getBookName() {
		return this.bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
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

	public BookSale getBooksale() {
		return booksale;
	}

	public void setBooksale(BookSale booksale) {
		this.booksale = booksale;
	}

	
}