package com.stumosys.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.primefaces.model.UploadedFile;

import com.stumosys.shared.ImagePath;
import com.stumosys.shared.Library;
@XmlRootElement
public class LibraryDataBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1904858249546961344L;

	private String bookName;
	private String authorName;
	private String bookeditions;
	private String bookCategory;
	private String bookPages;
	private String bookQty;
	private String borrowedBookQty;
	private String bookDescription;
	private String bookPrice;
	private UploadedFile bookFile;
	private String parfilePath;
	private String sNo;
	private List<Library> librayList = null;
	private List<ImagePath> librayImageList = null;
	private Date parfilePathDate;
	private String libStuClass;
	private String libStudID;
	private List<String> targetList = new ArrayList<String>();
	private List<String> availList = new ArrayList<String>();
	private Date borroweDate;
	private String bookFee;
	private String bookDueType;
	private String bookfine;
	private String status;
	private String addQuentity;
	private String studentname;
	private Date duedate;
	private String schoolLogo;

	private String studentid;
	
	public String getStudentid() {
		return studentid;
	}
	public void setStudentid(String studentid) {
		this.studentid = studentid;
	}

	public String getSchoolLogo() {
		return schoolLogo;
	}

	public void setSchoolLogo(String schoolLogo) {
		this.schoolLogo = schoolLogo;
	}
	public Date getDuedate() {
		return duedate;
	}

	public void setDuedate(Date duedate) {
		this.duedate = duedate;
	}

	public String getStudentname() {
		return studentname;
	}

	public void setStudentname(String studentname) {
		this.studentname = studentname;
	}

	public String getAddQuentity() {
		return addQuentity;
	}

	public void setAddQuentity(String addQuentity) {
		this.addQuentity = addQuentity;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getBorrowedBookQty() {
		return borrowedBookQty;
	}

	public void setBorrowedBookQty(String borrowedBookQty) {
		this.borrowedBookQty = borrowedBookQty;
	}

	public String getBookfine() {
		return bookfine;
	}

	public void setBookfine(String bookfine) {
		this.bookfine = bookfine;
	}

	public String getBookFee() {
		return bookFee;
	}

	public void setBookFee(String bookFee) {
		this.bookFee = bookFee;
	}

	public String getBookDueType() {
		return bookDueType;
	}

	public void setBookDueType(String bookDueType) {
		this.bookDueType = bookDueType;
	}

	/**
	 * @return the borroweDate
	 */
	public Date getBorroweDate() {
		return borroweDate;
	}

	/**
	 * @param borroweDate
	 *            the borroweDate to set
	 */
	public void setBorroweDate(Date borroweDate) {
		this.borroweDate = borroweDate;
	}

	/**
	 * @return the targetList
	 */
	public List<String> getTargetList() {
		return targetList;
	}

	/**
	 * @param targetList
	 *            the targetList to set
	 */
	public void setTargetList(List<String> targetList) {
		this.targetList = targetList;
	}

	/**
	 * @return the availList
	 */
	public List<String> getAvailList() {
		return availList;
	}

	/**
	 * @param availList
	 *            the availList to set
	 */
	public void setAvailList(List<String> availList) {
		this.availList = availList;
	}

	/**
	 * @return the libStuClass
	 */
	public String getLibStuClass() {
		return libStuClass;
	}

	/**
	 * @param libStuClass
	 *            the libStuClass to set
	 */
	public void setLibStuClass(String libStuClass) {
		this.libStuClass = libStuClass;
	}

	/**
	 * @return the libStudID
	 */
	public String getLibStudID() {
		return libStudID;
	}

	/**
	 * @param libStudID
	 *            the libStudID to set
	 */
	public void setLibStudID(String libStudID) {
		this.libStudID = libStudID;
	}

	/**
	 * @return the librayList
	 */
	public List<Library> getLibrayList() {
		return librayList;
	}

	/**
	 * @param librayList
	 *            the librayList to set
	 */
	public void setLibrayList(List<Library> librayList) {
		this.librayList = librayList;
	}

	/**
	 * @return the librayImageList
	 */
	public List<ImagePath> getLibrayImageList() {
		return librayImageList;
	}

	/**
	 * @param librayImageList
	 *            the librayImageList to set
	 */
	public void setLibrayImageList(List<ImagePath> librayImageList) {
		this.librayImageList = librayImageList;
	}

	/**
	 * @return the bookFile
	 */
	public UploadedFile getBookFile() {
		return bookFile;
	}

	/**
	 * @param bookFile
	 *            the bookFile to set
	 */
	public void setBookFile(UploadedFile bookFile) {
		this.bookFile = bookFile;
	}

	/**
	 * @return the bookName
	 */
	public String getBookName() {
		return bookName;
	}

	/**
	 * @param bookName
	 *            the bookName to set
	 */
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	/**
	 * @return the authorName
	 */
	public String getAuthorName() {
		return authorName;
	}

	/**
	 * @param authorName
	 *            the authorName to set
	 */
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	/**
	 * @return the bookeditions
	 */
	public String getBookeditions() {
		return bookeditions;
	}

	/**
	 * @param bookeditions
	 *            the bookeditions to set
	 */
	public void setBookeditions(String bookeditions) {
		this.bookeditions = bookeditions;
	}

	/**
	 * @return the bookCategory
	 */
	public String getBookCategory() {
		return bookCategory;
	}

	/**
	 * @param bookCategory
	 *            the bookCategory to set
	 */
	public void setBookCategory(String bookCategory) {
		this.bookCategory = bookCategory;
	}

	/**
	 * @return the bookPages
	 */
	public String getBookPages() {
		return bookPages;
	}

	/**
	 * @param bookPages
	 *            the bookPages to set
	 */
	public void setBookPages(String bookPages) {
		this.bookPages = bookPages;
	}

	/**
	 * @return the bookDescription
	 */
	public String getBookDescription() {
		return bookDescription;
	}

	/**
	 * @param bookDescription
	 *            the bookDescription to set
	 */
	public void setBookDescription(String bookDescription) {
		this.bookDescription = bookDescription;
	}

	/**
	 * @return the bookPrice
	 */
	public String getBookPrice() {
		return bookPrice;
	}

	/**
	 * @param bookPrice
	 *            the bookPrice to set
	 */
	public void setBookPrice(String bookPrice) {
		this.bookPrice = bookPrice;
	}

	/**
	 * @return the parfilePath
	 */
	public String getParfilePath() {
		return parfilePath;
	}

	/**
	 * @param parfilePath
	 *            the parfilePath to set
	 */
	public void setParfilePath(String parfilePath) {
		this.parfilePath = parfilePath;
	}

	/**
	 * @return the sNo
	 */
	public String getsNo() {
		return sNo;
	}

	/**
	 * @param sNo
	 *            the sNo to set
	 */
	public void setsNo(String sNo) {
		this.sNo = sNo;
	}

	/**
	 * @return the parfilePathDate
	 */
	public Date getParfilePathDate() {
		return parfilePathDate;
	}

	/**
	 * @param parfilePathDate
	 *            the parfilePathDate to set
	 */
	public void setParfilePathDate(Date parfilePathDate) {
		this.parfilePathDate = parfilePathDate;
	}

	public String getBookQty() {
		return bookQty;
	}

	public void setBookQty(String bookQty) {
		this.bookQty = bookQty;
	}

}
