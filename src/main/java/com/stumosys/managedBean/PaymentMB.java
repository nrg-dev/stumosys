package com.stumosys.managedBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
//import java.util.Properties;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

/*import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
*/
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.stumosys.controler.SmsController;
import com.stumosys.domain.BookSaleDataBean;
import com.stumosys.domain.PaymentDatabean;
import com.stumosys.util.CommonValidate;
import com.stumosys.util.MailSendJDBC;


@ManagedBean(name = "paymentMB")
@RequestScoped
public class PaymentMB {

	PaymentDatabean paymentDatabean=new PaymentDatabean();
	private static Logger logger = Logger.getLogger(PaymentMB.class);
	FacesContext context = FacesContext.getCurrentInstance();
	ResourceBundle text = context.getApplication().evaluateExpressionGet(context, "#{text}", ResourceBundle.class);
	private List<String> sRoll = null;
	private List<String> sRoll1 = null;
	private List<PaymentDatabean> paymentList = null;
	private List<PaymentDatabean> paymentViewList = null;
	private boolean successFlag=false;
	private boolean existFlag=false;
	private boolean editFlag=false;
	private List<String> classlist=null;
	public List<String> getClasslist() {
		return classlist;
	}

	public void setClasslist(List<String> classlist) {
		this.classlist = classlist;
	}
	BookSaleDataBean bookSaleDataBean = new BookSaleDataBean();

	ResourceBundle paths = context.getApplication().evaluateExpressionGet(context, "#{paths}", ResourceBundle.class);
	private String clssection;
	List<BookSaleDataBean> booklist = null;
	List<BookSaleDataBean> booklist1 = null;
	private boolean flag3 = true;
	private boolean flag = true;
	List<BookSaleDataBean> filteredStudent;
	private boolean sflag = false;
	private boolean boxflag = false;
	private boolean oldschoolflag=false;
	private boolean classflag=false;
	private boolean studentflag=false;
	
	public boolean isClassflag() {
		return classflag;
	}

	public void setClassflag(boolean classflag) {
		this.classflag = classflag;
	}

	public boolean isStudentflag() {
		return studentflag;
	}

	public void setStudentflag(boolean studentflag) {
		this.studentflag = studentflag;
	}

	public boolean isOldschoolflag() {
		return oldschoolflag;
	}

	public void setOldschoolflag(boolean oldschoolflag) {
		this.oldschoolflag = oldschoolflag;
	}

	public boolean isNewschoolflag() {
		return newschoolflag;
	}

	public void setNewschoolflag(boolean newschoolflag) {
		this.newschoolflag = newschoolflag;
	}
	private boolean newschoolflag=false;

	public BookSaleDataBean getBookSaleDataBean() {
		return bookSaleDataBean;
	}

	public void setBookSaleDataBean(BookSaleDataBean bookSaleDataBean) {
		this.bookSaleDataBean = bookSaleDataBean;
	}

	public ResourceBundle getPaths() {
		return paths;
	}

	public void setPaths(ResourceBundle paths) {
		this.paths = paths;
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

	public List<BookSaleDataBean> getBookviewlist() {
		return bookviewlist;
	}

	public void setBookviewlist(List<BookSaleDataBean> bookviewlist) {
		this.bookviewlist = bookviewlist;
	}

	public List<BookSaleDataBean> getBookinfolist() {
		return bookinfolist;
	}

	public void setBookinfolist(List<BookSaleDataBean> bookinfolist) {
		this.bookinfolist = bookinfolist;
	}

	public String getPersonID() {
		return personID;
	}

	public void setPersonID(String personID) {
		this.personID = personID;
	}
	List<String> rollnumber=null;
	
	List<String> classList=null;
	List<String> idlist = null;
	List<BookSaleDataBean> bookviewlist = null;
	private boolean invoiceflag = false;
	List<BookSaleDataBean> bookinfolist = null;
	private boolean delBoxflag = false;
	private boolean adminflag=false;
	private boolean parentflag=false;
	private StreamedContent file;
	Date now = new Date();
	SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
private boolean uploadflag= false;
	
	private UploadedFile files;
	
	public SimpleDateFormat getFt() {
		return ft;
	}

	public void setFt(SimpleDateFormat ft) {
		this.ft = ft;
	}

	public Date getNow() {
		return now;
	}

	public void setNow(Date now) {
		this.now = now;
	}

	public boolean isEditFlag() {
		return editFlag;
	}

	public void setEditFlag(boolean editFlag) {
		this.editFlag = editFlag;
	}

	public List<PaymentDatabean> getPaymentViewList() {
		return paymentViewList;
	}

	public void setPaymentViewList(List<PaymentDatabean> paymentViewList) {
		this.paymentViewList = paymentViewList;
	}

	public List<PaymentDatabean> getPaymentList() {
		return paymentList;
	}

	public void setPaymentList(List<PaymentDatabean> paymentList) {
		this.paymentList = paymentList;
	}

	public boolean isSuccessFlag() {
		return successFlag;
	}

	public void setSuccessFlag(boolean successFlag) {
		this.successFlag = successFlag;
	}

	public boolean isExistFlag() {
		return existFlag;
	}

	public void setExistFlag(boolean existFlag) {
		this.existFlag = existFlag;
	}

	public List<String> getsRoll1() {
		return sRoll1;
	}

	public void setsRoll1(List<String> sRoll1) {
		this.sRoll1 = sRoll1;
	}
	public List<String> getsRoll() {
		return sRoll;
	}

	public void setsRoll(List<String> sRoll) {
		this.sRoll = sRoll;
	}

	public PaymentDatabean getPaymentDatabean() {
		return paymentDatabean;
	}

	public void setPaymentDatabean(PaymentDatabean paymentDatabean) {
		this.paymentDatabean = paymentDatabean;
	}
	
	public List<String> getRollnumber() {
		return rollnumber;
	}

	public void setRollnumber(List<String> rollnumber) {
		this.rollnumber = rollnumber;
	}
	
	public String feesReg() {
		paymentDatabean.setPayClassSection("");
		paymentDatabean.setPaymentStudID("");
		paymentDatabean.setExamFees("");
		paymentDatabean.setTransportFees("");
		paymentDatabean.setTuitionFees("");
		SmsController controller = null;
		setSuccessFlag(false);
		setExistFlag(false);
		String status = "";
		paymentDatabean.setTitle("");
		paymentDatabean.setAmount("");
		paymentDatabean.setDescription("");
		paymentDatabean.setDuedate(null);
		paymentDatabean.setOptions("");
		paymentDatabean.setPayclass("");
		setOldschoolflag(false);
		setNewschoolflag(false);
		setClassflag(false);
		setStudentflag(false);
		try {
			logger.info("-----feesReg method calling-----");
			String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			int schoolid=Integer.parseInt(schoolID);
			if(schoolid==40){
				paymentDatabean.setMonth(String.valueOf(Calendar.getInstance().get(Calendar.MONTH)+1));
				paymentDatabean.setTransportFees("0");
				paymentDatabean.setTuitionFees("0");
				paymentDatabean.setSpecialFees("0");
				paymentDatabean.setEcaFees("0");
				paymentDatabean.setLabFees("0");
				paymentDatabean.setAdmissionFees("0");
			}	
			if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6 || schoolid==37 || schoolid==44 || schoolid==47){
				setOldschoolflag(true);
				setNewschoolflag(false);
			}else if(schoolid==40 ){
				setOldschoolflag(false);
				setNewschoolflag(false);
			}else{
				setOldschoolflag(true);
				setNewschoolflag(false);
			}
			sRoll = new ArrayList<String>();
			classlist=new ArrayList<String>();
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			controller = (SmsController) ctx.getBean("controller");
			if (personID != null) {
				status = controller.getRoll(personID);
				logger.debug("-----status----" + status);
				classlist = controller.getClassList(personID);
				Collections.sort(classlist);
				controller.getPerClassList1(personID,paymentDatabean);
				logger.debug("---------dom size-----" + paymentDatabean.getStuClassList().size());
				for (int i = 0; i < paymentDatabean.getStuClassList().size(); i++) {
					sRoll.add(paymentDatabean.getStuClassList().get(i).getStuCls1());
				}
			}
		}
		catch (Exception e) {
			//logger.debug("-------feesReg method Exception calling------");
			logger.warn("Exception -->"+e.getMessage());
		}

		return "feesRegistration";
	}
	public void classSection(ValueChangeEvent e) {
		logger.debug("-------inside test method------");
		SmsController controller = null;
		sRoll1=null;
		try {
			sRoll1 = new ArrayList<String>();
			String classname= e.getNewValue().toString();
			logger.debug("Insde"+classname);
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			controller = (SmsController) ctx.getBean("controller");
			if(classname.equalsIgnoreCase("select")){
				sRoll1 = new ArrayList<String>();
			}else{
			paymentDatabean.setParentID(schoolID);
			paymentDatabean.setPayClassSection(classname);
			controller.getPerClass(paymentDatabean);
			if(paymentDatabean.getRollnolist().size() > 0){
			for (int i = 0; i < paymentDatabean.getRollnolist().size(); i++) {
				sRoll1.add(paymentDatabean.getRollnolist().get(i).getPaymentStudID());
			}
			}else{
				
				sRoll1 = new ArrayList<String>();
			}
			}
		} catch (Exception v) {
			logger.warn(v.getMessage());
		}
	}
	
	private boolean valiateSMRV(boolean flag,boolean valid,String fieldName,FacesContext fc){

		logger.info("Inside SMRV School Condition");
		if (StringUtils.isEmpty(paymentDatabean.getPayClassSection())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("validation").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Login And Try Again,Something Went Worng"));
			}
			valid = false;
		}
		
		if (StringUtils.isEmpty(paymentDatabean.getPaymentStudID())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("validation").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Login And Try Again,Something Went Worng"));
			}
			valid = false;
		}
		if (StringUtils.isEmpty(paymentDatabean.getTuitionFees())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("paytuiFeesSMRV").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.payment.fees")));
			}
			valid = false;
		} else if (!StringUtils.isEmpty(paymentDatabean.getTuitionFees())) {
			if (!CommonValidate.validateNumberOnly(paymentDatabean.getTuitionFees())) {
				logger.debug("character");
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("paytuiFeesSMRV").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.payment.vfees")));
				}
				valid = false;
			}else if(new BigDecimal(paymentDatabean.getTuitionFees()).compareTo(BigDecimal.ZERO)==0){
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("paytuiFeesSMRV").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.payment.vfees")));
				}
				valid = false;
			}
		}
	
		return valid;
	}
	
	private boolean ValidateMAHARISHI(boolean flag,boolean valid,String fieldName,FacesContext fc){
		

		logger.info("Inside MAHARISHI School Condition");
		if (StringUtils.isEmpty(paymentDatabean.getPayClassSection())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("validation").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Login And Try Again,Something Went Worng"));
			}
			valid = false;
		}
		
		if (StringUtils.isEmpty(paymentDatabean.getPaymentStudID())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("validation").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Login And Try Again,Something Went Worng"));
			}
			valid = false;
		}
		
		
		if (StringUtils.isEmpty(paymentDatabean.getTuitionFees())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("schoolFees").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.payment.fees")));
			}
			valid = false;
		} else if (!StringUtils.isEmpty(paymentDatabean.getTuitionFees())) {
			if (!CommonValidate.validateNumberOnly(paymentDatabean.getTuitionFees())) {
				logger.debug("character");
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("schoolFees").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.payment.vfees")));
				}
				valid = false;
			}else if(new BigDecimal(paymentDatabean.getTuitionFees()).compareTo(BigDecimal.ZERO)==0){
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("schoolFees").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.payment.vfees")));
				}
				valid = false;
			}
		}
		
		if (StringUtils.isEmpty(paymentDatabean.getTransportFees())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("vanFees").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.payment.fees")));
			}
			valid = false;
		} else if (!StringUtils.isEmpty(paymentDatabean.getTransportFees())) {
			if (!CommonValidate.validateNumberOnly(paymentDatabean.getTransportFees())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("vanFees").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.payment.vfees")));
				}
				valid = false;
			}
			else if(new BigDecimal(paymentDatabean.getTransportFees()).compareTo(BigDecimal.ZERO)==0){
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("vanFees").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.payment.vfees")));
				}
				valid = false;
			}
		}
		
		
		 if(paymentDatabean.getClassName().equalsIgnoreCase(paths.getString("MAHARISHI.CLASS.STDI")) || 
				 paymentDatabean.getClassName().equalsIgnoreCase(paths.getString("MAHARISHI.CLASS.STDII"))){
			 if (StringUtils.isEmpty(paymentDatabean.getExtraFee1())) {
					if (flag) {
						fieldName = CommonValidate.findComponentInRoot("ecaFees").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage(text.getString("sms.payment.fees")));
					}
					valid = false;
				} else if (!StringUtils.isEmpty(paymentDatabean.getExtraFee1())) {
					if (!CommonValidate.validateNumberOnly(paymentDatabean.getExtraFee1())) {
						if (flag) {
							fieldName = CommonValidate.findComponentInRoot("ecaFees").getClientId(fc);
							fc.addMessage(fieldName, new FacesMessage(text.getString("sms.payment.vfees")));
						}
						valid = false;
					}
					else if(new BigDecimal(paymentDatabean.getExtraFee1()).compareTo(BigDecimal.ZERO)==0){
						if (flag) {
							fieldName = CommonValidate.findComponentInRoot("ecaFees").getClientId(fc);
							fc.addMessage(fieldName, new FacesMessage(text.getString("sms.payment.vfees")));
						}
						valid = false;
					}
				}
			paymentDatabean.setExtraFee2("0");	
		 }
		 else if(paymentDatabean.getClassName().equalsIgnoreCase(paths.getString("MAHARISHI.CLASS.STDIII")) || 
				 paymentDatabean.getClassName().equalsIgnoreCase(paths.getString("MAHARISHI.CLASS.STDIV")) ||
				 paymentDatabean.getClassName().equalsIgnoreCase(paths.getString("MAHARISHI.CLASS.STDV"))){
			 if (StringUtils.isEmpty(paymentDatabean.getExtraFee1())) {
					if (flag) {
						fieldName = CommonValidate.findComponentInRoot("ecaFees").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage(text.getString("sms.payment.fees")));
					}
					valid = false;
				} else if (!StringUtils.isEmpty(paymentDatabean.getExtraFee1())) {
					if (!CommonValidate.validateNumberOnly(paymentDatabean.getExtraFee1())) {
						if (flag) {
							fieldName = CommonValidate.findComponentInRoot("ecaFees").getClientId(fc);
							fc.addMessage(fieldName, new FacesMessage(text.getString("sms.payment.vfees")));
						}
						valid = false;
					}
					else if(new BigDecimal(paymentDatabean.getExtraFee1()).compareTo(BigDecimal.ZERO)==0){
						if (flag) {
							fieldName = CommonValidate.findComponentInRoot("ecaFees").getClientId(fc);
							fc.addMessage(fieldName, new FacesMessage(text.getString("sms.payment.vfees")));
						}
						valid = false;
					}
				}
				
				if (StringUtils.isEmpty(paymentDatabean.getExtraFee2())) {
					if (flag) {
						fieldName = CommonValidate.findComponentInRoot("abacusFees").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage(text.getString("sms.payment.fees")));
					}
					valid = false;
				} else if (!StringUtils.isEmpty(paymentDatabean.getExtraFee2())) {
					if (!CommonValidate.validateNumberOnly(paymentDatabean.getExtraFee2())) {
						if (flag) {
							fieldName = CommonValidate.findComponentInRoot("abacusFees").getClientId(fc);
							fc.addMessage(fieldName, new FacesMessage(text.getString("sms.payment.vfees")));
						}
						valid = false;
					}
					else if(new BigDecimal(paymentDatabean.getExtraFee2()).compareTo(BigDecimal.ZERO)==0){
						if (flag) {
							fieldName = CommonValidate.findComponentInRoot("abacusFees").getClientId(fc);
							fc.addMessage(fieldName, new FacesMessage(text.getString("sms.payment.vfees")));
						}
						valid = false;
					}
				}
		 }
		 else{
			 paymentDatabean.setExtraFee1("0");
			 paymentDatabean.setExtraFee2("0");
		 }
	
		return valid;
		
	}
	private boolean validateIndo(boolean valid,String fieldName,FacesContext fc){
		valid = true;
		// Class empty check
		if (StringUtils.isEmpty(paymentDatabean.getPayClassSection())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("payClass").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the Student Class."));
			}
			valid = false;
		}
		// Student empty check
		if (StringUtils.isEmpty(paymentDatabean.getPaymentStudID())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("payStudentID").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the Student ID."));
			}
			valid = false;
		}
		// Exam fees empty check
		if (StringUtils.isEmpty(paymentDatabean.getExamFees())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("payexamFees1").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.payment.examfees")));
			}
			valid = false;
		}
		//Tuition fees empty check
		if (StringUtils.isEmpty(paymentDatabean.getTuitionFees())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("paytuiFees1").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.payment.tuitionfees")));
			}
			valid = false;
		}
		// Transport fees empty check
		if (StringUtils.isEmpty(paymentDatabean.getTransportFees())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("paytransFees1").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.payment.transportfees")));
			}
			valid = false;
		}
		
		return valid;
	}
	public boolean validate(boolean flag) {
		logger.info("Inside validate() method");
		boolean valid = true;
		String fieldName=null;
		FacesContext fc = FacesContext.getCurrentInstance();
		String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
		int schoolid=Integer.parseInt(schoolID);
		
		if(schoolid==Integer.parseInt(paths.getString("SMRV.SCHOOLID"))){
			valid=valiateSMRV(flag,valid,fieldName,fc);
			}
		
		else if(schoolid==Integer.parseInt(paths.getString("MAHARISHI.SCHOOLID"))){
			valid=ValidateMAHARISHI(flag,valid,fieldName,fc);
		}
		
		else if(schoolid==Integer.parseInt(paths.getString("SMAN.SCHOOL_ID"))){
			logger.info("Validation check 3");
			valid=validateIndo(valid,fieldName,fc);
		}
		

		else{
			logger.info("Inside Other School Condition");
			if(schoolid!=Integer.parseInt(paths.getString("SCMS.SCHOOLID"))){
				
				logger.info("Inside SCMS School Condition");
			if (StringUtils.isEmpty(paymentDatabean.getPayClassSection())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("payClass").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Select the Student Class."));
				}
				valid = false;
			}
			if (StringUtils.isEmpty(paymentDatabean.getPaymentStudID())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("payStudentID").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Select the Student ID."));
				}
				valid = false;
			}
			
			if (StringUtils.isEmpty(paymentDatabean.getExamFees())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("payexamFees1").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.payment.examfees")));
				}
				valid = false;
			} 
			
			
			else if (!StringUtils.isEmpty(paymentDatabean.getExamFees())) {
				if (!CommonValidate.validateNumberOnly(paymentDatabean.getExamFees())) {
					logger.debug("character");
					if (flag) {
						fieldName = CommonValidate.findComponentInRoot("payexamFees1").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage(text.getString("sms.payment.vexamfees")));
					}
					valid = false;
				}else if(new BigDecimal(paymentDatabean.getExamFees()).compareTo(BigDecimal.ZERO)==0){
					if (flag) {
						fieldName = CommonValidate.findComponentInRoot("payexamFees1").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage(text.getString("sms.payment.vexamfees")));
					}
					valid = false;
				}
			}
			if (StringUtils.isEmpty(paymentDatabean.getTuitionFees())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("paytuiFees1").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.payment.tuitionfees")));
				}
				valid = false;
			} 
			else if (!StringUtils.isEmpty(paymentDatabean.getTuitionFees())) {
				if (!CommonValidate.validateNumberOnly(paymentDatabean.getTuitionFees())) {
					if (flag) {
						fieldName = CommonValidate.findComponentInRoot("paytuiFees1").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage(text.getString("sms.payment.vtuitionfees")));
					}
					valid = false;
				}
				else if(new BigDecimal(paymentDatabean.getTuitionFees()).compareTo(BigDecimal.ZERO)==0){
					if (flag) {
						fieldName = CommonValidate.findComponentInRoot("paytuiFees1").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage(text.getString("sms.payment.vtuitionfees")));
					}
					valid = false;
				}
			}
			if (StringUtils.isEmpty(paymentDatabean.getTransportFees())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("paytransFees1").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.payment.transportfees")));
				}
				valid = false;
			} 
			
			else if (!StringUtils.isEmpty(paymentDatabean.getTransportFees())) {
				if (!CommonValidate.validateNumberOnly(paymentDatabean.getTransportFees())) {
					if (flag) {
						fieldName = CommonValidate.findComponentInRoot("paytransFees1").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage(text.getString("sms.payment.vtransportfees")));
					}
					valid = false;
				}
				else if(new BigDecimal(paymentDatabean.getTransportFees()).compareTo(BigDecimal.ZERO)==0){
					if (flag) {
						fieldName = CommonValidate.findComponentInRoot("paytransFees1").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage(text.getString("sms.payment.vtransportfees")));
					}
					valid = false;
				}
			}
		}
		}
	return valid;
	}
	public String submit(){
		logger.info("submit() 1");
		SmsController controller = null;
		setSuccessFlag(false);
		setExistFlag(false);
		String status = "Fail";
		paymentDatabean.setTotalFees("");
		String totalFees= "";
		try {
			logger.info("submit() 2");
			String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolName");
			String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			int schoolid=Integer.parseInt(schoolID);
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			if(personID != null){
				logger.info("submit() 2");
				if(saveValidation(true)){
				if(paths.getString("SCMS.SCHOOLID").equalsIgnoreCase(schoolID)){
					logger.info("submit() 3");
					logger.info("submit() Inside SCMC School Condition");
					paymentDatabean.setYear(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
					if("6".equalsIgnoreCase(paymentDatabean.getMonth())){
						BigDecimal temp =new BigDecimal(paymentDatabean.getTransportFees()).add(new BigDecimal(paymentDatabean.getTuitionFees()));
						BigDecimal temp1=new BigDecimal(paymentDatabean.getSpecialFees()).add(new BigDecimal(paymentDatabean.getEcaFees()));
						BigDecimal temp2=new BigDecimal(paymentDatabean.getLabFees()).add(new BigDecimal(paymentDatabean.getAdmissionFees()));
						totalFees=temp.add(temp1).add(temp2).toString();
						paymentDatabean.setTotalFees(totalFees);
					}else{
						totalFees =new BigDecimal(paymentDatabean.getTransportFees()).add(new BigDecimal(paymentDatabean.getTuitionFees())).toString();
						paymentDatabean.setTotalFees(totalFees);
					}
				}
				logger.info("submit() 4");
				status = controller.insertfees(personID, paymentDatabean);
				logger.info("submit() 5");
				logger.debug("------->>>>>><<<<<<>>>>>" + status);
				if(status.equalsIgnoreCase("Success")){
					logger.info("submit() 6");
					MailSendJDBC.feesRegister(paymentDatabean,schoolName,schoolid);
					logger.info("submit() 7");
						RequestContext reqcontext = RequestContext.getCurrentInstance();
						reqcontext.execute("PF('feesRegblockUI').hide();");
						reqcontext.execute("PF('saveFeesReg').show();");
						logger.info("submit() 8");		
				}else{
					logger.info("submit() 9");
					RequestContext reqcontext = RequestContext.getCurrentInstance();
					reqcontext.execute("PF('feesRegblockUI').hide();");
					reqcontext.execute("PF('existFeesReg').show();");
					logger.info("submit() 10");
				}
			}
			}
		}catch(NumberFormatException n){
			logger.info("submit() 11");
			paymentDatabean.setTransportFees("0");
			paymentDatabean.setTuitionFees("0");
			paymentDatabean.setSpecialFees("0");
			paymentDatabean.setEcaFees("0");
			paymentDatabean.setLabFees("0");
			paymentDatabean.setAdmissionFees("0");
			FacesMessage message=new FacesMessage("Please enter the amounts should be number");
			FacesContext.getCurrentInstance().addMessage(CommonValidate.findComponentInRoot("feesReg").getClientId(), message);
			logger.warn("Inside Exception"+n.getMessage());
		}catch(Exception e){
			logger.info("submit() 12");
			logger.warn("Exception -->"+e.getMessage());
		}
	return "";
	}
	private boolean saveValidation(boolean valid) {
		valid=true;
		FacesContext fc=FacesContext.getCurrentInstance();
		String schoolID ="";
		String[] msg=null;
		try{
			msg=new String[3];
			msg[0]="Please choose the month";
			msg[1]="Please choose the class name";
			msg[2]="Please choose the student name";
			 schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			 if(paths.getString("SCMS.SCHOOLID").equalsIgnoreCase(schoolID)){
				 if(StringUtils.isEmpty(paymentDatabean.getMonth())){
					fc.addMessage(CommonValidate.findComponentInRoot("month").getClientId(), new FacesMessage(msg[0]));
					valid=false;
				 }
				 if(StringUtils.isEmpty(paymentDatabean.getPayClassSection())){
					 fc.addMessage(CommonValidate.findComponentInRoot("classname").getClientId(), new FacesMessage(msg[1]));
					 valid=false;
				 }
				 if(StringUtils.isEmpty(paymentDatabean.getPaymentStudID())){
					 fc.addMessage(CommonValidate.findComponentInRoot("studentname").getClientId(), new FacesMessage(msg[2]));
					 valid=false;
				 }
			 }
		}catch(Exception e){
			logger.warn("Exception-----"+e.getMessage());
		}
		return valid;
	}

	public String returnToHome2() {
		paymentDatabean.setPayClassSection("");
		paymentDatabean.setPaymentStudID("");
		paymentDatabean.setExamFees("");
		paymentDatabean.setTransportFees("0");
		paymentDatabean.setTuitionFees("0");
		paymentDatabean.setSpecialFees("0");
		paymentDatabean.setEcaFees("0");
		paymentDatabean.setLabFees("0");
		paymentDatabean.setAdmissionFees("0");
		return "";

	}
	public String reset(){
		paymentDatabean.setPayClassSection("");
		paymentDatabean.setPaymentStudID("");
		paymentDatabean.setExamFees("");
		paymentDatabean.setTransportFees("");
		paymentDatabean.setTuitionFees("");
		setSuccessFlag(false);
		setExistFlag(false);
		paymentDatabean.setTitle("");
		paymentDatabean.setAmount("");
		paymentDatabean.setDescription("");
		paymentDatabean.setDuedate(null);
		paymentDatabean.setOptions("");
		paymentDatabean.setPayclass("");
		paymentDatabean.setExtraFee1("");
		paymentDatabean.setExtraFee2("");
		setClassflag(false);
		setStudentflag(false);
		return "";
	}
	
	/*private String sendEmail(String personID, PaymentDatabean paymentDatabean) {
		String status = "Fail";

		try {
			// String to="robertarjun46@gmail.com";
			String to = paymentDatabean.getMailId();
			Properties prop = new Properties();
			prop.put("mail.smtp.host", text.getString("sms.mail.host"));
			prop.put("mail.smtp.socketFactory.port", text.getString("sms.mail.port"));
			prop.put("mail.smtp.socketFactory.class", text.getString("sms.mail.class"));
			prop.put("mail.smtp.starttls.enable", text.getString("sms.mail.smtp.startles"));
			prop.put("mail.smtp.auth", text.getString("sms.mail.auth"));
			prop.put("mail.smtp.port", text.getString("sms.mail.smtp.port"));
			prop.put("mail.smtp.ssl.trust", text.getString("sms.mail.smtp.trust"));
			String body1 = "<htm><head></heade><body>"
					+ " <header style='background-color:orange;color:white;height:50px;'>" + "<br></br>" + "<center>"
					+ "<h1> Welcome to Student Monitoring System</h1><br></br></center>" + "</center>" + "</header>"
					+ "<h3>Dear " + paymentDatabean.getParentID()+ ",</h3>"
					+ "<p>Welcome to join  our Student Monitoring System. "
					+ "you can check your child fees structure. "
					+ "Total Fees  :  Rs.  "+paymentDatabean.getTotalFees()+"</p>"
					+ "<footer>" + "<center>"
					+ "<h4>&copy; 2016 PT. Neotural Era Graha</h4>" + "</center>" + "</footer>" + "</body></html>";

			Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("nrgsolutions.india@gmail.com", "Nrg@1234");
				}
			});

			// compose message
			try {
				
				 * session = Session.getInstance(prop, this);// this is the
				 * local variable not the instance one
				  MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress("nrgsolutions.india@gmail.com"));
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
				message.setSubject("Fees Information  " + paymentDatabean.getParentID());
				BodyPart messageBodyPart1 = new MimeBodyPart();
				messageBodyPart1.setContent(body1, "text/html");

				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart1);
				message.setContent(multipart);

				Transport.send(message);

				logger.debug("message sent successfully");
				status = "Success";

			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}

		} catch (Exception e) {
			logger.warn("Inside Exception",e);
			logger.warn("Exception -->"+e.getMessage());
		}
		return status;
	}*/
	
	public String feesregpage() {
	  try {
	   logger.info("------feesregpage method calling--------");
	   if (validate(true)) {
		   logger.info("feesregpage() 1");
	    String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
	      .get("LogID");
	    if (personID != null) {
	     RequestContext reqcontextt = RequestContext.getCurrentInstance();
	     reqcontextt.execute("feesRegBlk();");// calling JavaScript
	    }
	   }
		logger.info("feeregpage 2");
	  } catch (Exception e) {
	   logger.warn("Exception -->"+e.getMessage());
	   return "failure";
	  }
	  return "";
	 }

	public String bookpayment() {
		logger.debug("------------inside booksales view-----");
		SmsController controller = null;
		setFlag3(true);
		setFlag(true);
		setBoxflag(false);
		setDelBoxflag(false);
		setInvoiceflag(false);
		setAdminflag(false);
		setParentflag(false);
		setUploadflag(false);
		String rollnumber = "";
		bookSaleDataBean.setClassname(null);
		bookSaleDataBean.setStudID(null);
		try {
			bookSaleDataBean.setTotal("0");
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (personID != null) {
				//rollnumber = controller.getRollNumber(personID);
				classList = controller.ClassListbooksale(personID,bookSaleDataBean);
				logger.debug("classList --"+classList.size());
				HashSet<String> classes=new HashSet<String>(classList);
				classList.clear();classList.addAll(classes);
				Collections.sort(classList);
				bookSaleDataBean.setStudID(rollnumber);
				setFlag3(true);
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "bookPaymentView";
	}
	
	/*Neela Oct 25 paymentstudIDchange*/
	public void paymentstudIDchange(ValueChangeEvent event) 
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
			logger.warn(" exception - "+e);
		}
	}
	
	
	public void paymentbooklistvaluechange(ValueChangeEvent event) 
	{
		SmsController controller = null;
		try
		{
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			String s=event.getNewValue().toString();
			bookSaleDataBean.setStudID(s);
			booklist = controller.getBookViewInfo(personID, bookSaleDataBean);
			logger.debug("booklist"+booklist.size());
			setFlag3(false);
			String rolltype=controller.getRoll(personID);	
			if(rolltype.equalsIgnoreCase("Admin") || rolltype.equalsIgnoreCase("BookShop")){
				setAdminflag(true);
				setParentflag(false);
			}else if(rolltype.equalsIgnoreCase("Parent")){
				setParentflag(true);
				setAdminflag(false);
			}
		}
		catch(Exception e)
		{
			logger.warn(" exception - "+e);
		}
	}
	
	public String view()
	{
		SmsController controller = null;
		try
		{
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			bookinfolist = controller.getBookView(personID, bookSaleDataBean);
			
			setFlag3(false);
			setInvoiceflag(true);
		}
		catch(Exception e)
		{
			logger.warn(" exception - "+e);
		}
		return null;
	}
	

	public void downloadfile(BookSaleDataBean bookSaleDataBean){
		SmsController controller = null;
		String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
		try{
			InputStream input=null;
			if(personID!=null){
			bookinfolist=new ArrayList<BookSaleDataBean>();
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			bookinfolist = controller.getBookView(personID, bookSaleDataBean);
			String path=bookSaleDataBean.getFilePath();
			File file=new File(paths.getString("sms.bookpayment.photo").toString()+path);
			input = new FileInputStream(file);
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			this.setFile(new DefaultStreamedContent(input, externalContext.getMimeType(file.getName()), file.getName()));
			}
		}catch(FileNotFoundException e){
			logger.warn(" exception - "+e);
		}finally{
			
		}
	   
	}
	
	public String approve(){
		String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
		SmsController controller = null;
		try{
			if(personID!=null){
				String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolName");
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				String status=controller.bookpayemtapprove(personID,bookSaleDataBean);
				if(status.equalsIgnoreCase("Success")){
					//MailSendJDBC.bookPaymentapproval(bookSaleDataBean,schoolName);
					/*String emailstatus=sendapproveemail(bookSaleDataBean);
					if(emailstatus.equalsIgnoreCase("Success")){*/
					RequestContext.getCurrentInstance().execute("PF('approve').show();");/*
					
					}*/
				}
			}
		}catch(Exception e){
			logger.warn(" exception - "+e);
		}
		return null;
	}
	public String reject(){
		String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
		SmsController controller = null;
		try{
			if(personID!=null){
				String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolName");
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				String status=controller.bookpayemtreject(personID,bookSaleDataBean);
				if(status.equalsIgnoreCase("Success")){
					//MailSendJDBC.bookPaymentReject(bookSaleDataBean,schoolName);
					/*String emailstatus=sendrejectemail(bookSaleDataBean);
					if(emailstatus.equalsIgnoreCase("Success")){*/
					RequestContext.getCurrentInstance().execute("PF('reject').show();");/*
					}*/
				}
			}
		}catch(Exception e){
			logger.warn(" exception - "+e);
		}
		return null;	
	}
	
	/*private String sendrejectemail(BookSaleDataBean book) {
		String status = "Fail";
		String to=book.getEmailId();
		try{
			Properties prop = new Properties();
			prop.put("mail.smtp.host", text.getString("sms.mail.host"));
			prop.put("mail.smtp.socketFactory.port", text.getString("sms.mail.port"));
			prop.put("mail.smtp.socketFactory.class", text.getString("sms.mail.class"));
			prop.put("mail.smtp.starttls.enable", text.getString("sms.mail.smtp.startles"));
			prop.put("mail.smtp.auth", text.getString("sms.mail.auth"));
			prop.put("mail.smtp.port", text.getString("sms.mail.smtp.port"));
			prop.put("mail.smtp.ssl.trust", text.getString("sms.mail.smtp.trust"));
			String body1 = "<htm><head></heade><body>"
					+ " <header style='background-color:orange;color:white;height:50px;'>" + "<br></br>" + "<center>"
					+ "<h1> Welcome to Student Monitoring System</h1><br></br></center>" + "</center>" + "</header>"
					+ "<h3>Dear " +"Parent" + ",</h3>"
					+ "<p>Your Book Payment for Order Number"+" "+(book.getOrderNumber()) +" "+"has been Rejected"+"</p>"
					+ "<h4>&copy; 2016 PT. Neotural Era Graha</h4>" + "</center>" + "</footer>" + "</body></html>";
			Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("nrgsolutions.india@gmail.com", "Nrg@1234");
				}
			});
			try {
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress("nrgsolutions.india@gmail.com"));
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
				message.setSubject("SMS Confirmation");
				BodyPart messageBodyPart1 = new MimeBodyPart();
				messageBodyPart1.setContent(body1, "text/html");
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart1);
				message.setContent(multipart);
				Transport.send(message);
				logger.debug("message sent successfully");
				status = "Success";

			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}
		}catch(Exception e){
			logger.warn(" exception - "+e);
		}
		return status;
	}
*/
	/*private String sendapproveemail(BookSaleDataBean book){
		String status = "Fail";
		String to=book.getEmailId();
		try{
			Properties prop = new Properties();
			prop.put("mail.smtp.host", text.getString("sms.mail.host"));
			prop.put("mail.smtp.socketFactory.port", text.getString("sms.mail.port"));
			prop.put("mail.smtp.socketFactory.class", text.getString("sms.mail.class"));
			prop.put("mail.smtp.starttls.enable", text.getString("sms.mail.smtp.startles"));
			prop.put("mail.smtp.auth", text.getString("sms.mail.auth"));
			prop.put("mail.smtp.port", text.getString("sms.mail.smtp.port"));
			prop.put("mail.smtp.ssl.trust", text.getString("sms.mail.smtp.trust"));
			String body1 = "<htm><head></heade><body>"
					+ " <header style='background-color:orange;color:white;height:50px;'>" + "<br></br>" + "<center>"
					+ "<h1> Welcome to Student Monitoring System</h1><br></br></center>" + "</center>" + "</header>"
					+ "<h3>Dear " +"Parent" + ",</h3>"
					+ "<p>Your Book Payment for Order Number"+" "+(book.getOrderNumber()) +" "+"Approved Successfully"+"</p>"
					+ "<h4>&copy; 2016 PT. Neotural Era Graha</h4>" + "</center>" + "</footer>" + "</body></html>";
			Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("nrgsolutions.india@gmail.com", "Nrg@1234");
				}
			});
			try {
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress("nrgsolutions.india@gmail.com"));
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
				message.setSubject("SMS Confirmation");
				BodyPart messageBodyPart1 = new MimeBodyPart();
				messageBodyPart1.setContent(body1, "text/html");
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart1);
				message.setContent(multipart);
				Transport.send(message);
				logger.debug("message sent successfully");
				status = "Success";

			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}
		}catch(Exception e){
			logger.warn(" exception - "+e);
		}
		return status;
	}
*/
	public String personID=null;
	
	
	public String payNow(){
		  SmsController controller=null; 
		  String fieldName;
		  FacesContext fc = FacesContext.getCurrentInstance();
		  try{
			  String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolName");
			 // String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			  //int schoolid=Integer.parseInt(schoolID);
		   personID=FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID").toString();
		   ApplicationContext ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		   controller=(SmsController) ctx.getBean("controller");
		   if(personID!=null){
		    if(bookSaleDataBean.getBookFile()==null){
		     fieldName = CommonValidate.findComponentInRoot("file").getClientId(fc);
		     fc.addMessage(fieldName, new FacesMessage("Please upload the file"));
		    }else{
		     String s = bookSaleDataBean.getBookFile().getContentType();
		     String type = s.substring(s.lastIndexOf("/") + 1);
		     StringTokenizer stringTokenizer = new StringTokenizer(bookSaleDataBean.getOrderNumber());
		     String Filename = stringTokenizer.nextToken("/");
		     uploadFile(Filename, bookSaleDataBean.getBookFile().getInputstream(), type);
		     String path = ft.format(now) + "/" + Filename + "." + type;
		     bookSaleDataBean.setBookfilePath(path);
		     String statusup=controller.bookpaymentupload(personID,bookSaleDataBean);
		  //   MailSendJDBC.bookPayment(bookSaleDataBean,schoolName);
		     RequestContext context=RequestContext.getCurrentInstance();
		    /* String status=sendEmails(personID, bookSaleDataBean);
		     if(statusup.equalsIgnoreCase("success") && status.equalsIgnoreCase("Success")){
		    	 sendEmails(personID,bookSaleDataBean);*/
		     if(statusup.equalsIgnoreCase("success")){
		    	context.execute("PF('bookpaymentDialog').show();");
		     }
		     else{
		    	 context.execute("PF('bookpaymentDialogfail').show();");
		     }
		    
		    }     
		   }
		  }
		  catch(Exception e){
		   logger.warn("inside exception "+e);
		   logger.warn(" exception - "+e);
		  }
		  return "";
		 }
	/*private String sendEmails(String personID,BookSaleDataBean bookSaleDataBean) {
		  String status = "Fail";
		  try {
		   String to = bookSaleDataBean.getEmailId();
		   Properties prop = new Properties();
		   prop.put("mail.smtp.host", text.getString("sms.mail.host"));
		   prop.put("mail.smtp.socketFactory.port", text.getString("sms.mail.port"));
		   prop.put("mail.smtp.socketFactory.class", text.getString("sms.mail.class"));
		   prop.put("mail.smtp.starttls.enable", text.getString("sms.mail.smtp.startles"));
		   prop.put("mail.smtp.auth", text.getString("sms.mail.auth"));
		   prop.put("mail.smtp.port", text.getString("sms.mail.smtp.port"));
		   prop.put("mail.smtp.ssl.trust", text.getString("sms.mail.smtp.trust"));
		    String body1 = "<htm><head></heade><body>"
						+ " <header style='background-color:orange;color:white;height:50px;'>" + "<br></br>" + "<center>"
						+ "<h1> Welcome to Student Monitoring System</h1><br></br></center>" + "</center>" + "</header>"
						+ "<h3>Dear</h3>"
						+ "<h4> <p>Welcome on-board into NRG family.The Student  <span style='color: red'> "+bookSaleDataBean.getStudID()+"</span>'s   OrderNumber <span style='color: red'> (" + bookSaleDataBean.getOrderNumber()
						+ ")</span>  Payment Slip is Uploaded Successfully </p> </h4>"

						+ "<footer>" + "<center>" + "<h4>&copy; 2016 PT. Neotural Era Graha</h4>" + "</center>"
						+ "</footer>" + "</body></html>";
		    
		   Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
		    protected PasswordAuthentication getPasswordAuthentication() {
		     return new PasswordAuthentication("nrgsolutions.india@gmail.com", "Nrg@1234");
		    }
		   });
		   try {
		    MimeMessage message = new MimeMessage(session);
		    message.setFrom(new InternetAddress("nrgsolutions.india@gmail.com"));
		    message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		    message.setSubject("Approval Information");
		    BodyPart messageBodyPart1 = new MimeBodyPart();
		    messageBodyPart1.setContent(body1, "text/html");

		    Multipart multipart = new MimeMultipart();
		    multipart.addBodyPart(messageBodyPart1);
		    message.setContent(multipart);

		    Transport.send(message);

		    logger.debug("message sent successfully");
		    status = "Success";

		   } catch (MessagingException e) {
		    throw new RuntimeException(e);
		   }

		  } catch (Exception e) {
		   logger.warn(" exception - "+e);
		   logger.warn("Exception -->"+e.getMessage());
		  }
		  return status;
		 }*/

	public void fileupview() {
		
		setFlag3(true);
		setUploadflag(true);
				}
	public void fileupsuccess(){
		SmsController controller = null;
		try {
			
		
		ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		controller = (SmsController) ctx.getBean("controller");
		booklist = controller.getBookViewInfo(personID, bookSaleDataBean);
		setFlag3(false);
 		setUploadflag(false);
		} catch (Exception e) {
			logger.warn(" exception - "+e);
		}
	}
		 private void uploadFile(String fileName, InputStream inputstream, String n) {
		  try {
		   File files = new File(paths.getString("sms.bookpayment.photo") + ft.format(now));
		   if (!files.exists()) {
		    files.mkdirs();
		   } else {
			   logger.warn("Alreday Found");
		   }
		   // write the inputStream to a FileOutputStream
		   FileOutputStream out = new FileOutputStream(new File(files + paths.getString("path_context").toString() + fileName + "." + n));
		   int read = 0;
		   byte[] bytes = new byte[1024];
		   while ((read = inputstream.read(bytes)) != -1) {
		    out.write(bytes, 0, read);
		   }
		   inputstream.close();
		   out.flush();
		   out.close();
		   logger.info("New file created!");
		  } catch (IOException e) {
			  logger.warn("Exception -->"+e.getMessage());
		  }
		 }
		
		 
		 public String dummyAction(FileUploadEvent event) throws IOException {
				this.setFiles(event.getFile());
				bookSaleDataBean.setBookFile(event.getFile());
				FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
				FacesContext.getCurrentInstance().addMessage(null, message);
				logger.debug(bookSaleDataBean.getBookFile().getContentType());

				return "";
			}

		public boolean isDelBoxflag() {
			return delBoxflag;
		}

		public void setDelBoxflag(boolean delBoxflag) {
			this.delBoxflag = delBoxflag;
		}

		public boolean isAdminflag() {
			return adminflag;
		}

		public void setAdminflag(boolean adminflag) {
			this.adminflag = adminflag;
		}

		public boolean isParentflag() {
			return parentflag;
		}

		public void setParentflag(boolean parentflag) {
			this.parentflag = parentflag;
		}

		public StreamedContent getFile() {
			return file;
		}

		public void setFile(StreamedContent file) {
			this.file = file;
		}

		public boolean isUploadflag() {
			return uploadflag;
		}

		public void setUploadflag(boolean uploadflag) {
			this.uploadflag = uploadflag;
		}

		public UploadedFile getFiles() {
			return files;
		}

		public void setFiles(UploadedFile files) {
			this.files = files;
		}

		public boolean isInvoiceflag() {
			return invoiceflag;
		}

		public void setInvoiceflag(boolean invoiceflag) {
			this.invoiceflag = invoiceflag;
		}

		public boolean isBoxflag() {
			return boxflag;
		}

		public void setBoxflag(boolean boxflag) {
			this.boxflag = boxflag;
		}
		
		public void changdata(){
			SmsController controller=null;
			try {			
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			booklist = controller.getBookViewInfo(personID, bookSaleDataBean);
			} catch (Exception e) {
			logger.warn(" exception - "+e);
			}
		}
		
		//Ragulan 17-10-2016
		public void optionchange(ValueChangeEvent v) {
			try{
				String option=v.getNewValue().toString();
				if(option.equalsIgnoreCase("All")){
					setClassflag(false);
					setStudentflag(false);
				}else if(option.equalsIgnoreCase("Class")){
					setClassflag(true);
					setStudentflag(false);
				}else if(option.equalsIgnoreCase("Student")){
					setClassflag(false);
					setStudentflag(true);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		
	public String feessave(){
		SmsController controller=null;
		//String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolName");
		String status="";
		try{
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if(personID!=null){

			if(validation(true)){
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");	
			status=controller.feessave(personID, paymentDatabean);
			if(status.equalsIgnoreCase("Success")){
				RequestContext.getCurrentInstance().execute("PF('confirm').show()");
				paymentDatabean.setTitle("");
				paymentDatabean.setAmount("");
				paymentDatabean.setDescription("");
				paymentDatabean.setDuedate(null);
				paymentDatabean.setOptions("");
				paymentDatabean.setPayclass("");
				setClassflag(false);
				setStudentflag(false);
			}
			}
		}
		}catch(Exception e){
			logger.error("Exception -->"+e.getMessage());
			//e.printStackTrace();
		}
		
		return"";
	}

	private boolean validation(boolean valid) {
		valid=true;
		String name="";
		FacesContext fc=FacesContext.getCurrentInstance();
		if(StringUtils.isEmpty(paymentDatabean.getTitle())){
			name=CommonValidate.findComponentInRoot("title").getClientId(fc);
			fc.addMessage(name, new FacesMessage("Please Enter the title"));
			valid=false;
		}
		if(StringUtils.isEmpty(paymentDatabean.getAmount())){
			name=CommonValidate.findComponentInRoot("amount").getClientId(fc);
			fc.addMessage(name, new FacesMessage("Please Enter the Amount"));
			valid=false;
		}else if(!StringUtils.isEmpty(paymentDatabean.getAmount())){
			if(!CommonValidate.validateNumber(paymentDatabean.getAmount())){
				name=CommonValidate.findComponentInRoot("amount").getClientId(fc);
				fc.addMessage(name, new FacesMessage("Please Enter the Valid Amount"));
				valid=false;
			}
		}
		if(paymentDatabean.getDuedate()==null){
			name=CommonValidate.findComponentInRoot("duedate").getClientId(fc);
			fc.addMessage(name, new FacesMessage("Please Select the Due date"));
			valid=false;
		}
		if(StringUtils.isEmpty(paymentDatabean.getDescription())){
			name=CommonValidate.findComponentInRoot("desc").getClientId(fc);
			fc.addMessage(name, new FacesMessage("Please Enter the Description"));
			valid=false;
		}
		if(StringUtils.isEmpty(paymentDatabean.getOptions())){
			name=CommonValidate.findComponentInRoot("option").getClientId(fc);
			fc.addMessage(name, new FacesMessage("Please Choose the Options"));
			valid=false;
		}else if(!StringUtils.isEmpty(paymentDatabean.getOptions())){
			if(paymentDatabean.getOptions().equalsIgnoreCase("Class")){
				if(StringUtils.isEmpty(paymentDatabean.getPayclass())){
					name=CommonValidate.findComponentInRoot("clas").getClientId(fc);
					fc.addMessage(name, new FacesMessage("Please Choose the Class"));
					valid=false;
				}
			}else if(paymentDatabean.getOptions().equalsIgnoreCase("Student")){
				if(StringUtils.isEmpty(paymentDatabean.getPayClassSection())){
					name=CommonValidate.findComponentInRoot("clasec").getClientId(fc);
					fc.addMessage(name, new FacesMessage("Please Choose the Class & section"));
					valid=false;
				}
				if(StringUtils.isEmpty(paymentDatabean.getPaymentStudID())){
					name=CommonValidate.findComponentInRoot("StudentID").getClientId(fc);
					fc.addMessage(name, new FacesMessage("Please Choose the StudentID"));
					valid=false;
				}
			}
		}
		return valid;
	}
	private List<String> years;
	
	public List<String> getYears() {
		return years;
	}

	public void setYears(List<String> years) {
		this.years = years;
	}

	public String viewFeesRegistrationdlg(){
		int year=0;
		SmsController controller=null;
		try{
			sRoll=new ArrayList<String>();
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller=(SmsController) ctx.getBean("controller");
			if(paths.getString("SCMS.SCHOOLID").equalsIgnoreCase(schoolID)){
				paymentDatabean.setTransportFees("0");
				paymentDatabean.setTuitionFees("0");
				paymentDatabean.setSpecialFees("0");
				paymentDatabean.setEcaFees("0");
				paymentDatabean.setLabFees("0");
				paymentDatabean.setAdmissionFees("0");
				classlist = controller.getClassList(personID);
				Collections.sort(classlist);
				controller.getPerClassList1(personID,paymentDatabean);
				System.out.println("---------dom size-----" + paymentDatabean.getStuClassList().size());
				for (int i = 0; i < paymentDatabean.getStuClassList().size(); i++) {
					sRoll.add(paymentDatabean.getStuClassList().get(i).getStuCls1());
				}
			}else{
				paymentDatabean.setTuitionFees("");
				paymentDatabean.setExamFees("");
				paymentDatabean.setTransportFees("");
				paymentDatabean.setExtraFee1("");
				paymentDatabean.setExtraFee2("");
				year=Integer.valueOf(Calendar.getInstance().get(Calendar.YEAR));
				years=new ArrayList<String>();
				years.add(0,(String.valueOf((year-1))));
				years.add(1,(String.valueOf((year))));
				years.add(2,(String.valueOf((year+1))));
				paymentDatabean.setYear(String.valueOf(year));
			}
			paymentDatabean.setMonth(String.valueOf(Calendar.getInstance().get(Calendar.MONTH)+1));
			RequestContext reqcontext = RequestContext.getCurrentInstance();
			reqcontext.execute("PF('feesRegdlg').show();");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	public String globalfeesregpage() {
		SmsController controller = null;
		String status = "Fail";
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		String totalFees="";
		  try {
			  System.out.println("------feesregpage method calling--------");
			  if (validate(true)) {
				  System.out.println("------validate true  --------");
				  String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolName");
					String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
					int schoolid=Integer.parseInt(schoolID);
					String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
							.get("LogID");
					ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
					controller = (SmsController) ctx.getBean("controller");
					if(personID != null){
						if(saveValidation(true)){
						if(paths.getString("SCMS.SCHOOLID").equalsIgnoreCase(schoolID)){
							paymentDatabean.setYear(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
							if("6".equalsIgnoreCase(paymentDatabean.getMonth())){
								BigDecimal temp =new BigDecimal(paymentDatabean.getTransportFees()).add(new BigDecimal(paymentDatabean.getTuitionFees()));
								BigDecimal temp1=new BigDecimal(paymentDatabean.getSpecialFees()).add(new BigDecimal(paymentDatabean.getEcaFees()));
								BigDecimal temp2=new BigDecimal(paymentDatabean.getLabFees()).add(new BigDecimal(paymentDatabean.getAdmissionFees()));
								totalFees=temp.add(temp1).add(temp2).toString();
								paymentDatabean.setTotalFees(totalFees);
							}else{
								totalFees =new BigDecimal(paymentDatabean.getTransportFees()).add(new BigDecimal(paymentDatabean.getTuitionFees())).toString();
								paymentDatabean.setTotalFees(totalFees);
							}
						}
						status = controller.insertfees(personID, paymentDatabean);
						System.out.println("------->>>>>><<<<<<>>>>>" + status);
						if(status.equalsIgnoreCase("Success")){
							try{
								if(!paymentDatabean.getMailId().equals("") || paymentDatabean.getMailId()!=null){
									MailSendJDBC.feesRegister(paymentDatabean,schoolName,schoolid);
								}
							}catch(Exception e){
								e.printStackTrace();
							}
							
							RequestContext reqcontext = RequestContext.getCurrentInstance();
							reqcontext.execute("PF('feesRegconfirmdlg').show();");
						}
						else if (status.equalsIgnoreCase("Exist")) {
							if(schoolid==Integer.parseInt(paths.getString("MAHARISHI.SCHOOLID")) || paths.getString("SCMS.SCHOOLID").equalsIgnoreCase(schoolID)){
							fieldName = CommonValidate.findComponentInRoot("validation").getClientId(fc);
							fc.addMessage(fieldName, new FacesMessage("This Month Fees Already Registered"));
							System.out.println("----inside exist");
							}
							else{
								fieldName = CommonValidate.findComponentInRoot("validation").getClientId(fc);
								fc.addMessage(fieldName, new FacesMessage("Fees Already Registered"));
							}
						}
					}
			  }
			  }
		  }catch (Exception e) {
		   logger.warn("Exception -->"+e.getMessage());
		   e.printStackTrace();
		   return "failure";
		  }
		  return "";
		 }
	
}
