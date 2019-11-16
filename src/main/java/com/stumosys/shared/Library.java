package com.stumosys.shared;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;
import java.sql.Timestamp;


/**
 * The persistent class for the library database table.
 * 
 */
@Entity
@Table(name="library")
@NamedQuery(name="Library.findAll", query="SELECT l FROM Library l")
public class Library implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int library_ID;

	@Column(name="author_name")
	private String authorName;

	@Column(name="book_edition")
	private String bookEdition;

	@Column(name="book_name")
	private String bookName;

	@Temporal(TemporalType.DATE)
	@Column(name="created_date")
	private Date createdDate;

	@Column(name="created_time")
	private Timestamp createdTime;

	private String description;
	
	private String category;


	private String pages;

	private String price;
	
	private String quantity;
	
	@Column(name="due_type")
	private String dueType;

	@Column(name="penalty_fee")
	private String penaltyFee;

	private String status;
	
	//bi-directional many-to-one association to ImagePath
	@OneToMany(mappedBy="library")
	private List<ImagePath> imagePaths;

	//bi-directional many-to-one association to School
	@ManyToOne
	@JoinColumn(name="school_ID")
	private School school;

	//bi-directional many-to-one association to Browerbook
		@OneToMany(mappedBy="library")
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

		public Browerbook addBrowerbook(Browerbook browerbook) {
			getBrowerbooks().add(browerbook);
			browerbook.setLibrary(this);

			return browerbook;
		}

		public Browerbook removeBrowerbook(Browerbook browerbook) {
			getBrowerbooks().remove(browerbook);
			browerbook.setLibrary(null);

			return browerbook;
		}
		
	public Library() {
	}

	public int getLibrary_ID() {
		return this.library_ID;
	}

	public void setLibrary_ID(int library_ID) {
		this.library_ID = library_ID;
	}

	public String getAuthorName() {
		return this.authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getBookEdition() {
		return this.bookEdition;
	}

	public void setBookEdition(String bookEdition) {
		this.bookEdition = bookEdition;
	}

	public String getBookName() {
		return this.bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
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

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPages() {
		return this.pages;
	}

	public void setPages(String pages) {
		this.pages = pages;
	}

	public String getPrice() {
		return this.price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public School getSchool() {
		return this.school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	public List<ImagePath> getImagePaths() {
		return this.imagePaths;
	}

	public void setImagePaths(List<ImagePath> imagePaths) {
		this.imagePaths = imagePaths;
	}

	public ImagePath addImagePath(ImagePath imagePath) {
		getImagePaths().add(imagePath);
		imagePath.setLibrary(this);

		return imagePath;
	}

	public ImagePath removeImagePath(ImagePath imagePath) {
		getImagePaths().remove(imagePath);
		imagePath.setLibrary(null);

		return imagePath;
	}

	public String getDueType() {
		return dueType;
	}

	public void setDueType(String dueType) {
		this.dueType = dueType;
	}

	public String getPenaltyFee() {
		return penaltyFee;
	}

	public void setPenaltyFee(String penaltyFee) {
		this.penaltyFee = penaltyFee;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
	
}