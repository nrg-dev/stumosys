package com.stumosys.managedBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

//import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.event.RowEditEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.stumosys.controler.SmsController;
import com.stumosys.domain.BookSaleDataBean;
/*import com.stumosys.domain.StudentPerformanceDataBean;
import com.stumosys.shared.BookOrderRecord;
import com.stumosys.shared.BookSale;
*/import com.stumosys.util.CommonValidate;

@ManagedBean(name = "bookSaleMB")
@RequestScoped
public class BookSaleMB {

	BookSaleDataBean bookSaleDataBean = new BookSaleDataBean();
	private static Logger logger = Logger.getLogger(BookSaleMB.class);

	private List<String> sRoll = null;
	private List<String> sRoll1 = null;
	private String clssection;
	List<BookSaleDataBean> booklist = null;
	List<BookSaleDataBean> booklisttt = null;
	List<BookSaleDataBean> booklist1 = null;
	private boolean flag3 = true;
	private boolean flag = true;
	private boolean invoiceflag = false;
	List<BookSaleDataBean> filteredStudent;
	private boolean sflag = false;
	private boolean flag4 = true;
	private boolean flag5 = true;
	private boolean buttonflag = true;
	List<String> classList=null;
	List<String> rollnumber=null;
	
	/*
	 * private List<BookSaleDataBean> bookSaleData =new
	 * ArrayList<BookSaleDataBean>();
	 * 
	 * public List<BookSaleDataBean> complete() { return booklist;
	 * 
	 * }
	 */
	/*
	 * public List<BookSaleDataBean> complete(String query) {
	 * List<BookSaleDataBean> allThemes = new ArrayList<BookSaleDataBean>();
	 * allThemes.addAll(bookSaleData); List<BookSaleDataBean> filteredThemes =
	 * new ArrayList<BookSaleDataBean>();
	 * 
	 * for (int i = 0; i < allThemes.size(); i++) { BookSaleDataBean skin =
	 * allThemes.get(i); if(skin.getBookName().toLowerCase().startsWith(query))
	 * { filteredThemes.add(skin); } }
	 * 
	 * return filteredThemes; }
	 */
	/*
	 * @PostConstruct public void init(){ BookSaleDataBean book=new
	 * BookSaleDataBean(); book.setBookName("Hai"); bookSaleData.add(book);
	 * 
	 * book=new BookSaleDataBean(); book.setBookName("Hai2");
	 * bookSaleData.add(book);
	 * 
	 * book=new BookSaleDataBean(); book.setBookName("Boo");
	 * bookSaleData.add(book);
	 * 
	 * book=new BookSaleDataBean(); book.setBookName("Boo2");
	 * bookSaleData.add(book); }
	 */
	
	public boolean isFlag5() {
		return flag5;
	}

	public boolean isButtonflag() {
		return buttonflag;
	}

	public void setButtonflag(boolean buttonflag) {
		this.buttonflag = buttonflag;
	}

	public List<BookSaleDataBean> getBooklisttt() {
		return booklisttt;
	}

	public void setBooklisttt(List<BookSaleDataBean> booklisttt) {
		this.booklisttt = booklisttt;
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

	public void setFlag5(boolean flag5) {
		this.flag5 = flag5;
	}

	public boolean isFlag4() {
		return flag4;
	}

	public void setFlag4(boolean flag4) {
		this.flag4 = flag4;
	}

	private boolean boxflag = false;

	public boolean isBoxflag() {
		return boxflag;
	}

	public void setBoxflag(boolean boxflag) {
		this.boxflag = boxflag;
	}

	List<String> idlist = null;

	public List<BookSaleDataBean> getBooklist1() {
		return booklist1;
	}

	public void setBooklist1(List<BookSaleDataBean> booklist1) {
		this.booklist1 = booklist1;
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

	public boolean isFlag3() {
		return flag3;
	}

	public void setFlag3(boolean flag3) {
		this.flag3 = flag3;
	}

	public List<BookSaleDataBean> getBooklist() {
		return booklist;
	}

	public void setBooklist(List<BookSaleDataBean> booklist) {
		this.booklist = booklist;
	}

	public boolean isSflag() {
		return sflag;
	}

	public void setSflag(boolean sflag) {
		this.sflag = sflag;
	}

	public String getClssection() {
		return clssection;
	}

	public void setClssection(String clssection) {
		this.clssection = clssection;
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

	public BookSaleDataBean getBookSaleDataBean() {
		return bookSaleDataBean;
	}

	public void setBookSaleDataBean(BookSaleDataBean bookSaleDataBean) {
		this.bookSaleDataBean = bookSaleDataBean;
	}

	/*
	 * public String bookSalePage() { logger.debug(
	 * "--------inside booksalepage-----");
	 * bookSaleDataBean.setPerClassSection(""); bookSaleDataBean.setStudID("");
	 * SmsController controller=null; setFlag3(true); setFlag(true);
	 * setBoxflag(false); try { sRoll=new ArrayList<String>();
	 * ApplicationContext ctx =
	 * FacesContextUtils.getWebApplicationContext(FacesContext.
	 * getCurrentInstance()); controller = (SmsController)
	 * ctx.getBean("controller");
	 * idlist=controller.getPerClassList1(bookSaleDataBean); for(int
	 * i=0;i<bookSaleDataBean.getStuClassList().size();i++) {
	 * sRoll.add(bookSaleDataBean.getStuClassList().get(i).getStuCls1()); } }
	 * catch(Exception e) { logger.warn("Exception -->"+e.getMessage()); } return
	 * "Booksalesorder";
	 * 
	 * }
	 */

	public String bookSalePage() {
		logger.debug("--------inside booksalepage-----");
		setBoxflag(false);
		setInvoiceflag(false);
		bookSaleDataBean.setClassname(null);
		bookSaleDataBean.setStudID(null);
		SmsController controller = null;
		try {
			bookSaleDataBean.setTotal("0");
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (personID != null) {
				classList = controller.ClassListbooksale(personID,bookSaleDataBean);
				Collections.sort(classList);
				//rollnumber = controller.getRollNumber(personID);
				//bookSaleDataBean.setStudID(rollnumber);
				booklist = controller.getBookInfo(personID, bookSaleDataBean);
				setFlag3(false);

			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			logger.warn("Inside Exception",e);
		}
		return "Booksalesorder";

	}
	/*Neela Oct 25 studIDchange*/
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
		}
		catch(Exception e)
		{
			logger.warn("Inside Exception",e);
		}
	}
	
	/*public void booklistchange(ValueChangeEvent event) 
	{
		SmsController controller = null;
		try
		{
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			String classname=event.getNewValue().toString();
			rollnumber = controller.getstudentRollNumber(personID,classname);
		}
		catch(Exception e)
		{
			logger.warn("Inside Exception",e);
		}
	}*/
	
	public String submit() {

		try {
			logger.debug("---------- submit ----");
			if (validate(true)) {
				return "";
			}
		} catch (Exception e) {
			logger.debug("---------- submit Metho-----");

			logger.warn("Exception -->"+e.getMessage());
		}
		return "";
	}

		public String reset() {
		logger.debug(" ....Reset ...");
		bookSaleDataBean.setClassname("");
		bookSaleDataBean.setSection("");
		bookSaleDataBean.setStudentid("");

		return "";

	}

	/*
	 * public void classSection(ValueChangeEvent e) { logger.debug(
	 * "-------inside test method------");
	 * 
	 * SmsController controller=null; try { sRoll1=new ArrayList<String>();
	 * ApplicationContext ctx =
	 * FacesContextUtils.getWebApplicationContext(FacesContext.
	 * getCurrentInstance()); controller = (SmsController)
	 * ctx.getBean("controller"); bookSaleDataBean.setPerClassSection((String)
	 * e.getNewValue()); clssection=controller.getPerClass(bookSaleDataBean);
	 * logger.debug("------size------"+bookSaleDataBean.getRollnolist());
	 * if(bookSaleDataBean.getRollnolist().size()>0) { for(int
	 * i=0;i<bookSaleDataBean.getRollnolist().size();i++) {
	 * sRoll1.add(bookSaleDataBean.getRollnolist().get(i).getStudID()); } } }
	 * catch(Exception v) { v.printStackTrace(); } }
	 */
	/*
	 * public void submit1(ValueChangeEvent event) { logger.debug(
	 * "-------inside submit1-----");
	 * bookSaleDataBean.setStudID(event.getNewValue().toString()); SmsController
	 * controller=null; String status=null;
	 * 
	 * try { bookSaleDataBean.setTotal("0"); ApplicationContext ctx =
	 * FacesContextUtils.getWebApplicationContext(FacesContext.
	 * getCurrentInstance()); controller = (SmsController)
	 * ctx.getBean("controller"); String personID=(String)
	 * FacesContext.getCurrentInstance().getExternalContext().getSessionMap().
	 * get("LogID"); logger.debug("-------person id-------"+personID);
	 * if(personID!=null) { status=controller.getRoll(personID);
	 * logger.debug("-----status----"+status);
	 * if(status.equalsIgnoreCase("Parent")) { setSflag(false); }
	 * if(status.equalsIgnoreCase("Student") ||
	 * status.equalsIgnoreCase("Teacher")) { setSflag(true); }
	 * booklist=controller.getBookInfo(personID,bookSaleDataBean);
	 * setFlag3(false);
	 * 
	 * } } catch(Exception e) { logger.warn("Exception -->"+e.getMessage()); }
	 * 
	 * }
	 */
	public void order(ValueChangeEvent event) {
		logger.debug("---------inside order------");
		String no = "";
		String name = "";
		String price = "";
		String quantity = "";
		SmsController controller = null;
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (personID != null) {
				if (event.getNewValue().toString().trim().equalsIgnoreCase("true")) {
					no = event.getComponent().getAttributes().get("sno").toString();
					name = event.getComponent().getAttributes().get("name").toString();
					price = event.getComponent().getAttributes().get("price").toString();
					logger.debug("------price-----" + price);
					quantity = event.getComponent().getAttributes().get("quantity").toString();
					logger.debug("------no----------" + no);
					BookSaleDataBean obj = new BookSaleDataBean();
					obj.setSerial(no);
					obj.setBookName(name);
					obj.setBookPrice(price);
					obj.setBookQuantity(quantity);
					obj.setFlag(false);
					bookSaleDataBean.getBooklist2().set(Integer.parseInt(no) - 1, obj);

				}

			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		finally
		{
			if(controller!=null){
				controller=null;
			}
		}
	}

	public void add(ValueChangeEvent vc) {
		logger.debug("-------inside add------");
		String price = "";
		String amt = "";
		String no = "";
		String name = "";
		String quantity = "";
		String order = "";
		BigDecimal order1 = new BigDecimal(0);
		BigDecimal Quant = new BigDecimal(0);
		BigDecimal pr = new BigDecimal(0);
		BigDecimal total = new BigDecimal(0);
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			total = new BigDecimal(bookSaleDataBean.getTotal());
			order1 = new BigDecimal((String) vc.getNewValue());
			Quant = new BigDecimal(vc.getComponent().getAttributes().get("quantity").toString());
			pr = new BigDecimal(vc.getComponent().getAttributes().get("price").toString());
			amt = order1.multiply(pr).toString();
			bookSaleDataBean.setNetAmount(amt);
			logger.debug("----net amount----" + amt);
			if (order1.compareTo(Quant) == 1) {
				fieldName = CommonValidate.findComponentInRoot("textOrder").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the correct Quantity."));
			} else {
				no = vc.getComponent().getAttributes().get("sno").toString();
				name = vc.getComponent().getAttributes().get("name").toString();
				quantity = vc.getComponent().getAttributes().get("quantity").toString();
				order = (String) vc.getNewValue();
				price = vc.getComponent().getAttributes().get("price").toString();
				BookSaleDataBean obj = new BookSaleDataBean();
				obj.setSerial(no);
				obj.setBookName(name);
				obj.setBookPrice(price);
				obj.setFlag(false);
				obj.setFlag1(true);
				obj.setBookQuantity(quantity);
				obj.setNetAmount(amt);
				obj.setOrder(order);
				bookSaleDataBean.getBooklist2().set(Integer.parseInt(no) - 1, obj);
				total = total.add(new BigDecimal(amt));
				logger.debug("--total amount--" + total);
			}
			bookSaleDataBean.setTotal(total.toString());
			setFlag4(false);
			setFlag(false);
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
	}

	public String submit2() {
		logger.debug("----------inside submit2------");
		setFlag4(true);
		String status = "";
		int count=0;
		int s=1;
		setInvoiceflag(false);
		SmsController controller = null;
		try {
			if(validate(true))
			{
			for (int i = 0; i < booklist.size(); i++) {
				logger.debug("==========="+booklist.get(i).getOrder());
				try{
					if(booklist.get(i).getOrder().equalsIgnoreCase("")||booklist.get(i).getOrder()==null)
					{
						logger.debug("-----");
						count++;
					}
				}catch(Exception e){
					count++;
				}
			}
			logger.debug("count"+count);
			logger.debug("booklist--"+booklist.size());
			if(booklist.size() == count)
			{
				logger.debug("hhhhhhhhhh");
				String fieldName;
				FacesContext fc = FacesContext.getCurrentInstance();
				fieldName = CommonValidate.findComponentInRoot("textOrder").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Quantity."));
			}
			else
			{
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (personID != null) {
				bookSaleDataBean.setStudID(bookSaleDataBean.getStudID());
				bookSaleDataBean.setBookPrice(bookSaleDataBean.getBookPrice());
				logger.debug("---------studid-------" + bookSaleDataBean.getStudID());
				status = controller.addBookOrder(personID, bookSaleDataBean);
				booklisttt=new ArrayList<BookSaleDataBean>();
				for (int i = 0; i < bookSaleDataBean.getBooklist2().size(); i++) {
					if (bookSaleDataBean.getBooklist2().get(i).getNetAmount() != null) {
						BookSaleDataBean book1 = new BookSaleDataBean();
						book1.setSerial(String.valueOf(s));
						book1.setBookName(bookSaleDataBean.getBooklist2().get(i).getBookName());
						book1.setBookPrice(bookSaleDataBean.getBooklist2().get(i).getBookPrice());
						book1.setOrder(bookSaleDataBean.getBooklist2().get(i).getOrder());
						book1.setNetAmount(bookSaleDataBean.getBooklist2().get(i).getNetAmount());
						booklisttt.add(book1);
						s++;
					}
				}
				if (status.equalsIgnoreCase("Success")) {
					//setBoxflag(true);
					setInvoiceflag(true);
				}

			}
			}
		} 
		}catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "";
	}

	public String returnToHome() {
		logger.debug("--------inside returntohome-----");
		setBoxflag(false);
		setFlag4(true);
		try {
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (personID != null) {
				return "SuccessHome5";
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "SuccessHome5";
	}
	public String showQty()
	{
		try
		{
			logger.debug("iiiinnnnnnn"+bookSaleDataBean.getSerial());
			BookSaleDataBean obj = new BookSaleDataBean();
			obj.setSerial(bookSaleDataBean.getSerial());
			obj.setBookName(booklist.get(Integer.parseInt(bookSaleDataBean.getSerial())-1).getBookName());
			obj.setBookPrice(booklist.get(Integer.parseInt(bookSaleDataBean.getSerial())-1).getBookPrice());
			obj.setBookQuantity(booklist.get(Integer.parseInt(bookSaleDataBean.getSerial())-1).getBookQuantity());
			
			obj.setFlag(true);
			obj.setFlag1(false);
			booklist.set(Integer.parseInt(bookSaleDataBean.getSerial()) - 1, obj);
			}
		catch(Exception e)
		{
			logger.warn(" exception - "+e);
		}
		return null;
	}
	
	public void onRowEdit(RowEditEvent event){
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		String quantity="";
		String bookname="";
		quantity = ((BookSaleDataBean) event.getObject()).getBookQuantity().toString();
		if(quantity!=null){
			if(!CommonValidate.validateNumber(quantity)){
				fieldName = CommonValidate.findComponentInRoot("quantityid").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Enter the Valid Quantity."));
			}else{
				bookname = ((BookSaleDataBean) event.getObject()).getBookName().toString();
				bookSaleDataBean.setBookName(bookname);
				bookSaleDataBean.setBookQuantity(quantity);
				try{
					String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
					ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
					 SmsController controller = (SmsController) ctx.getBean("controller");
					 String status = controller.bookstockin(bookSaleDataBean,personID);
					 if(status.equalsIgnoreCase("Success")){
						 	FacesMessage msg = new FacesMessage("Sucessfully Saved Book Quantity",
										bookSaleDataBean.getBookName());
								FacesContext.getCurrentInstance().addMessage(null, msg);
					 }
				}
				catch(Exception e){
					logger.warn(" exception - "+e);
				}
			}
		}
		
		}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edit Cancelled", ((BookSaleDataBean) event.getObject()).getBookName());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	private boolean validate(boolean flag) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();

		if (StringUtils.isEmpty(bookSaleDataBean.getClassname())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("classID").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the class."));
			}
			valid = false;

		}
		if (StringUtils.isEmpty(bookSaleDataBean.getStudID())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("perStudentID").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the Student Name."));
			}
			valid = false;

		}

		return valid;
	}
	
}
