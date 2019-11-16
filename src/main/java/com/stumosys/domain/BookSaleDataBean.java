package com.stumosys.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.primefaces.model.UploadedFile;


@XmlRootElement
public class BookSaleDataBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1610311262089687224L;
	private String bookName;
	private String bookQuantity;
	private String bookPrice;
	private String studID;
	private String amount;
	private String order;
	private String total;
	private String netAmount;
	private String totalAmount;
	// Added by Alex
	private String PhoneNo;
	List<String> totallist = null;
	private boolean flag1 = true;
	private boolean flag = true;
	List<BookSaleDataBean> booklist2 = null;
	List<BookSaleDataBean> bookviewlist = null;
	List<BookSaleDataBean> orderlist = null;
	private UploadedFile bookFile;
	private String bookfilePath;
	private String returnStatus;
	
	private String orderNumber;
	List<BookSaleDataBean> booklist = null;
	List<BookSaleDataBean> bookinfolist = null;
	private String status;
	private boolean qtyflag = true;
	private boolean qtyflag1 = false;
	private String oldQuan;
	private String EmailId;
	private String filePath;
	private boolean actionFlag=false;
	
	public boolean isActionFlag() {
		return actionFlag;
	}
	public void setActionFlag(boolean actionFlag) {
		this.actionFlag = actionFlag;
	}
	
	
	public String getPhoneNo() {
		return PhoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		PhoneNo = phoneNo;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}


	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public List<BookSaleDataBean> getBooklist() {
		return booklist;
	}

	public void setBooklist(List<BookSaleDataBean> booklist) {
		this.booklist = booklist;
	}

	public List<BookSaleDataBean> getBookinfolist() {
		return bookinfolist;
	}

	public void setBookinfolist(List<BookSaleDataBean> bookinfolist) {
		this.bookinfolist = bookinfolist;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isQtyflag() {
		return qtyflag;
	}

	public void setQtyflag(boolean qtyflag) {
		this.qtyflag = qtyflag;
	}

	public boolean isQtyflag1() {
		return qtyflag1;
	}

	public void setQtyflag1(boolean qtyflag1) {
		this.qtyflag1 = qtyflag1;
	}

	public String getOldQuan() {
		return oldQuan;
	}

	public void setOldQuan(String oldQuan) {
		this.oldQuan = oldQuan;
	}

	public String getReturnStatus() {
		return returnStatus;
	}

	public void setReturnStatus(String returnStatus) {
		this.returnStatus = returnStatus;
	}
	
	public String getBookfilePath() {
		return bookfilePath;
	}

	public void setBookfilePath(String bookfilePath) {
		this.bookfilePath = bookfilePath;
	}

	public UploadedFile getBookFile() {
		return bookFile;
	}

	public void setBookFile(UploadedFile bookFile) {
		this.bookFile = bookFile;
	}

	public List<BookSaleDataBean> getOrderlist() {
		return orderlist;
	}

	public void setOrderlist(List<BookSaleDataBean> orderlist) {
		this.orderlist = orderlist;
	}

	private String serial;
	private String perClassSection;
	private ArrayList<BookSaleDataBean> rollnolist;
	private String stuCls1;
	private String Classname;
	private String Section;
	private String Studentid;

	public List<String> getTotallist() {
		return totallist;
	}

	public void setTotallist(List<String> totallist) {
		this.totallist = totallist;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public boolean isFlag1() {
		return flag1;
	}

	public void setFlag1(boolean flag1) {
		this.flag1 = flag1;
	}

	public String getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(String netAmount) {
		this.netAmount = netAmount;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getAmount() {
		return amount;
	}

	public List<BookSaleDataBean> getBooklist2() {
		return booklist2;
	}

	public void setBooklist2(List<BookSaleDataBean> booklist2) {
		this.booklist2 = booklist2;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getStuCls1() {
		return stuCls1;
	}

	public void setStuCls1(String stuCls1) {
		this.stuCls1 = stuCls1;
	}

	private ArrayList<BookSaleDataBean> stuClassList;

	public ArrayList<BookSaleDataBean> getStuClassList() {
		return stuClassList;
	}

	public void setStuClassList(ArrayList<BookSaleDataBean> stuClassList) {
		this.stuClassList = stuClassList;
	}

	public ArrayList<BookSaleDataBean> getRollnolist() {
		return rollnolist;
	}

	public void setRollnolist(ArrayList<BookSaleDataBean> rollnolist) {
		this.rollnolist = rollnolist;
	}

	public String getStudID() {
		return studID;
	}

	public void setStudID(String studID) {
		this.studID = studID;
	}

	public String getPerClassSection() {
		return perClassSection;
	}

	public void setPerClassSection(String perClassSection) {
		this.perClassSection = perClassSection;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getBookQuantity() {
		return bookQuantity;
	}

	public void setBookQuantity(String bookQuantity) {
		this.bookQuantity = bookQuantity;
	}

	public String getBookPrice() {
		return bookPrice;
	}

	public void setBookPrice(String bookPrice) {
		this.bookPrice = bookPrice;
	}

	/**
	 * @return the classname
	 */
	public String getClassname() {
		return Classname;
	}

	/**
	 * @param classname
	 *            the classname to set
	 */
	public void setClassname(String classname) {
		Classname = classname;
	}

	/**
	 * @return the section
	 */
	public String getSection() {
		return Section;
	}

	/**
	 * @param section
	 *            the section to set
	 */
	public void setSection(String section) {
		Section = section;
	}

	/**
	 * @return the studentid
	 */
	public String getStudentid() {
		return Studentid;
	}

	public List<BookSaleDataBean> getBookviewlist() {
		return bookviewlist;
	}

	public void setBookviewlist(List<BookSaleDataBean> bookviewlist) {
		this.bookviewlist = bookviewlist;
	}

	/**
	 * @param studentid
	 *            the studentid to set
	 */
	public void setStudentid(String studentid) {
		Studentid = studentid;
	}

	public String getEmailId() {
		return EmailId;
	}

	public void setEmailId(String emailId) {
		EmailId = emailId;
	}

}
