package com.stumosys.managedBean;

import java.math.BigDecimal;
//import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.stumosys.controler.SmsController;
import com.stumosys.domain.BookSaleDataBean;
import com.stumosys.util.CommonValidate;

@ManagedBean(name = "bookSaleViewMB")
@RequestScoped
public class BookSaleViewMB {

	BookSaleDataBean bookSaleDataBean = new BookSaleDataBean();
	private static Logger logger = Logger.getLogger(BookSaleViewMB.class);

	private List<String> sRoll = null;
	private List<String> sRoll1 = null;
	private String clssection;
	List<BookSaleDataBean> booklist = null;
	List<BookSaleDataBean> booklist1 = null;
	private boolean flag3 = true;
	private boolean flag = true;
	List<BookSaleDataBean> filteredStudent;
	private boolean sflag = false;
	private boolean boxflag = false;
	List<String> classList=null;
	List<String> idlist = null;
	List<String> rollnumber=null;
	List<BookSaleDataBean> bookviewlist = null;
	private boolean invoiceflag = false;
	List<BookSaleDataBean> bookinfolist = null;
	private boolean delBoxflag = false;
	private boolean qtyflag = true;
	private boolean qtyflag1 = true;
	private String role;
	private String classstuflag="1";
	public String getClassstuflag() {
		return classstuflag;
	}

	public void setClassstuflag(String classstuflag) {
		this.classstuflag = classstuflag;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
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

	public boolean isDelBoxflag() {
		return delBoxflag;
	}

	public void setDelBoxflag(boolean delBoxflag) {
		this.delBoxflag = delBoxflag;
	}

	public List<BookSaleDataBean> getBookinfolist() {
		return bookinfolist;
	}

	public void setBookinfolist(List<BookSaleDataBean> bookinfolist) {
		this.bookinfolist = bookinfolist;
	}

	public List<BookSaleDataBean> getBookviewlist() {
		return bookviewlist;
	}

	public void setBookviewlist(List<BookSaleDataBean> bookviewlist) {
		this.bookviewlist = bookviewlist;
	}

	public boolean isInvoiceflag() {
		return invoiceflag;
	}

	public void setInvoiceflag(boolean invoiceflag) {
		this.invoiceflag = invoiceflag;
	}

	public List<String> getRollnumber() {
		return rollnumber;
	}

	public void setRollnumber(List<String> rollnumber) {
		this.rollnumber = rollnumber;
	}

	public List<String> getClassList() {
		return classList;
	}

	public void setClassList(List<String> classList) {
		this.classList = classList;
	}

	public List<String> getIdlist() {
		return idlist;
	}

	public void setIdlist(List<String> idlist) {
		this.idlist = idlist;
	}

	public List<String> getsRoll() {
		return sRoll;
	}

	public void setsRoll(List<String> sRoll) {
		this.sRoll = sRoll;
	}

	public List<String> getsRoll1() {
		return sRoll1;
	}

	public void setsRoll1(List<String> sRoll1) {
		this.sRoll1 = sRoll1;
	}

	public String getClssection() {
		return clssection;
	}

	public void setClssection(String clssection) {
		this.clssection = clssection;
	}

	public List<BookSaleDataBean> getBooklist() {
		return booklist;
	}

	public void setBooklist(List<BookSaleDataBean> booklist) {
		this.booklist = booklist;
	}

	public List<BookSaleDataBean> getBooklist1() {
		return booklist1;
	}

	public void setBooklist1(List<BookSaleDataBean> booklist1) {
		this.booklist1 = booklist1;
	}

	public boolean isFlag3() {
		return flag3;
	}

	public void setFlag3(boolean flag3) {
		this.flag3 = flag3;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public List<BookSaleDataBean> getFilteredStudent() {
		return filteredStudent;
	}

	public void setFilteredStudent(List<BookSaleDataBean> filteredStudent) {
		this.filteredStudent = filteredStudent;
	}

	public boolean isSflag() {
		return sflag;
	}

	public void setSflag(boolean sflag) {
		this.sflag = sflag;
	}

	public boolean isBoxflag() {
		return boxflag;
	}

	public void setBoxflag(boolean boxflag) {
		this.boxflag = boxflag;
	}

	public BookSaleDataBean getBookSaleDataBean() {
		return bookSaleDataBean;
	}

	public void setBookSaleDataBean(BookSaleDataBean bookSaleDataBean) {
		this.bookSaleDataBean = bookSaleDataBean;
	}
	/* Neela OCT 25   studIDchange*/
	public void studIDchange(ValueChangeEvent event) 
	{
		SmsController controller = null;
		try
		{
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			String classname=event.getNewValue().toString();
			rollnumber = controller.getstudentRollNumber(personID,classname);
			HashSet<String> hashclass = new HashSet<String>(rollnumber);
		    rollnumber.clear();
		    rollnumber.addAll(hashclass);
			logger.debug("roll number -- "+rollnumber.size());
		}
		catch(Exception e)
		{
			logger.warn("Inside Exception",e);
		}
	}
	
	public void booklistvaluechange(ValueChangeEvent event) 
	{
		SmsController controller = null;
		try
		{
			setInvoiceflag(false);
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			role = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role");			
			String s=event.getNewValue().toString();
			bookSaleDataBean.setStudID(s);
			booklist = controller.getBookViewInfo(personID, bookSaleDataBean);
			logger.debug("booklist"+booklist.size());
			setFlag3(false);
		}
		catch(Exception e)
		{
			logger.warn("Inside Exception",e);
		}
	}
	
	
	public String submit() {

		try {
			logger.debug("---------- submit ----");
			if (validate(true)) {
				return "";
			}
		} catch (Exception e) {
			logger.debug("---------- submit Method-----");

			logger.warn("Exception -->"+e.getMessage());
		}
		return "";
	}

	private boolean validate(boolean flag) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();

		if (bookSaleDataBean.getClassname().equalsIgnoreCase("")) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("BookSalViewClass").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the class."));
			}
			valid = false;

		}
		if (bookSaleDataBean.getSection().equalsIgnoreCase("")) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("BookSalViewSection").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the Section."));
			}
			valid = false;

		}

		if (bookSaleDataBean.getStudentid().equalsIgnoreCase("")) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("BookSalViewID").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the StudentID."));
			}
			valid = false;

		}

		return valid;
	}

	public void submitView(ValueChangeEvent event) {
		logger.debug("-------inside submit1-----");
		bookSaleDataBean.setStudID(event.getNewValue().toString());
		SmsController controller = null;
		String status = null;

		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			logger.debug("-------person id-------" + personID);
			if (personID != null) {
				status = controller.getRoll(personID);
				logger.debug("-----status----" + status);
				if (status.equalsIgnoreCase("Parent")) {
					setSflag(false);
				}
				if (status.equalsIgnoreCase("Student") || status.equalsIgnoreCase("Teacher")) {
					setSflag(true);
				}
				booklist = controller.getBookViewInfo(personID, bookSaleDataBean);
				setFlag3(false);

			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}

	}
	
	public String view()
	{
		logger.debug("inside view method --- "+bookSaleDataBean.getOrderNumber());
		SmsController controller = null;
		try
		{
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			bookinfolist = controller.getBookView(personID, bookSaleDataBean);
			logger.debug("bookinfolist"+bookinfolist.size());
			//setFlag3(false);
			setInvoiceflag(true);
		}
		catch(Exception e)
		{
			logger.warn("Inside Exception",e);
		}
		return null;
	}
	
	public String returnToHome() {
		logger.debug("--------inside returntohome-----");
		logger.debug("--------inside returntohome-----");
		setBoxflag(false);
		setInvoiceflag(false);
		try {
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (personID != null) {
				return "SuccessHome10";
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		logger.debug("--------out returntohome-----");
		return "SuccessHome10";
	}

	public void delete() {
		SmsController controller = null;
		try {
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			logger.debug("----------------" + bookSaleDataBean.getOrderNumber());
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			if (personID != null && bookSaleDataBean.getOrderNumber() != null) {
				String status = controller.deletebooksale(personID, bookSaleDataBean);
				if (status.equalsIgnoreCase("Success")) {
					setDelBoxflag(true);
				}
			}
		} catch (Exception e) {
			logger.warn("Inside Exception",e);
			logger.warn("Exception -->"+e.getMessage());
		}
	}
	
	public String edit()
	{
		logger.debug("inside edit method --- "+bookSaleDataBean.getOrderNumber());
		SmsController controller = null;
		try
		{
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			bookinfolist = controller.getBookView(personID, bookSaleDataBean);
			logger.debug("bookinfolist---"+bookinfolist.size());
			logger.debug("quantityyyy====="+bookSaleDataBean.getBookinfolist().get(0).getOldQuan());
			//setFlag3(false);
			//setInvoiceflag(true);
			setQtyflag(true);
			setQtyflag1(false);
			bookSaleDataBean.setQtyflag(true);
			bookSaleDataBean.setQtyflag1(false);
		}
		catch(Exception e)
		{
			logger.warn("Inside Exception",e);
		}
		return "booksaleEditpage";
	}
	
	public void add(ValueChangeEvent vc) {
		logger.debug("-------inside add------");
		String price = "";
		String amt = "";
		String amt1 = "";
		String no = "";
		String name = "";
		String quantity = "";
		String order = "";
		BigDecimal order1 = new BigDecimal(0);
		BigDecimal order2 = new BigDecimal(0);
		BigDecimal Quant = new BigDecimal(0);
		BigDecimal pr = new BigDecimal(0);
		BigDecimal total = new BigDecimal(0);
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		logger.debug("new =="+vc.getNewValue().toString());
		logger.debug("old =="+vc.getOldValue().toString());
		try {
			total = new BigDecimal(bookSaleDataBean.getTotal());
			order1 = new BigDecimal((String) vc.getNewValue());
			order2 = new BigDecimal((String) vc.getOldValue());
			Quant = new BigDecimal(vc.getComponent().getAttributes().get("quantity").toString());
			pr = new BigDecimal(vc.getComponent().getAttributes().get("price").toString());
			amt = order1.multiply(pr).toString();
			amt1= order2.multiply(pr).toString();
			bookSaleDataBean.setNetAmount(amt);
			logger.debug("----net amount----" + amt);
			logger.debug("-----old net amount -----"+amt1);
			if (order1.compareTo(Quant) == 1) {
				fieldName = CommonValidate.findComponentInRoot("textOrder").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the correct Quantity."));
			} else {
				no = vc.getComponent().getAttributes().get("sno").toString();
				name = vc.getComponent().getAttributes().get("name").toString();
				quantity = vc.getComponent().getAttributes().get("quantity").toString();
				order = (String) vc.getNewValue();
				price = vc.getComponent().getAttributes().get("price").toString();
				logger.debug("price  -- "+price);
				BookSaleDataBean obj = new BookSaleDataBean();
				obj.setSerial(no);
				obj.setBookName(name);
				obj.setBookPrice(price);
				obj.setQtyflag(false);
				obj.setQtyflag1(true);
				obj.setBookQuantity(quantity);
				obj.setOldQuan(order2.toString());
				obj.setNetAmount(amt);
				obj.setOrder(order);
				bookinfolist.set(Integer.parseInt(no) - 1, obj);
				bookSaleDataBean.setBookinfolist(bookinfolist);
				total = total.add(new BigDecimal(amt)).subtract(new BigDecimal(amt1));
				logger.debug("--total amount--" + total);
			}
			bookSaleDataBean.setTotal(total.toString());
			
			setFlag(false);
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			logger.warn("Inside Exception",e);
		}
	}
	
	public String update()
	{
		logger.debug("inside view method --- "+bookSaleDataBean.getOrderNumber());
		SmsController controller = null;
		String status="fail";
		try
		{
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (personID != null) {
				bookSaleDataBean.setStudID(bookSaleDataBean.getStudID());
				bookSaleDataBean.setBookPrice(bookSaleDataBean.getBookPrice());
				logger.debug("---------studid-------" + bookSaleDataBean.getStudID());
				status = controller.bookOrderUpdate(personID, bookSaleDataBean);
				if(status.equalsIgnoreCase("Success"))
				{
					setBoxflag(true);
				}
			}
		}
		catch(Exception e)
		{
			logger.warn("Inside Exception",e);
		}
		return "";
	}
	
	public String returnToview() 
	{
		setBoxflag(false);
		try {
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (personID != null) {
				return "redirectView";
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "redirectView";
	}
	
	public String bookSalesviewLoad() {
		logger.debug("------------inside booksales view-----");
		SmsController controller = null;
		setFlag3(true);
		setFlag(true);
		setBoxflag(false);
		setDelBoxflag(false);
		setInvoiceflag(false);
		String rollnumber = "";
		bookSaleDataBean.setClassname(null);
		bookSaleDataBean.setStudID(null);
		try {
			bookSaleDataBean.setTotal("0");
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			role = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role");	
			if (personID != null) {
				//rollnumber = controller.getRollNumber(personID);
				if(role.equalsIgnoreCase("Student")){
					booklist = controller.getBookViewInfo(personID, bookSaleDataBean);
					setFlag3(false);
					classstuflag="none";
				}else{
				classList = controller.ClassListbooksale(personID,bookSaleDataBean);
				logger.debug("classList --"+classList.size());
				Collections.sort(classList);
				bookSaleDataBean.setStudID(rollnumber);
				setFlag3(true);
				}				
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "Booksalesview";
	}
}
