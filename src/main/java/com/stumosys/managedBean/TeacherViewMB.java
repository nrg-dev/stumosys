package com.stumosys.managedBean;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
//import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
//import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.imageio.ImageIO;
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
import javax.mail.internet.MimeMultipart;*/
//import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
//import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.stumosys.controler.SmsController;
import com.stumosys.domain.BookSaleDataBean;
import com.stumosys.domain.TeacherDataBean;
import com.stumosys.util.CommonValidate;
import com.stumosys.util.MailSendJDBC;

@ManagedBean(name = "teacherViewMB")
@RequestScoped
public class TeacherViewMB {
	Date now = new Date();
	TeacherDataBean teacherDataBean = new TeacherDataBean();
	BookSaleDataBean bookSaleDataBean=new BookSaleDataBean();
	UploadedFile file;
	private List<TeacherDataBean> teacherList = null;
	private List<TeacherDataBean> filteredTeacher;
	private String imagefile;
	private List<TeacherDataBean> teacherViewList = null;
	SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
	private DualListModel<String> subjectList;
	private DualListModel<String> classList;
	private List<String> stateList = null;
	private boolean boxflag = false;
	private boolean delBoxflag = false;
	private List<TeacherDataBean> teacherTableList = null;
	private List<TeacherDataBean> ImageListPath = null;
	private List<String> parclassList = null;
	private boolean flag11 = false;
	private boolean tableflag = false;
	private boolean classflag = false;
	private boolean chkBox;
	private boolean permenantAddFlag=true;
	private boolean permenantAddFlag1=false; 
	public List<String> listTeacher=new ArrayList<String>();
	public List<String> listTeacher1=new ArrayList<String>();
	List<String> classSource = new ArrayList<String>();
	List<String> classTarget = new ArrayList<String>();
	List<String> subjectSource = new ArrayList<String>();
	List<String> subjectTarget = new ArrayList<String>();
	private boolean newflag=false;
	private boolean oldflag=false;
	
	public boolean isNewflag() {
		return newflag;
	}

	public void setNewflag(boolean newflag) {
		this.newflag = newflag;
	}

	public boolean isOldflag() {
		return oldflag;
	}

	public void setOldflag(boolean oldflag) {
		this.oldflag = oldflag;
	}

	public List<String> getClassSource() {
		return classSource;
	}

	public void setClassSource(List<String> classSource) {
		this.classSource = classSource;
	}

	public List<String> getClassTarget() {
		return classTarget;
	}

	public void setClassTarget(List<String> classTarget) {
		this.classTarget = classTarget;
	}

	public List<String> getSubjectSource() {
		return subjectSource;
	}

	public void setSubjectSource(List<String> subjectSource) {
		this.subjectSource = subjectSource;
	}

	public List<String> getSubjectTarget() {
		return subjectTarget;
	}

	public void setSubjectTarget(List<String> subjectTarget) {
		this.subjectTarget = subjectTarget;
	}

	public List<String> getListTeacher() {
		return listTeacher;
	}

	public void setListTeacher(List<String> listTeacher) {
		this.listTeacher = listTeacher;
	}

	public List<String> getListTeacher1() {
		return listTeacher1;
	}

	public void setListTeacher1(List<String> listTeacher1) {
		this.listTeacher1 = listTeacher1;
	}

	public boolean isChkBox() {
		return chkBox;
	}

	public void setChkBox(boolean chkBox) {
		this.chkBox = chkBox;
	}

	public boolean isPermenantAddFlag() {
		return permenantAddFlag;
	}

	public void setPermenantAddFlag(boolean permenantAddFlag) {
		this.permenantAddFlag = permenantAddFlag;
	}

	public boolean isPermenantAddFlag1() {
		return permenantAddFlag1;
	}

	public void setPermenantAddFlag1(boolean permenantAddFlag1) {
		this.permenantAddFlag1 = permenantAddFlag1;
	}

	public boolean isClassflag() {
		return classflag;
	}

	public void setClassflag(boolean classflag) {
		this.classflag = classflag;
	}
	
	public boolean isTableflag() {
		return tableflag;
	}

	public void setTableflag(boolean tableflag) {
		this.tableflag = tableflag;
	}

	public boolean isFlag11() {
		return flag11;
	}

	public void setFlag11(boolean flag11) {
		this.flag11 = flag11;
	}

	public List<String> getParclassList() {
		return parclassList;
	}

	public void setParclassList(List<String> parclassList) {
		this.parclassList = parclassList;
	}

	public List<TeacherDataBean> getImageListPath() {
		return ImageListPath;
	}

	public void setImageListPath(List<TeacherDataBean> imageListPath) {
		ImageListPath = imageListPath;
	}

	private String studClass;
	FacesContext context = FacesContext.getCurrentInstance();
	ResourceBundle text = context.getApplication().evaluateExpressionGet(context, "#{text}", ResourceBundle.class);
	ResourceBundle paths = context.getApplication().evaluateExpressionGet(context, "#{paths}", ResourceBundle.class);
	private static Logger logger = Logger.getLogger(TeacherViewMB.class);

	public String getStudClass() {
		return studClass;
	}

	public void setStudClass(String studClass) {
		this.studClass = studClass;
	}

	boolean flag1 = false;

	public boolean isFlag1() {
		return flag1;
	}

	public void setFlag1(boolean flag1) {
		this.flag1 = flag1;
	}

	public List<TeacherDataBean> getTeacherTableList() {
		return teacherTableList;
	}

	public void setTeacherTableList(List<TeacherDataBean> teacherTableList) {
		this.teacherTableList = teacherTableList;
	}

	/**
	 * @return the now
	 */
	public Date getNow() {
		return now;
	}

	/**
	 * @param now
	 *            the now to set
	 */
	public void setNow(Date now) {
		this.now = now;
	}

	/**
	 * @return the stateList
	 */
	public List<String> getStateList() {
		return stateList;
	}

	/**
	 * @param stateList
	 *            the stateList to set
	 */
	public void setStateList(List<String> stateList) {
		this.stateList = stateList;
	}

	/**
	 * @return the subjectList
	 */
	public DualListModel<String> getSubjectList() {
		return subjectList;
	}

	/**
	 * @param subjectList
	 *            the subjectList to set
	 */
	public void setSubjectList(DualListModel<String> subjectList) {
		this.subjectList = subjectList;
	}

	/**
	 * @return the classList
	 */
	public DualListModel<String> getClassList() {
		return classList;
	}

	/**
	 * @param classList
	 *            the classList to set
	 */
	public void setClassList(DualListModel<String> classList) {
		this.classList = classList;
	}

	/**
	 * @return the file
	 */
	public UploadedFile getFile() {
		return file;
	}

	/**
	 * @param file
	 *            the file to set
	 */
	public void setFile(UploadedFile file) {
		this.file = file;
	}

	/**
	 * @return the teacherViewList
	 */
	public List<TeacherDataBean> getTeacherViewList() {
		return teacherViewList;
	}

	/**
	 * @param teacherViewList
	 *            the teacherViewList to set
	 */
	public void setTeacherViewList(List<TeacherDataBean> teacherViewList) {
		this.teacherViewList = teacherViewList;
	}

	/**
	 * @return the teacherDataBean
	 */
	public TeacherDataBean getTeacherDataBean() {
		return teacherDataBean;
	}

	/**
	 * @param teacherDataBean
	 *            the teacherDataBean to set
	 */
	public void setTeacherDataBean(TeacherDataBean teacherDataBean) {
		teacherDataBean = teacherDataBean;
	}

	/**
	 * @return the teacherList
	 */
	public List<TeacherDataBean> getTeacherList() {
		return teacherList;
	}

	/**
	 * @param teacherList
	 *            the teacherList to set
	 */
	public void setTeacherList(List<TeacherDataBean> teacherList) {
		this.teacherList = teacherList;
	}

	/**
	 * @return the filteredTeacher
	 */
	public List<TeacherDataBean> getFilteredTeacher() {
		return filteredTeacher;
	}

	/**
	 * @param filteredTeacher
	 *            the filteredTeacher to set
	 */
	public void setFilteredTeacher(List<TeacherDataBean> filteredTeacher) {
		this.filteredTeacher = filteredTeacher;
	}
	/*Neela Oct 25 teacherInfo*/
	public String teacherInfo() {
		logger.debug("----------inside teacher info-----");
		selectedteachers=new ArrayList<TeacherDataBean>();
		setBoxflag(false);
		setDelBoxflag(false);
		setFlag1(false);
		setTableflag(false);
		setFlag11(false);setClassflag(true);
		SmsController controller = null;
		String rollnumber = "";
		String status = "";teacherDataBean.setTeaclsSection("");
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			logger.debug("-------person ID-------" + personID);
			status = controller.getRoll(personID);
			if (personID != null) {
				if (status.equalsIgnoreCase("Admin")) {
					teacherList = new ArrayList<TeacherDataBean>();
					teacherList = controller.getAllTeacherInfo(personID,teacherDataBean);
					setFlag1(false);
					setTableflag(true);setClassflag(false);
				} else if (status.equalsIgnoreCase("Student")) {
					rollnumber = controller.getRollNumber(personID);
					logger.debug("------student roll number-----" + rollnumber);
					studClass = controller.getStudClsSection(rollnumber);
					teacherDataBean.setTeaclsSection(studClass);
					teacherList = new ArrayList<TeacherDataBean>();
					teacherList = controller.getAllTeacherInfo(personID,teacherDataBean);
					setFlag1(true);setClassflag(false);
					setTableflag(true);
				} else if (status.equalsIgnoreCase("Parent")) {
					rollnumber = controller.getRollNumber(personID);
					logger.debug("------student roll number-----" + rollnumber);
					parclassList = controller.ClassListbooksale(personID,bookSaleDataBean);
					setFlag1(true);setClassflag(false);
					setFlag11(true);
				}
		
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "TeacherviewPageLoad";

	}
	
	/*Neela Oct 25 classChange*/
	public void classChange(ValueChangeEvent v)
	{
		logger.debug("iiiiiiiiiiinnnnnnnnnnn");
		SmsController controller = null;
		try
		{
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			try{
				String clas=v.getNewValue().toString();
				teacherDataBean.setTeaclsSection(clas);
				teacherList = new ArrayList<TeacherDataBean>();
				teacherList = controller.getAllTeacherInfo(personID,teacherDataBean);
			}
			catch(Exception e){
				teacherList = new ArrayList<TeacherDataBean>();
			}			
			setTableflag(true);
		}
		catch(Exception e)
		{
			logger.warn(" exception - "+e);
		}
	}

	public String returnToHome() {
		SmsController controller = null;
		logger.debug("Calling");
		teacherList = null;
		try {
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (personID != null) {
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				teacherList = new ArrayList<TeacherDataBean>();
				teacherList = controller.getAllTeacherInfo(personID,teacherDataBean);
				setBoxflag(false);
				setDelBoxflag(false);
				return "TeacherviewPageLoad";
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "";

	}

	
	private StreamedContent listImage = null;

	/**
	 * @return the listImage
	 */
	public StreamedContent getListImage() {
		if (listImage == null) {
			try {
				listImage = new DefaultStreamedContent(new FileInputStream("E:/t.jpg"), "image/png"); // load
																										// a
																										// dummy
																										// image
			} catch (FileNotFoundException e) {
				logger.warn("Exception -->"+e.getMessage());
			}
		}
		return listImage;
	}

	/**
	 * @param listImage
	 *            the listImage to set
	 */
	public void setListImage(StreamedContent listImage) {
		this.listImage = listImage;
	}

	/**
	 * @return the imagefile
	 */
	public String getImagefile() {
		return imagefile;
	}

	/**
	 * @param imagefile
	 *            the imagefile to set
	 */
	public void setImagefile(String imagefile) {
		this.imagefile = imagefile;
	}

	public void imageview(OutputStream out, Object data) {
		String path="";
		try{
			path= paths.getString("sms.teacher.teacherphoto");
			BufferedImage img = ImageIO
					.read(new File(path + ft.format(teacherDataBean.getPathDate()) + "/" + teacherDataBean.getPath()));
			ImageIO.write(img, "png", out);
		}catch(Exception e){
			setOldflag(false);
			setNewflag(true);
			RequestContext.getCurrentInstance().update(":XX");
		}
	}

	public String view() {

		try {
			logger.debug("-----Inside call Teacher view method ------");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");

			if (personID != null) {
				if (teacherDataBean.getTeaID() != null) {
					String status = getTeacherView(personID, teacherDataBean.getTeaID());
					logger.debug(status);
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "";
	}
	
	public String view1() {

		try {
			logger.debug("-----Inside call Teacher view method ------");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");

			if (personID != null) {
				if (teacherDataBean.getTeaID() != null) {
					String status = getTeacherView(personID, teacherDataBean.getTeaID());
					logger.debug(status);
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "viewPageLoad";
	}

	private Boolean flagback;
	
	public Boolean getFlagback() {
		return flagback;
	}

	public void setFlagback(Boolean flagback) {
		this.flagback = flagback;
	}

	public String edit() {
		flagback=true;
		SmsController controller = null;
		logger.info("teacher ID-------->"+teacherDataBean.getTeaID());
		List<String> subjectnameList=null;
		try {
			logger.debug("-----Inside call Teacher edit method ------");
			subjectList=new DualListModel<String>();
			classList=new DualListModel<String>();
			classSource=new ArrayList<String>();
			classTarget=new ArrayList<String>();
			subjectSource=new ArrayList<String>();
			subjectTarget=new ArrayList<String>();
			subjectnameList=new ArrayList<String>();
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			if (personID != null) {
				if (teacherDataBean.getTeaID() != null) {
					String status = getTeacherView(personID, teacherDataBean.getTeaID());
					logger.debug("status" + status);
					logger.debug("state --- "+teacherDataBean.getTeaState());
					if (status.equalsIgnoreCase("Success")) {
						gettimelist();
						stateList = new ArrayList<String>();
						stateList = controller.getStateList(personID);
						logger.debug("stateList === "+stateList.size());
						if (classSource.size() > 0) {
							classSource.clear();
						}
						if (subjectSource.size() > 0) {
							subjectSource.clear();
						}
						for (int i = 0; i < teacherDataBean.getTeaClassTargetList().size(); i++) {
							subjectnameList=controller.getSubjectListTeacher(personID,teacherDataBean.getTeaClassTargetList().get(i));
							subjectSource.addAll(subjectnameList);
						}
						Set<String> dublicate=new HashSet<String>(subjectSource);
						subjectSource.clear();
						subjectSource.addAll(dublicate);
						//subjectSource = controller.getSubjectList(personID);
						subjectTarget.addAll(teacherDataBean.getTeaSubjectTargetList());
						logger.info("--------subjectSource--------------------"+subjectSource.size());
						logger.info("--------TargetList--------------------"+teacherDataBean.getTeaSubjectTargetList().size());
						if (subjectSource.size() > 0) {
							
							/*for (int i = 0; i < subjectSource.size(); i++) {
								for (int j = 0; j < subjectTarget.size(); j++) {
									String temp1 = subjectSource.get(i);
									String temp2 = subjectTarget.get(j);
									if (temp2.equalsIgnoreCase(temp1)) {
										subjectSource.remove(temp1);
									}
								}
							}*/
						}
						subjectList = new DualListModel<String>(subjectSource, subjectTarget);
						classSource = controller.getClassSection(personID);
						logger.info("--------classSource--------------------"+classSource.size());
						logger.info("--------TargetList--------------------"+teacherDataBean.getTeaClassTargetList().size());
						if (classSource.size() > 0) {
							classTarget.addAll(teacherDataBean.getTeaClassTargetList());
							/*for (int m = 0; m < classSource.size(); m++) {
								for (int k = 0; k < classTarget.size(); k++) {
									String temp3 = classSource.get(m);
									String temp4 = classTarget.get(k);
									if (temp4.equalsIgnoreCase(temp3)) {
										classSource.remove(temp3);
									}
								}
							}*/
						}
						classList = new DualListModel<String>(classSource, classTarget);
						setBoxflag(false);
						logger.debug("inside succeessss -5---->"+teacherDataBean.getTeaCountry());
						try {
							listTeacher=controller.getcountryList(teacherDataBean.getTeaCountry()); 
							logger.debug("================>"+listTeacher.size());  
							if(teacherDataBean.getTeaCountry().equalsIgnoreCase("INDIA")) teacherDataBean.setCode("+91"); 
						     else if(teacherDataBean.getTeaCountry().equalsIgnoreCase("MALAYSIA")) teacherDataBean.setCode("+60"); 
						     else if(teacherDataBean.getTeaCountry().equalsIgnoreCase("UAE")) teacherDataBean.setCode("+971"); 
						     else if(teacherDataBean.getTeaCountry().equalsIgnoreCase("INDONESIA")) teacherDataBean.setCode("+62"); 
							
							listTeacher1=controller.getcountryList(teacherDataBean.getTeaCountry1()); 
							logger.debug("================>"+listTeacher.size());  
							if(teacherDataBean.getTeaCountry1().equalsIgnoreCase("INDIA")) teacherDataBean.setCode1("+91"); 
						     else if(teacherDataBean.getTeaCountry1().equalsIgnoreCase("MALAYSIA")) teacherDataBean.setCode1("+60"); 
						     else if(teacherDataBean.getTeaCountry1().equalsIgnoreCase("UAE")) teacherDataBean.setCode1("+971");
						     else if(teacherDataBean.getTeaCountry1().equalsIgnoreCase("INDONESIA")) teacherDataBean.setCode1("+62");
						} catch (NullPointerException e) {
							
						}
						teacherDataBean.setNames("");
						if(schoolID.equalsIgnoreCase(paths.getString("MALAYSIA.SCHOOLID"))){
							teacherDataBean.setNames("Teacher Photo");
						}else{
							teacherDataBean.setNames("Education Details");
						}
						FacesContext.getCurrentInstance().getExternalContext().redirect(paths.getString("stumosys.teacherglobalpage"));
						return "";
					} else {
						logger.debug("Fail");
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			//logger.warn("Exception -->"+e.getMessage());
		}
		return "";

	}
	
	public void classChange(){
		String personID ="";
		ApplicationContext ctx=null;
		SmsController controller=null;
		List<String> classList=null;
		try{
			classList=new ArrayList<String>();
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			if(classTarget.size()==0){
				subjectSource=new ArrayList<String>();
			}else{
				for(String s : classTarget) {
					String[] value = s.split(",");
					for(String classname : value) {
						classList=controller.getSubjectListTeacher(personID,classname);
					}
					subjectSource.addAll(classList);
				}
				Set<String> dublicate=new HashSet<String>(subjectSource);
				subjectSource.clear();subjectSource.addAll(dublicate);
				subjectList = new DualListModel<String>(subjectSource, subjectTarget);
			}
		}catch(Exception e){
			logger.warn("Exception -->"+e.getMessage());
			e.printStackTrace();
		}
	}
	private List<TeacherDataBean> selectedteachers=null;
	
	
	
		public List<TeacherDataBean> getSelectedteachers() {
		return selectedteachers;
	}

	public void setSelectedteachers(List<TeacherDataBean> selectedteachers) {
		this.selectedteachers = selectedteachers;
	}

		public String multiDelete() {
			SmsController controller = null;
			String status="Fail";
			try {
				if (selectedteachers.size()>0) {
				for (int i = 0; i < selectedteachers.size(); i++) {
					String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
					ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
					controller = (SmsController) ctx.getBean("controller");
					teacherDataBean.setTeaID(getSelectedteachers().get(i).getTeaID());
					if (personID != null && teacherDataBean.getTeaID() != null) {
						status = controller.deleteTeacher(personID, teacherDataBean);
					}
				}
				if (status.equalsIgnoreCase("Success")) {
					RequestContext.getCurrentInstance().update("XX");
					setDelBoxflag(true);
				}
			}
			} catch (Exception e) {
				logger.warn("Exception -->"+e.getMessage());
				logger.warn("Exception -->"+e.getMessage());
			}
			finally{
				status="";
			}
			return "";
		}
	
	private String getTeacherView(String personID, String teaID) {
		setNewflag(false);
		setOldflag(false);
		SmsController controller = null;
		teacherViewList = null;
		String status = "Fail";
		try {
			if (teacherDataBean.getTeaSubjectTargetList() == null) {
				logger.info("teacherDataBean.getTeaSubjectTargetList() null");
			} else {
				if (teacherDataBean.getTeaSubjectTargetList().size() > 0) {
					teacherDataBean.getTeaSubjectTargetList().clear();
				}
			}
			if (teacherDataBean.getTeaClassTargetList() == null) {
				logger.info("teacherDataBean.getTeaClassTargetList() null");

			} else {
				if (teacherDataBean.getTeaClassTargetList().size() > 0) {
					teacherDataBean.getTeaClassTargetList().clear();
				}
			}

			if (personID != null && teaID != null) {

				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				teacherViewList = new ArrayList<TeacherDataBean>();
				teacherViewList = controller.getTeacherInfo(personID, teacherDataBean.getTeaID());
				if (teacherViewList.size() > 0) {
					try {
						if (teacherViewList.get(0).getTeacherDet().size() > 0) {
							teacherDataBean
									.setTeaFirstName(teacherViewList.get(0).getTeacherDet().get(0).getFirstName());
							teacherDataBean.setTeaLastName(teacherViewList.get(0).getTeacherDet().get(0).getLastName());
							teacherDataBean.setNames(
									teacherDataBean.getTeaFirstName() + " " + teacherDataBean.getTeaLastName());
							teacherDataBean
									.setTeaFatherName(teacherViewList.get(0).getTeacherDet().get(0).getFatherName());
							teacherDataBean
									.setTeaMotherName(teacherViewList.get(0).getTeacherDet().get(0).getMotherName());
							teacherDataBean.setTeaDob(teacherViewList.get(0).getTeacherDet().get(0).getDob());
							teacherDataBean.setTeaGender(teacherViewList.get(0).getTeacherDet().get(0).getGender());
							teacherDataBean.setTeaEduQualification(
									teacherViewList.get(0).getTeacherDet().get(0).getQualification());
							teacherDataBean.setTeaEmail(teacherViewList.get(0).getTeacherDet().get(0).getEmailId());
							teacherDataBean.setTeaID(
									teacherViewList.get(0).getTeacherDet().get(0).getTeacher().getRollNumber());
							teacherDataBean
									.setTeaPercentage(teacherViewList.get(0).getTeacherDet().get(0).getPercentage());
							teacherDataBean
									.setTeaPhoneNo(teacherViewList.get(0).getTeacherDet().get(0).getPhoneNumber());
							teacherDataBean.setTeaPosition(teacherViewList.get(0).getTeacherDet().get(0).getPost());
							teacherDataBean.setTeaPermanentAddress(
									teacherViewList.get(0).getTeacherDet().get(0).getAddress2_Street());
							teacherDataBean.setTeaPresentAddress(
									teacherViewList.get(0).getTeacherDet().get(0).getAddress1_Street());
							teacherDataBean
									.setTeaWorkingHour(teacherViewList.get(0).getTeacherDet().get(0).getWorkingHour());
							teacherDataBean.setTeaYearOfPassing(
									teacherViewList.get(0).getTeacherDet().get(0).getYearOfPassing());
							/*teacherDataBean.setTeaPermanentZip(
									teacherViewList.get(0).getTeacherDet().get(0).getPostcode2().getPostcode());
							teacherDataBean.setTeaPresentZip(
									teacherViewList.get(0).getTeacherDet().get(0).getPostcode1().getPostcode());*/
							teacherDataBean.setTeaAddress1(teacherViewList.get(0).getTeaAddress1());
							teacherDataBean.setTeaAddress2(teacherViewList.get(0).getTeaPermanentAddress());
							teacherDataBean.setTeaCity(teacherViewList.get(0).getTeacherDet().get(0).getPresentCity());
							teacherDataBean.setTeaCity1(teacherViewList.get(0).getTeacherDet().get(0).getPermanentCity());
							teacherDataBean.setTeaState(teacherViewList.get(0).getTeacherDet().get(0).getPresentState());
							teacherDataBean.setTeaState1(teacherViewList.get(0).getTeacherDet().get(0).getPermanentState());
							teacherDataBean.setTeaCountry(teacherViewList.get(0).getTeacherDet().get(0).getPresentCountry());
							teacherDataBean.setTeaCountry1(teacherViewList.get(0).getTeacherDet().get(0).getPermanentCountry());
							teacherDataBean.setTeaPostal(teacherViewList.get(0).getTeacherDet().get(0).getPresentPostal());
							teacherDataBean.setTeaPostal1(teacherViewList.get(0).getTeacherDet().get(0).getPermanentPostal());
							teacherDataBean.setTeaPhoneNo1(teacherViewList.get(0).getTeacherDet().get(0).getPhoneNumber1());
							teacherDataBean.setCode(teacherViewList.get(0).getTeacherDet().get(0).getPresentCode());
							teacherDataBean.setCode1(teacherViewList.get(0).getTeacherDet().get(0).getPermanentCode());
							teacherDataBean.setManagement(teacherViewList.get(0).getTeacherDet().get(0).getManagement());
							/*teacherDataBean.setTeaPermanentState(teacherViewList.get(0).getTeacherDet().get(0)
									.getPostcode2().getState().getStateName());
							teacherDataBean.setTeaPresentState(teacherViewList.get(0).getTeacherDet().get(0)
									.getPostcode2().getState().getStateName());*/

						} else {
							teacherDataBean.setTeaFirstName("");
							teacherDataBean.setTeaLastName("");
							teacherDataBean.setTeaFatherName("");
							teacherDataBean.setTeaMotherName("");
							teacherDataBean.setTeaDob(null);
							teacherDataBean.setTeaGender("");
							teacherDataBean.setTeaEduQualification("");
							teacherDataBean.setTeaEmail("");
							teacherDataBean.setTeaID("");
							teacherDataBean.setTeaPercentage("");
							teacherDataBean.setTeaPhoneNo("");
							teacherDataBean.setTeaPosition("");
							teacherDataBean.setTeaPermanentAddress("");
							teacherDataBean.setTeaPresentAddress("");
							teacherDataBean.setTeaWorkingHour("");
							teacherDataBean.setTeaYearOfPassing("");
							teacherDataBean.setTeaPermanentZip("");
							teacherDataBean.setTeaPresentZip("");
							teacherDataBean.setTeaAddress1("");
							teacherDataBean.setTeaAddress2("");
							teacherDataBean.setTeaPermanentState("");
							teacherDataBean.setTeaPresentState("");
							teacherDataBean.setTeaFile(null);
							teacherDataBean.setTeaCity("");
							teacherDataBean.setTeaCity1("");
							teacherDataBean.setTeaState("");
							teacherDataBean.setTeaState1("");
							teacherDataBean.setTeaCountry("");
							teacherDataBean.setTeaCountry1("");
							teacherDataBean.setTeaPostal("");
							teacherDataBean.setTeaPostal1("");
							teacherDataBean.setTeaPhoneNo1("");
							teacherDataBean.setCode("");
							teacherDataBean.setCode1("");
							
						}
						if (teacherViewList.get(0).getTeacherImgPath().size() > 0) {
							teacherDataBean.setPath(teacherViewList.get(0).getTeacherImgPath().get(0).getPath());
							teacherDataBean.setPathDate(teacherViewList.get(0).getTeacherImgPath().get(0).getDate());
							setOldflag(true);
							setNewflag(false);
						} else {
							teacherDataBean.setPath("");
							teacherDataBean.setPathDate(null);
							setOldflag(false);
							setNewflag(true);
						}
						logger.info(teacherViewList.get(0).getTeacherSub().size());
						if (teacherViewList.get(0).getTeacherSub().size() > 0) {
							for (int j = 0; j < teacherViewList.get(0).getTeacherSub().size(); j++) {
								teacherDataBean.getTeaSubjectTargetList().add(teacherViewList.get(0).getTeacherSub().get(j).getSubject().getSujectName()
												+ "/" + teacherViewList.get(0).getTeacherSub().get(j).getSubject().getSubjectCode());
							}
						} else {
							teacherDataBean.getTeaSubjectTargetList().clear();
						}
						logger.info(teacherViewList.get(0).getTeacherCls().size());
						if (teacherViewList.get(0).getTeacherCls().size() > 0) {
							for (int m = 0; m < teacherViewList.get(0).getTeacherCls().size(); m++) {
								teacherDataBean.getTeaClassTargetList().add(teacherViewList.get(0).getTeacherCls().get(m).getClazz().getClassName()
												+ "/" + teacherViewList.get(0).getTeacherCls().get(m).getClazz().getClassSection());

							}

						} else {
							teacherDataBean.getTeaClassTargetList().clear();
						}

						logger.debug("state 1" + teacherDataBean.getTeaPresentState());
						logger.debug("state 2" + teacherDataBean.getTeaPermanentState());
						status = "Success";
					} catch (NullPointerException e) {
						logger.warn("Exception -->"+e.getMessage());
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return status;
	}
//single delete old
	public void delete() {
		SmsController controller = null;
		try {
			logger.debug("-----Inside call Teacher delete method ------" + teacherDataBean.getTeaID());
			logger.debug("----------------" + teacherDataBean.getTeaID());
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");

			if (personID != null && teacherDataBean.getTeaID() != null) {
				String status = controller.deleteTeacher(personID, teacherDataBean);
				logger.debug(status);
				if (status.equalsIgnoreCase("Success")) {
					setDelBoxflag(true);
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}

	}

	public String dummyAction(FileUploadEvent event) throws IOException {

		this.file = event.getFile();
		teacherDataBean.setTeaFile(event.getFile());
		FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, message);
		return "";
	}

	public String update() {

		try {
			logger.debug("----Inside update method calling----"+teacherDataBean.getTeaPermanentAddress()+teacherDataBean.getTeaPresentAddress());
			if (validate(true)) {
				RequestContext reqcontextt = RequestContext.getCurrentInstance();
				reqcontextt.execute("teachereditcheck();");

			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			logger.warn("Inside Exception",e);
		}
		return "";
	}

	public String updateTeacher() {
		String status = "";
		SmsController controller = null;
		try {
			String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolName");
			String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			int schoolid=Integer.parseInt(schoolID);
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			if (personID != null) {
				
				teacherDataBean.setTeaClassTargetList(classTarget);
				teacherDataBean.setTeaSubjectTargetList(subjectTarget);
				
				logger.debug("File Name" + teacherDataBean.getTeaFile());
				if (teacherDataBean.getTeaFile() != null) {
					String s = teacherDataBean.getTeaFile().getContentType();
					logger.debug("file Type " + s);
					String type = s.substring(s.lastIndexOf("/") + 1);
					logger.debug(type);

					copyFile(teacherDataBean.getTeaID(), teacherDataBean.getTeaFile().getInputstream(), type);

					logger.debug(teacherDataBean.getTeaID() + "." + type);
					String path = ft.format(now) + "/" + teacherDataBean.getTeaID() + "." + type;
					teacherDataBean.setTeafilePath(path);
					logger.debug(teacherDataBean.getTeafilePath());
				} else {
					teacherDataBean.setTeafilePath(null);
				}
				/*if(teacherDataBean.getTeaPhoneNo().startsWith("0")){
					String phoneno=teacherDataBean.getTeaPhoneNo().substring(1);
					teacherDataBean.setTeaPhoneNo(text.getString("sms.phno")+phoneno);
				}else if(teacherDataBean.getTeaPhoneNo().startsWith(text.getString("sms.phno"))){
					teacherDataBean.setTeaPhoneNo(teacherDataBean.getTeaPhoneNo());
				}else{
					teacherDataBean.setTeaPhoneNo(text.getString("sms.phno")+teacherDataBean.getTeaPhoneNo());
				}*/
				/*if (validateReg(true)) {*/
					status = controller.updateTeacher(personID, teacherDataBean);
				/*}*/
				if (status.equalsIgnoreCase("Success")) {
					setBoxflag(true);
					logger.debug("MB Status-->" + status);
					String pdfStatus = generatePdf(teacherDataBean);
					logger.debug("pdfStatus Status-->" + pdfStatus);
					MailSendJDBC.teacherUpdate(teacherDataBean,schoolName,schoolid);
					if (pdfStatus.equalsIgnoreCase("Success")) {
						/*String emailStatus = sendEmail(teacherDataBean, ImageListPath);
						logger.debug("emailStatus Status-->" + emailStatus);

						if (emailStatus.equalsIgnoreCase("Success")) {*/

							RequestContext reqcontext = RequestContext.getCurrentInstance();
							reqcontext.execute("PF('teacheredblockUIW').hide();");
							reqcontext.execute("PF('teacherUpdateDialog').show();");
							setBoxflag(true);

						/*} else {
							RequestContext reqcontext = RequestContext.getCurrentInstance();
							reqcontext.execute("PF('teacheredblockUIW').hide();");
							reqcontext.execute("PF('teacherUpdateDialog').hide();");
							setBoxflag(false);
						}*/
					} else {
						RequestContext reqcontext = RequestContext.getCurrentInstance();
						reqcontext.execute("PF('teacheredblockUIW').hide();");
						reqcontext.execute("PF('teacherUpdateDialog').show();");
						setBoxflag(true);
					}
				} else {
					RequestContext reqcontext = RequestContext.getCurrentInstance();
					reqcontext.execute("PF('teacheredblockUIW').hide();");
					reqcontext.execute("PF('teacherUpdateDialog').hide();");
					setBoxflag(false);
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			logger.warn("Exception -->"+e.getMessage());
		} finally {
			
		}
		return "";
	}
public void reset(){
	teacherDataBean.setTeaFirstName("");
	teacherDataBean.setTeaLastName("");
	teacherDataBean.setTeaFatherName("");
	teacherDataBean.setTeaMotherName("");
	teacherDataBean.setTeaDob(null);
	teacherDataBean.setTeaGender("");
	teacherDataBean.setTeaEduQualification("");
	teacherDataBean.setTeaEmail("");
	teacherDataBean.setTeaPercentage("");
	teacherDataBean.setTeaPhoneNo("");
	teacherDataBean.setTeaPosition("");
	teacherDataBean.setTeaPermanentAddress("");
	teacherDataBean.setTeaPresentAddress("");
	teacherDataBean.setTeaSubject("");
	teacherDataBean.setTeaTeacherPhoto("");
	teacherDataBean.setTeaWorkingHour("");
	teacherDataBean.setTeaYearOfPassing("");
	teacherDataBean.setTeaPermanentState("");
	teacherDataBean.setTeaPresentState("");
	teacherDataBean.setTeaPermanentZip("");
	teacherDataBean.setTeaPresentZip("");
	teacherDataBean.setTeaFile(null);
	teacherDataBean.setTeaSecurePasword("");
	teacherDataBean.setPath("");
	teacherDataBean.setPathDate(null);
	teacherDataBean.getTeaClassTargetList().clear();
	teacherDataBean.getTeaClassTargetList().clear();
	teacherDataBean.setNames("");
}
	private boolean validate(boolean flag) {
		String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();

		if (StringUtils.isEmpty(teacherDataBean.getTeaFirstName())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("TFName").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.fname")));
			}
			valid = false;

		} else if (!StringUtils.isEmpty(teacherDataBean.getTeaFirstName())) {
			if (!CommonValidate.validateName(teacherDataBean.getTeaFirstName())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("TFName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.vfname")));
				}
				valid = false;
			} else if (teacherDataBean.getTeaFirstName().length() < 3) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("TFName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.vfname")));
				}
				valid = false;
			}
		}
		
		
		if(StringUtils.isEmpty(teacherDataBean.getTeaCountry())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("TPcountry").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.teacherCountry")));
			}
			valid = false;
		}
		if(!StringUtils.isEmpty(teacherDataBean.getTeaCountry())){
			if(StringUtils.isEmpty(teacherDataBean.getTeaPhoneNo())) {/*
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("TPhone").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.phonenumber")));
				}
				valid = false;
			*/}else if(!CommonValidate.validateNumberOnly(teacherDataBean.getTeaPhoneNo())){
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("TPhone").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.phonenumberinvalid")));
				}
				valid = false;
			}
			else if(!CommonValidate.countryValidateNumber(teacherDataBean.getTeaPhoneNo(),teacherDataBean.getTeaCountry())){
				fieldName = CommonValidate.findComponentInRoot("TPhone").getClientId(fc);
				if (teacherDataBean.getTeaCountry().equalsIgnoreCase("INDIA")) {
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.india.phonevalidation")));
				}
				else if (teacherDataBean.getTeaCountry().equalsIgnoreCase("UAE")) {
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.uae.phonevalidation")));
				}
				else if (teacherDataBean.getTeaCountry().equalsIgnoreCase("INDONESIA")) {
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.indonesia.phonevalidation")));
				}
				else if (teacherDataBean.getTeaCountry().equalsIgnoreCase("MALAYSIA")) {
					fc.addMessage(fieldName, new FacesMessage(text.getString("sms.malaysia.phonevalidation")));
				}
				
				valid = false;
			}
		}
		
		if (StringUtils.isEmpty(teacherDataBean.getTeaID())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("TTeacherID").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.teacherId")));
			}
			valid = false;

		}
		logger.debug("subjectList ===="+subjectList.toString()+"========"+subjectTarget.size());
		if (subjectTarget.size() <= 0) {
			if (flag) {

				fieldName = CommonValidate.findComponentInRoot("TSubject").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.subject")));
			}
			valid = false;

		}
		if (classTarget.size() <= 0) {
			if (flag) {

				fieldName = CommonValidate.findComponentInRoot("TClass").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.class")));
			}
			valid = false;

		}
		if(schoolID.equals(paths.getString("SMRV.SCHOOLID"))){
			if(StringUtils.isEmpty(teacherDataBean.getManagement())){
				fieldName = CommonValidate.findComponentInRoot("management").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(text.getString("sms.teacher.management")));
				valid=false;
			}
		}
		return valid;
	}

	private void copyFile(String fileName, InputStream inputstream, String n) {
		try {

			// Create Directory
			File files = new File(paths.getString("sms.teacher.teacherphoto") + ft.format(now));
			if (!files.exists()) {
				files.mkdirs();
			} else {
				logger.debug("Alreday Found");
			}

			// write the inputStream to a FileOutputStream
			OutputStream out = new FileOutputStream(new File(files + paths.getString("path_context").toString() + fileName + "." + n));

			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = inputstream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

			inputstream.close();
			out.flush();
			out.close();

			logger.debug("New file created!");
		} catch (IOException e) {
			logger.debug(e.getMessage());
		}

	}

	/**
	 * @return the boxflag
	 */
	public boolean isBoxflag() {
		return boxflag;
	}

	/**
	 * @param boxflag
	 *            the boxflag to set
	 */
	public void setBoxflag(boolean boxflag) {
		this.boxflag = boxflag;
	}

	/**
	 * @return the delBoxflag
	 */
	public boolean isDelBoxflag() {
		return delBoxflag;
	}

	/**
	 * @param delBoxflag
	 *            the delBoxflag to set
	 */
	public void setDelBoxflag(boolean delBoxflag) {
		this.delBoxflag = delBoxflag;
	}

	public void clsSection(ValueChangeEvent event) {
		SmsController controller = null;
		try {
			if (event.getNewValue().toString() != null) {
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
						.get("LogID");
				if (personID != null) {
					teacherDataBean.setTeaclsSection(event.getNewValue().toString());
					teacherTableList = controller.getTeacherInfo(personID, teacherDataBean.getTeaclsSection());

				}
			}
		}

		catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
	}

	public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
		String schoolID=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
		int schoolid=Integer.parseInt(schoolID);
		String logo ="";		
		Document pdf = (Document) document;
		pdf.open();
		pdf.setPageSize(PageSize.A4);		
		if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
			logo = paths.getString(schoolID+"_LOGO");
			pdf.add(Image.getInstance(getClass().getClassLoader().getResource(logo)));
		}else{
			logo=MailSendJDBC.pdfProcess(schoolid);
			logger.debug("logo "+logo);
			pdf.add(Image.getInstance(logo));
		}		
		pdf.add(new Paragraph(
				"------------------------------------------------------------------------------------------------------------------------"));
	}

	private String generatePdf(TeacherDataBean teacherDataBean2) {
		String status = "fail";
		SmsController controller = null;
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			String schoolID=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			int schoolid=Integer.parseInt(schoolID);
			if (personID != null) {

				ImageListPath = new ArrayList<TeacherDataBean>();
				ImageListPath = controller.getImagePath(personID, teacherDataBean2.getTeaID());
				if (ImageListPath.size() > 0) {
					// Create Directory
					File files = new File(paths.getString("sms.teacher.teacherpdf") + ft.format(now));
					if (!files.exists()) {
						files.mkdirs();
					} else {
						logger.debug("Alreday Found");
					}
					// Write PDF File
					OutputStream file = new FileOutputStream(
							new File(files +paths.getString("path_context").toString() + teacherDataBean2.getTeaID() + ".pdf"));
					teacherDataBean.setTeafilePath(files + paths.getString("path_context").toString() + teacherDataBean2.getTeaID() + ".pdf");
					Document document = new Document(PageSize.A4, 50, 50, 10, 50);
					PdfWriter.getInstance(document, file);
					document.open();
					String logo ="";
					if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
						logo = paths.getString(schoolID+"_LOGO");
						document.add(Image.getInstance(getClass().getClassLoader().getResource(logo)));
					}else{
						logo = teacherDataBean.getSchoolLogo();
						document.add(Image.getInstance(logo));
					}
					document.add(new Paragraph(
							"------------------------------------------------------------------------------------------------------------------------"));

					float[] columnWidths = { 20f, 1f };
				//	Font font3 = new Font(Font.TIMES_ROMAN, 27);
					PdfPTable table = new PdfPTable(2); // .
					table.setWidthPercentage(100f);
					table.setWidths(columnWidths);

					PdfPCell cell1 = new PdfPCell(new Paragraph());
					cell1.setBorder(PdfPCell.NO_BORDER);
					PdfPCell cell2 = new PdfPCell(new Paragraph());
					cell2.setBorder(PdfPCell.NO_BORDER);
					table.setSpacingBefore(10f);
					table.setSpacingAfter(25f);

					PdfPTable nestedTable = new PdfPTable(4);
					nestedTable.setWidthPercentage(100f);
					PdfPCell nesCell1 = new PdfPCell(new Paragraph("First Name:"));
					PdfPCell nesCell2 = new PdfPCell(new Paragraph(ImageListPath.get(0).getTeaFirstName()));
					PdfPCell nesCell3 = new PdfPCell(new Paragraph("Last Name:"));
					PdfPCell nesCell4 = new PdfPCell(new Paragraph(ImageListPath.get(0).getTeaLastName()));
					nesCell1.setBorder(PdfPCell.NO_BORDER);
					nesCell2.setBorder(PdfPCell.NO_BORDER);
					nesCell3.setBorder(PdfPCell.NO_BORDER);
					nesCell4.setBorder(PdfPCell.NO_BORDER);
					nestedTable.addCell(nesCell1);
					nestedTable.addCell(nesCell2);
					nestedTable.addCell(nesCell3);
					nestedTable.addCell(nesCell4);
					nestedTable.setSpacingBefore(3f);
					nestedTable.setSpacingAfter(3f);
					cell1.addElement(nestedTable);

					PdfPTable nestedTable1 = new PdfPTable(4);
					nestedTable1.setWidthPercentage(100f);
					PdfPCell nesCell5 = new PdfPCell(new Paragraph("Father Name:"));
					PdfPCell nesCell6 = new PdfPCell(new Paragraph(ImageListPath.get(0).getTeaFatherName()));
					PdfPCell nesCell7 = new PdfPCell(new Paragraph("Mother Name:"));
					PdfPCell nesCell8 = new PdfPCell(new Paragraph(ImageListPath.get(0).getTeaMotherName()));
					nesCell5.setBorder(PdfPCell.NO_BORDER);
					nesCell6.setBorder(PdfPCell.NO_BORDER);
					nesCell7.setBorder(PdfPCell.NO_BORDER);
					nesCell8.setBorder(PdfPCell.NO_BORDER);
					nestedTable1.addCell(nesCell5);
					nestedTable1.addCell(nesCell6);
					nestedTable1.addCell(nesCell7);
					nestedTable1.addCell(nesCell8);
					nestedTable1.setSpacingBefore(3f);
					nestedTable1.setSpacingAfter(3f);
					cell1.addElement(nestedTable1);

					PdfPTable nestedTable2 = new PdfPTable(4);
					nestedTable2.setWidthPercentage(100f);
					PdfPCell nesCell9 = new PdfPCell(new Paragraph("Date of Birth:"));
					PdfPCell nesCell10 = new PdfPCell(new Paragraph(ImageListPath.get(0).getTeaDob().toString()));
					PdfPCell nesCell11 = new PdfPCell(new Paragraph("Gender:"));
					PdfPCell nesCell12 = new PdfPCell(new Paragraph(ImageListPath.get(0).getTeaGender()));
					nesCell10.setBorder(PdfPCell.NO_BORDER);
					nesCell11.setBorder(PdfPCell.NO_BORDER);
					nesCell12.setBorder(PdfPCell.NO_BORDER);
					nesCell9.setBorder(PdfPCell.NO_BORDER);
					nestedTable2.addCell(nesCell9);
					nestedTable2.addCell(nesCell10);
					nestedTable2.addCell(nesCell11);
					nestedTable2.addCell(nesCell12);
					nestedTable2.setSpacingBefore(3f);
					nestedTable2.setSpacingAfter(3f);
					cell1.addElement(nestedTable2);

					PdfPTable nestedTable3 = new PdfPTable(4);
					nestedTable3.setWidthPercentage(100f);
					PdfPCell nesCell13 = new PdfPCell(new Paragraph("Present Address:"));
					PdfPCell nesCell14 = new PdfPCell(new Paragraph(ImageListPath.get(0).getTeaPresentAddress()));
					PdfPCell nesCell15 = new PdfPCell(new Paragraph("Permanent Address:"));
					PdfPCell nesCell16 = new PdfPCell(new Paragraph(ImageListPath.get(0).getTeaPermanentAddress()));
					nesCell13.setBorder(PdfPCell.NO_BORDER);
					nesCell14.setBorder(PdfPCell.NO_BORDER);
					nesCell15.setBorder(PdfPCell.NO_BORDER);
					nesCell16.setBorder(PdfPCell.NO_BORDER);
					nestedTable3.addCell(nesCell13);
					nestedTable3.addCell(nesCell14);
					nestedTable3.addCell(nesCell15);
					nestedTable3.addCell(nesCell16);
					nestedTable3.setSpacingBefore(3f);
					nestedTable3.setSpacingAfter(3f);
					cell1.addElement(nestedTable3);
					
					PdfPTable nestedTable6 = new PdfPTable(4);
					nestedTable6.setWidthPercentage(100f);
					PdfPCell nesCell21 = new PdfPCell(new Paragraph("Phone Number:"));
					PdfPCell nesCell22 = new PdfPCell(new Paragraph(ImageListPath.get(0).getTeaPhoneNo()));
					PdfPCell nesCell23 = new PdfPCell(new Paragraph("Email ID:"));
					PdfPCell nesCell24 = new PdfPCell(new Paragraph(ImageListPath.get(0).getTeaEmail()));
					nesCell21.setBorder(PdfPCell.NO_BORDER);
					nesCell22.setBorder(PdfPCell.NO_BORDER);
					nesCell23.setBorder(PdfPCell.NO_BORDER);
					nesCell24.setBorder(PdfPCell.NO_BORDER);
					nestedTable6.addCell(nesCell21);
					nestedTable6.addCell(nesCell22);
					nestedTable6.addCell(nesCell23);
					nestedTable6.addCell(nesCell24);
					nestedTable6.setSpacingBefore(3f);
					nestedTable6.setSpacingAfter(3f);
					cell1.addElement(nestedTable6);

					PdfPTable nestedTable4 = new PdfPTable(4);
					nestedTable4.setWidthPercentage(100f);
					PdfPCell nesCell17 = new PdfPCell(new Paragraph("Teacher ID:"));
					PdfPCell nesCell18 = new PdfPCell(new Paragraph(ImageListPath.get(0).getTeaID()));
					PdfPCell nesCell19 = new PdfPCell(new Paragraph("Post / Position"));
					PdfPCell nesCell20 = new PdfPCell(new Paragraph(teacherDataBean2.getTeaPosition()));
					nesCell17.setBorder(PdfPCell.NO_BORDER);
					nesCell18.setBorder(PdfPCell.NO_BORDER);
					nesCell19.setBorder(PdfPCell.NO_BORDER);
					nesCell20.setBorder(PdfPCell.NO_BORDER);
					nestedTable4.addCell(nesCell17);
					nestedTable4.addCell(nesCell18);
					nestedTable4.addCell(nesCell19);
					nestedTable4.addCell(nesCell20);
					nestedTable4.setSpacingBefore(3f);
					nestedTable4.setSpacingAfter(3f);
					cell1.addElement(nestedTable4);

					PdfPTable nestedTable5 = new PdfPTable(4);
					nestedTable5.setWidthPercentage(100f);
					PdfPCell nesCell37 = new PdfPCell(new Paragraph("Class:"));
					PdfPCell nesCell38 = new PdfPCell(new Paragraph(teacherDataBean2.getTeaClassTargetList().toString()));
					PdfPCell nesCell39 = new PdfPCell(new Paragraph("Faculties / Subject:"));
					PdfPCell nesCell40 = new PdfPCell(new Paragraph(teacherDataBean2.getTeaSubjectTargetList().toString()));
					nesCell37.setBorder(PdfPCell.NO_BORDER);
					nesCell38.setBorder(PdfPCell.NO_BORDER);
					nesCell39.setBorder(PdfPCell.NO_BORDER);
					nesCell40.setBorder(PdfPCell.NO_BORDER);
					nestedTable5.addCell(nesCell37);
					nestedTable5.addCell(nesCell38);
					nestedTable5.addCell(nesCell39);
					nestedTable5.addCell(nesCell40);
					nestedTable5.setSpacingBefore(3f);
					nestedTable5.setSpacingAfter(3f);
					cell1.addElement(nestedTable5);

					

					PdfPTable nestedTable7 = new PdfPTable(4);
					nestedTable7.setWidthPercentage(100f);
					PdfPCell nesCell25 = new PdfPCell(new Paragraph("Education Qualification:"));
					PdfPCell nesCell26 = new PdfPCell(new Paragraph(ImageListPath.get(0).getTeaEduQualification()));
					PdfPCell nesCell27 = new PdfPCell(new Paragraph("Year of Passing:"));
					PdfPCell nesCell28 = new PdfPCell(new Paragraph(ImageListPath.get(0).getTeaYearOfPassing()));
					nesCell25.setBorder(PdfPCell.NO_BORDER);
					nesCell26.setBorder(PdfPCell.NO_BORDER);
					nesCell27.setBorder(PdfPCell.NO_BORDER);
					nesCell28.setBorder(PdfPCell.NO_BORDER);
					nestedTable7.addCell(nesCell25);
					nestedTable7.addCell(nesCell26);
					nestedTable7.addCell(nesCell27);
					nestedTable7.addCell(nesCell28);
					nestedTable7.setSpacingBefore(3f);
					nestedTable7.setSpacingAfter(3f);
					cell1.addElement(nestedTable7);

					PdfPTable nestedTable8 = new PdfPTable(4);
					nestedTable8.setWidthPercentage(100f);
					PdfPCell nesCell50 = new PdfPCell(new Paragraph("Working Hour:"));
					PdfPCell nesCell51 = new PdfPCell(new Paragraph(teacherDataBean2.getTeaWorkingHour()));
					PdfPCell nesCell52 = new PdfPCell(new Paragraph("Percentage:"));
					PdfPCell nesCell53 = new PdfPCell(new Paragraph(teacherDataBean2.getTeaPercentage()));
					nesCell50.setBorder(PdfPCell.NO_BORDER);
					nesCell51.setBorder(PdfPCell.NO_BORDER);
					nesCell52.setBorder(PdfPCell.NO_BORDER);
					nesCell53.setBorder(PdfPCell.NO_BORDER);
					nestedTable8.addCell(nesCell50);
					nestedTable8.addCell(nesCell51);
					nestedTable8.addCell(nesCell52);
					nestedTable8.addCell(nesCell53);
					nestedTable8.setSpacingBefore(3f);
					nestedTable8.setSpacingAfter(3f);
					cell1.addElement(nestedTable8);
					
					PdfPTable nestedTable9 = new PdfPTable(4);
					nestedTable9.setWidthPercentage(100f);
					
					PdfPCell nesCell35 = new PdfPCell(new Paragraph("Photo"));
					PdfPCell nesCell36 = null;
					try{
						if(ImageListPath.get(0).getPath() !=null)
						{
							try{
								String tempdate = ft.format(ImageListPath.get(0).getPathDate());
								String imageLocation = paths.getString("sms.teacher.teacherphoto") + tempdate + "/" + ImageListPath.get(0).getPath();
								Image image = Image.getInstance(imageLocation);
								image.scaleAbsolute(100, 100);
								nesCell36 = new PdfPCell(image, false);								
							}catch(Exception e){								
								nesCell36 = new PdfPCell(new Paragraph(""));
								logger.error("catch ", e);
							}
						}else{
							 nesCell36 = new PdfPCell(new Paragraph(""));
						}
					}catch(Exception e){
						 nesCell36 = new PdfPCell(new Paragraph(""));
					}					
					PdfPCell nesCell33 = new PdfPCell(new Paragraph(""));
					PdfPCell nesCell34 = new PdfPCell(new Paragraph(""));
					nesCell35.setBorder(PdfPCell.NO_BORDER);
					nesCell36.setBorder(PdfPCell.NO_BORDER);
					nesCell33.setBorder(PdfPCell.NO_BORDER);
					nesCell34.setBorder(PdfPCell.NO_BORDER);
					nestedTable9.addCell(nesCell35);
					nestedTable9.addCell(nesCell36);
					nestedTable9.addCell(nesCell33);
					nestedTable9.addCell(nesCell34);
					nestedTable9.setSpacingBefore(3f);
					nestedTable9.setSpacingAfter(3f);
					cell1.addElement(nestedTable9);

					table.addCell(cell1);
					table.addCell(cell2);
					document.add(table);
					document.close();
					file.close();

					logger.debug("generatePdf Done");
					status = "Success";
				}
			}
		} catch (Exception e) {
			logger.warn("Inside Exception",e);
		}
		return status;
	}

	/*private String sendEmail(TeacherDataBean teacherDataBean2, List<TeacherDataBean> imageListPath2) {
		String status = "Fail";
		String schoolName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolName");
		try {
			String to = teacherDataBean2.getTeaEmail();
			logger.debug("to-------" + to);
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
					+ "<h3>Dear " + teacherDataBean2.getTeaID() + ",</h3>" + "<p>Welcome on-board into "
							+schoolName 
							+ "family. "
					+ "All the very best in your new assignment</p>" + "You Profile Updated" + "<footer>" + "<center>"
					+ "<h4>&copy; 2016 PT. Neotural Era Graha</h4>" + "</center>" + "</footer>" + "</body></html>";

			Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("nrgsolutions.india@gmail.com", "Nrg@1234");
				}
			});
			// compose message
			try {
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress("nrgsolutions.india@gmail.com"));
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
				message.setSubject("SMS Confirmation-" + teacherDataBean2.getTeaID());
				BodyPart messageBodyPart1 = new MimeBodyPart();
				messageBodyPart1.setContent(body1, "text/html");

				MimeBodyPart messageBodyPart2 = new MimeBodyPart();
				String filename =paths.getString("sms.teacher.teacherpdf") + ft.format(now) + "/" + teacherDataBean2.getTeaID()
						+ ".pdf";// change accordingly
				DataSource source = new FileDataSource(filename);
				messageBodyPart2.setDataHandler(new DataHandler(source));
				messageBodyPart2.setFileName(teacherDataBean2.getTeaID() + ".pdf");

				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart1);
				multipart.addBodyPart(messageBodyPart2);
				message.setContent(multipart);

				Transport.send(message);

				logger.debug("message sent successfully");
				logger.debug("message sent successfully");
				status = "Success";

			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			logger.warn("Inside Exception",e);
		}
		return status;
	}*/
	
	public void state(ValueChangeEvent v){
		SmsController controller=null;
		String valuechange="";
		teacherDataBean.setCode("");
		try{
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			logger.debug("=========================================1");
			controller = (SmsController) ctx.getBean("controller");	
			valuechange=v.getNewValue().toString();				
			listTeacher=controller.getcountryList(valuechange); 
			logger.debug("================>"+listTeacher.size());  
			if(valuechange.equalsIgnoreCase("INDIA")) teacherDataBean.setCode("+91"); 
		     else if(valuechange.equalsIgnoreCase("MALAYSIA")) teacherDataBean.setCode("+60"); 
		     else if(valuechange.equalsIgnoreCase("UAE")) teacherDataBean.setCode("+971"); 
		     else if(valuechange.equalsIgnoreCase("INDONESIA")) teacherDataBean.setCode("+62");
		}catch(Exception e){
			logger.warn("Exception -->"+e.getMessage());
		}
		}

	public void state1(ValueChangeEvent v){
		SmsController controller=null;
		String valuechange="";
		teacherDataBean.setCode1("");
		try{
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			logger.debug("=========================================1");
			controller = (SmsController) ctx.getBean("controller");	
			valuechange=v.getNewValue().toString();				
			listTeacher1=controller.getcountryList(valuechange); 
			logger.debug("================>"+listTeacher.size());  
			if(valuechange.equalsIgnoreCase("INDIA")) teacherDataBean.setCode1("+91"); 
		     else if(valuechange.equalsIgnoreCase("MALAYSIA")) teacherDataBean.setCode1("+60"); 
		     else if(valuechange.equalsIgnoreCase("UAE")) teacherDataBean.setCode1("+971"); 
		     else if(valuechange.equalsIgnoreCase("INDONESIA")) teacherDataBean.setCode1("+62");
		}catch(Exception e){
			logger.warn("Exception -->"+e.getMessage());
		}
		}
	
	public void checkbox(ValueChangeEvent v){	
		 String chkBox=null;
		try{
			logger.debug("========================>insidecheckbox================================>");
			chkBox=v.getNewValue().toString();
			if(chkBox.equals("true")){
				setPermenantAddFlag(false);
				setPermenantAddFlag1(true); 
				teacherDataBean.setTeaPermanentAddress(teacherDataBean.getTeaPresentAddress());  
				teacherDataBean.setTeaCountry1(teacherDataBean.getTeaCountry()); 
				teacherDataBean.setTeaState1(teacherDataBean.getTeaState()); 
				teacherDataBean.setTeaCity1(teacherDataBean.getTeaCity()); 
				teacherDataBean.setTeaPostal1(teacherDataBean.getTeaPostal()); 
				teacherDataBean.setTeaPhoneNo1(teacherDataBean.getTeaPhoneNo()); 
				teacherDataBean.setCode1(teacherDataBean.getCode());  
			}else{
				setPermenantAddFlag(true);
				setPermenantAddFlag1(false);  
				teacherDataBean.setTeaPermanentAddress("");  
				teacherDataBean.setTeaCountry1(""); 
				teacherDataBean.setTeaState1(""); 
				teacherDataBean.setTeaCity1(""); 
				teacherDataBean.setTeaPostal1(""); 
				teacherDataBean.setTeaPhoneNo1("");  
				teacherDataBean.setCode1(""); 
			}
		}catch(Exception e){
			logger.warn("Exception -->"+e.getMessage());
		}
	}
	
	/*global teacher view*/
	
	public String globalEdit(String globalValue) {
		flagback=false;
		SmsController controller = null;
		subjectList=new DualListModel<String>();
		classList=new DualListModel<String>();
		classSource=new ArrayList<String>();
		classTarget=new ArrayList<String>();
		subjectSource=new ArrayList<String>();
		subjectTarget=new ArrayList<String>();
		logger.debug("---->value -----gloabal-------->"+globalValue);
		
		teacherDataBean.setTeaID("");
		logger.debug("---->value -----gloabal-------->"+globalValue.split("/")[4]);
		try {
			teacherDataBean.setTeaID(globalValue.split("/")[4]);
			logger.debug("-----Inside call Teacher edit method ------");
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			
			if (personID != null) {
				if (teacherDataBean.getTeaID() != null) {
					String status = getTeacherView(personID, teacherDataBean.getTeaID());
					logger.debug("status" + status);
					logger.debug("state --- "+teacherDataBean.getTeaState());
					if (status.equalsIgnoreCase("Success")) {
						stateList = new ArrayList<String>();
						stateList = controller.getStateList(personID);
						logger.debug("stateList === "+stateList.size());
						if (classSource.size() > 0) {
							classSource.clear();
						}
						if (subjectSource.size() > 0) {
							subjectSource.clear();
						}

						subjectSource = controller.getSubjectList(personID);
						if (subjectSource.size() > 0) {
							subjectTarget.addAll(teacherDataBean.getTeaSubjectTargetList());
							/*for (int i = 0; i < subjectSource.size(); i++) {
								for (int j = 0; j < subjectTarget.size(); j++) {
									String temp1 = subjectSource.get(i);
									String temp2 = subjectTarget.get(j);
									if (temp2.equalsIgnoreCase(temp1)) {
										subjectSource.remove(temp1);
									}
								}
							}*/
						}
						subjectList = new DualListModel<String>(subjectSource, subjectTarget);
						classSource = controller.getClassSection(personID);
						if (classSource.size() > 0) {
							classTarget.addAll(teacherDataBean.getTeaClassTargetList());
							/*for (int m = 0; m < classSource.size(); m++) {
								for (int k = 0; k < classTarget.size(); k++) {
									String temp3 = classSource.get(m);
									String temp4 = classTarget.get(k);
									if (temp4.equalsIgnoreCase(temp3)) {
										classSource.remove(temp3);
									}
								}
							}*/
						}
						classList = new DualListModel<String>(classSource, classTarget);
						setBoxflag(false);
						try{
						listTeacher=controller.getcountryList(teacherDataBean.getTeaCountry()); 
						logger.debug("================>"+listTeacher.size());  
						if(teacherDataBean.getTeaCountry().equalsIgnoreCase("INDIA")) teacherDataBean.setCode("+91"); 
					     else if(teacherDataBean.getTeaCountry().equalsIgnoreCase("MALAYSIA")) teacherDataBean.setCode("+60"); 
					     else if(teacherDataBean.getTeaCountry().equalsIgnoreCase("UAE")) teacherDataBean.setCode("+971"); 
					     else if(teacherDataBean.getTeaCountry().equalsIgnoreCase("INDONESIA")) teacherDataBean.setCode("+62"); 
						
						listTeacher1=controller.getcountryList(teacherDataBean.getTeaCountry1()); 
						logger.debug("================>"+listTeacher.size());  
						if(teacherDataBean.getTeaCountry1().equalsIgnoreCase("INDIA")) teacherDataBean.setCode1("+91"); 
					     else if(teacherDataBean.getTeaCountry1().equalsIgnoreCase("MALAYSIA")) teacherDataBean.setCode1("+60"); 
					     else if(teacherDataBean.getTeaCountry1().equalsIgnoreCase("UAE")) teacherDataBean.setCode1("+971"); 
					     else if(teacherDataBean.getTeaCountry1().equalsIgnoreCase("INDONESIA")) teacherDataBean.setCode("+62"); 
						}
						catch(Exception e){
							
						}
						gettimelist();
						FacesContext.getCurrentInstance().getExternalContext().redirect(paths.getString("stumosys.teacherglobalpage"));
						return "";
					} else {
						logger.debug("Fail");
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "";

	}
	

	private void gettimelist() {
		try{
			teacherDataBean.setTimeList(new ArrayList<String>());
			teacherDataBean.getTimeList().addAll(Arrays.asList("12:00 AM","12:15 AM","12:30 AM","12:45 AM","01:00 AM","01:15 AM","01:30 AM","01:45 AM","02:00 AM","02:15 AM","02:30 AM","02:45 AM",
					"03:00 AM","03:15 AM","03:30 AM","03:45 AM","04:00 AM","04:15 AM","04:30 AM","04:45 AM","05:00 AM","05:15 AM","05:30 AM","05:45 AM","06:00 AM","06:15 AM","06:30 AM",
					"06:45 AM","07:00 AM","07:15 AM","07:30 AM","07:45 AM","08:00 AM","08:15 AM","08:30 AM","08:45 AM","09:00 AM","09:15 AM","09:30 AM","09:45 AM","10:00 AM","10:15 AM","10:30 AM",
					"10:45 AM","11:00 AM","11:15 AM","11:30 AM","11:45 AM","12:00 PM","12:15 PM","12:30 PM","12:45 PM","01:00 PM","01:15 PM","01:30 PM","01:45 PM","02:00 PM","02:15 PM","02:30 PM",
					"02:45 PM","03:00 PM","03:15 PM","03:30 PM","03:45 PM","04:00 PM","04:15 PM","04:30 PM","04:45 PM","05:00 PM","05:15 PM","05:30 PM","05:45 PM","06:00 PM","06:15 PM",
					"06:30 PM","06:45 PM","07:00 PM","07:15 PM","07:30 PM","07:45 PM","08:00 PM","08:15 PM","08:30 PM","08:45 PM","09:00 PM","09:15 PM","09:30 PM","09:45 PM","10:00 PM","10:15 PM",
					"10:30 PM","10:45 PM","11:00 PM","11:15 PM","11:30 PM","11:45 PM"));
			
		}catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		
	}

	public void onTabChange(TabChangeEvent event) {
		 SmsController controller=null;
		 List<TeacherDataBean> teacherlist=null;
		 String tabName;
		logger.debug("----------------Teacher Id------------------------"+teacherDataBean.getTeaID());
       try {
    	   tabName=event.getTab().getTitle();
    	  logger.debug(tabName);
    	   teacherlist=new ArrayList<TeacherDataBean>();
    	   teacherDataBean.setPerson_ID((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID"));
    	   ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
    	   controller = (SmsController) ctx.getBean("controller");
    	   setValidationMsg("");
    	   if (tabName.equalsIgnoreCase("Attendance")) {
    		   Calendar now = Calendar.getInstance();
    		   teacherDataBean.setCurrentyear(Integer.valueOf(now.get(Calendar.YEAR)));
    		   teacherDataBean.setCurrentmonths(Integer.valueOf(now.get(Calendar.MONTH) + 1));
    		   teacherDataBean.setAttendentsDate(new Date());
    		   teacherDataBean.setAttendentsStatus("Present");
    		   teacherDataBean.setInTime(null);
    		   teacherDataBean.setOutTime(null);
    		   teacherDataBean.setLeaveType("");
    		   
    		   teacherDataBean.setWorkingdays("0");
    		   teacherDataBean.setHalfdays("0");
    		   teacherDataBean.setCldays("0");
    		   teacherDataBean.setOddays("0");
    		   teacherDataBean.setOtdays("0");
    		   
    		   teacherDataBean.setAttTypeofPeriod("All");
    		   teacherlist= controller.getAttendentsDetail(teacherDataBean); 
    		   if (teacherlist.size()>0) {
    			  for (int i = 0; i < teacherlist.size(); i++) {
					if (teacherlist.get(i).getAttendentsStatus().equalsIgnoreCase("Present")) {
						teacherDataBean.setWorkingdays(String.valueOf((Integer.parseInt(teacherDataBean.getWorkingdays())+1)));
					}
					else{
						if (teacherlist.get(i).getLeaveType().equalsIgnoreCase("CL")) {
							teacherDataBean.setCldays(String.valueOf((Integer.parseInt(teacherDataBean.getCldays())+1)));
						}
						if (teacherlist.get(i).getLeaveType().equalsIgnoreCase("Half day")) {
							teacherDataBean.setHalfdays(String.valueOf((Integer.parseInt(teacherDataBean.getHalfdays())+1)));
						}
						if (teacherlist.get(i).getLeaveType().equalsIgnoreCase("OD")) {
							teacherDataBean.setOddays(String.valueOf((Integer.parseInt(teacherDataBean.getOddays())+1)));
						}
						if (teacherlist.get(i).getLeaveType().equalsIgnoreCase("OT")) {
							teacherDataBean.setOtdays(String.valueOf((Integer.parseInt(teacherDataBean.getOtdays())+1)));
						}
					}
    			  }
    			 logger.debug("count--->"+teacherDataBean.getWorkingdays()+"--->"+teacherDataBean.getCldays()+"--->"+
    			  teacherDataBean.getHalfdays()+"--->"+teacherDataBean.getOddays()+"--->"+teacherDataBean.getOtdays());
    		   	}
    		   else{
    			 logger.debug("------------inside else condition empty list---------------");
    		   }
    		   
    		  }
    	   else  if (tabName.equalsIgnoreCase("Notes")) {
    		   teacherDataBean.setNotesDate(new Date());
    		   teacherDataBean.setNotesHeading("");
    		   teacherDataBean.setNotes("");
    		   notesView();
    		   
    	   }
		
	} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
	}
    }
      
	public void Monthlycount(ValueChangeEvent v){	
		
		try {
			Calendar now = Calendar.getInstance();
 		   	teacherDataBean.setCurrentyear(Integer.valueOf(now.get(Calendar.YEAR)));
 		   	teacherDataBean.setCurrentmonths(Integer.valueOf(v.getNewValue().toString())); 		   
 		   	teacherDataBean.setAttendentsDate(new Date());
 		   	attendancecount();
 		  
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
	}
	public void attendancecount(){	
		 SmsController controller=null;
		 List<TeacherDataBean> teacherlist=null;
		try {
			 teacherlist=new ArrayList<TeacherDataBean>();
			 teacherDataBean.setPerson_ID((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID"));
			 ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			 controller = (SmsController) ctx.getBean("controller");	
			 teacherDataBean.setWorkingdays("0");
	 		   teacherDataBean.setHalfdays("0");
	 		   teacherDataBean.setCldays("0");
	 		   teacherDataBean.setOddays("0");
	 		   teacherDataBean.setOtdays("0");
	 		   
	 		   teacherDataBean.setAttTypeofPeriod("All");
	 		   teacherlist= controller.getAttendentsDetail(teacherDataBean); 
	 		   if (teacherlist.size()>0) {
	 			  for (int i = 0; i < teacherlist.size(); i++) {
	 				  logger.debug("getAttendentsStatus---->"+teacherlist.get(i).getAttendentsStatus());
						if (teacherlist.get(i).getAttendentsStatus().equalsIgnoreCase("Present")) {
							teacherDataBean.setWorkingdays(String.valueOf((Integer.parseInt(teacherDataBean.getWorkingdays())+1)));
						}
						else{
							if (teacherlist.get(i).getLeaveType().equalsIgnoreCase("CL")) {
								teacherDataBean.setCldays(String.valueOf((Integer.parseInt(teacherDataBean.getCldays())+1)));
							}
							if (teacherlist.get(i).getLeaveType().equalsIgnoreCase("Half day")) {
								teacherDataBean.setHalfdays(String.valueOf((Integer.parseInt(teacherDataBean.getHalfdays())+1)));
							}
							if (teacherlist.get(i).getLeaveType().equalsIgnoreCase("OD")) {
								teacherDataBean.setOddays(String.valueOf((Integer.parseInt(teacherDataBean.getOddays())+1)));
							}
							if (teacherlist.get(i).getLeaveType().equalsIgnoreCase("OT")) {
								teacherDataBean.setOtdays(String.valueOf((Integer.parseInt(teacherDataBean.getOtdays())+1)));
							}
						}
	 			  }
	 		   	}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
	}
	
	List<TeacherDataBean> teacherAtteDetList=new ArrayList<TeacherDataBean>();
	List<TeacherDataBean> notesList=new ArrayList<TeacherDataBean>();
	public String validationMsg;
	
	
	public String getValidationMsg() {
		return validationMsg;
	}

	public void setValidationMsg(String validationMsg) {
		this.validationMsg = validationMsg;
	}

	public List<TeacherDataBean> getNotesList() {
		return notesList;
	}

	public void setNotesList(List<TeacherDataBean> notesList) {
		this.notesList = notesList;
	}

	public List<TeacherDataBean> getTeacherAtteDetList() {
		return teacherAtteDetList;
	}

	public void setTeacherAtteDetList(List<TeacherDataBean> teacherAtteDetList) {
		this.teacherAtteDetList = teacherAtteDetList;
	}

	public void detailview(String daytype){
		logger.debug("inside method detail View"+daytype);
		SmsController controller=null;
		 List<TeacherDataBean> teacherlist=null;
		try {
			 teacherlist=new ArrayList<TeacherDataBean>();
			 teacherAtteDetList=new ArrayList<TeacherDataBean>();
			 teacherDataBean.setPerson_ID((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID"));
			 ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			 controller = (SmsController) ctx.getBean("controller");	
	 		   teacherlist= controller.getAttendentsDetail(teacherDataBean); 
	 		   if (teacherlist.size()>0) {
	 			   		for (int j = 0; j < teacherlist.size(); j++) {
	 			   				TeacherDataBean temp= new TeacherDataBean();
						
								if (daytype.equalsIgnoreCase("WorkingDays") && teacherlist.get(j).getAttendentsStatus().equalsIgnoreCase("Present")) {
									temp.setAttendentsDate(teacherlist.get(j).getAttendentsDate());
									temp.setAttendentsStatus(teacherlist.get(j).getAttendentsStatus());
									temp.setInTime(teacherlist.get(j).getInTime());
									temp.setOutTime(teacherlist.get(j).getOutTime());
									temp.setLeaveType("Nill");
									teacherAtteDetList.add(temp);
									
								}
								else{
									if (daytype.equalsIgnoreCase("HalfDay") && teacherlist.get(j).getLeaveType().equalsIgnoreCase("Half day")) {
										temp.setAttendentsDate(teacherlist.get(j).getAttendentsDate());
										temp.setAttendentsStatus(teacherlist.get(j).getAttendentsStatus());
										temp.setInTime(teacherlist.get(j).getInTime());
										temp.setOutTime(teacherlist.get(j).getOutTime());
										temp.setLeaveType(teacherlist.get(j).getLeaveType());
										teacherAtteDetList.add(temp);
									}
									else if (daytype.equalsIgnoreCase("CL") && teacherlist.get(j).getLeaveType().equalsIgnoreCase("CL")) {
										temp.setAttendentsDate(teacherlist.get(j).getAttendentsDate());
										temp.setAttendentsStatus(teacherlist.get(j).getAttendentsStatus());
										temp.setInTime(null);
										temp.setOutTime(null);
										temp.setLeaveType(teacherlist.get(j).getLeaveType());
										teacherAtteDetList.add(temp);
									}
									else if (daytype.equalsIgnoreCase("OD") && teacherlist.get(j).getLeaveType().equalsIgnoreCase("OD")) {
										temp.setAttendentsDate(teacherlist.get(j).getAttendentsDate());
										temp.setAttendentsStatus(teacherlist.get(j).getAttendentsStatus());
										temp.setInTime(null);
										temp.setOutTime(null);
										temp.setLeaveType(teacherlist.get(j).getLeaveType());
										teacherAtteDetList.add(temp);
									}
									else if (daytype.equalsIgnoreCase("OT") && teacherlist.get(j).getLeaveType().equalsIgnoreCase("OT")) {
										temp.setAttendentsDate(teacherlist.get(j).getAttendentsDate());
										temp.setAttendentsStatus(teacherlist.get(j).getAttendentsStatus());
										temp.setInTime(null);
										temp.setOutTime(null);
										temp.setLeaveType(teacherlist.get(j).getLeaveType());
										teacherAtteDetList.add(temp);
									}
								}
							
							
	 			   		}
	 			   	RequestContext reqcontext = RequestContext.getCurrentInstance();
	 			   	reqcontext.execute("PF('teacherAttendancedetail').show();");
	 		   }
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
	}
	public void saveAttendents(){	
		SmsController controller=null;
		String Status="Fail";
		String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
		try {
			teacherDataBean.setPerson_ID(personID);
			teacherDataBean.setSchoolID(Integer.parseInt((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID")));
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");	
			Status=controller.saveAttendents(teacherDataBean); 
			if(Status.equalsIgnoreCase("Success")){
				RequestContext reqcontext = RequestContext.getCurrentInstance();
				reqcontext.execute("PF('teacheredblockUIW').hide();");
				reqcontext.execute("PF('teacherAttentensDialog').show();");
			}
			
			
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		finally{
			
		}
	
	}
	public void saveNotes(){	
		SmsController controller=null;
		String Status="Fail";
		String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
		setValidationMsg("");
		try {
			teacherDataBean.setPerson_ID(personID);
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");	
			Status=controller.saveNotes(teacherDataBean); 
			if(Status.equalsIgnoreCase("Success")){
				RequestContext reqcontext = RequestContext.getCurrentInstance();
				reqcontext.execute("PF('teacheredblockUIW').hide();");
				reqcontext.execute("PF('teacherNotesDialog').show();");
			}
			else if (Status.equalsIgnoreCase("Exist")) {
				setValidationMsg("Notes Title is Already Exist");
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		finally{
			teacherDataBean.setNotesHeading("");
			
		}
	
	}
	public void notesView(){
		SmsController controller=null;
		 List<TeacherDataBean> teacherNotesList=null;
		try {
			teacherNotesList=new ArrayList<TeacherDataBean>();
			notesList=new ArrayList<TeacherDataBean>();
			 teacherAtteDetList=new ArrayList<TeacherDataBean>();
			 teacherDataBean.setPerson_ID((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID"));
			 ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			 controller = (SmsController) ctx.getBean("controller");	
			 teacherNotesList= controller.getNotesDetail(teacherDataBean); 
	 		   if (teacherNotesList.size()>0) {
	 			  notesList.addAll(teacherNotesList);
	 		   }
		}
		catch(Exception e){
			logger.warn("Exception -->"+e.getMessage());
			}
	}
	public void viewnotes(String c) {
		logger.debug("-------heading value for view----------->"+c+"--notelist size->"+notesList.size());
		try {
			for (int i = 0; i < notesList.size(); i++) {
				if (c.equalsIgnoreCase(notesList.get(i).getNotesHeading())) {
					teacherDataBean.setNotes(notesList.get(i).getNotes());
					teacherDataBean.setNotesHeading(notesList.get(i).getNotesHeading());
					teacherDataBean.setNotesDate(notesList.get(i).getNotesDate());
					teacherDataBean.setCode(notesList.get(i).getCode());
					RequestContext reqcontext = RequestContext.getCurrentInstance();
					reqcontext.execute("PF('teacheredblockUIW').hide();");
				}
			}
			
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		
	}
		public void updatenotes() {
					SmsController controller=null;
					String Status="Fail";
					try {
						logger.debug("------value----->"+teacherDataBean.getCode()+"----"+teacherDataBean.getNotesHeading());
						
						ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
						controller = (SmsController) ctx.getBean("controller");	
						Status=controller.updateNotes(teacherDataBean); 
						RequestContext reqcontext = RequestContext.getCurrentInstance();
						if(Status.equalsIgnoreCase("Success")){
							reqcontext.execute("PF('teacheredblockUIW').hide();");
							reqcontext.execute("PF('teacherNotesDialog').show();");
						}
						else{
							reqcontext.execute("PF('teacherNotesDialog').hide();");
						}
					} catch (Exception e) {
						logger.warn("Exception -->"+e.getMessage());
					}
					finally{
						teacherDataBean.setNotesHeading("");
						teacherDataBean.setNotes("");
						teacherDataBean.setNotesDate(new Date());
					}
				}
		public void deletenotes() {
			SmsController controller=null;
			String Status="Fail";
			try {
				logger.debug("------value----->"+teacherDataBean.getCode()+"----"+teacherDataBean.getNotesHeading());
				
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");	
				Status=controller.deleteNotes(teacherDataBean); 
				RequestContext reqcontext = RequestContext.getCurrentInstance();
				if(Status.equalsIgnoreCase("Success")){
					reqcontext.execute("PF('teacheredblockUIW').hide();");
					reqcontext.execute("PF('notesDeleteDialog').show();");
				}
				else{
					reqcontext.execute("PF('notesDeleteDialog').hide();");
				}
			} catch (Exception e) {
				logger.warn("Exception -->"+e.getMessage());
			}
			finally{
				teacherDataBean.setNotesHeading("");
				teacherDataBean.setNotes("");
				teacherDataBean.setNotesDate(new Date());
			}
		}

			public void viewnotesclose() {
				try {
					teacherDataBean.setNotes("");
					teacherDataBean.setNotesHeading("");
					teacherDataBean.setNotesDate(new Date());
				} catch (Exception e) {
					logger.warn("Exception -->"+e.getMessage());
				}
			}
		public void MonthlyNotes(ValueChangeEvent v){	
			List<TeacherDataBean> templist=null;
			try {
				templist=new ArrayList<TeacherDataBean>();
				notesView();
				
				for (int i = 0; i < notesList.size(); i++) {
					if (v.getNewValue().equals((notesList.get(i).getNotesDate().getMonth())+1) ){
						templist.add(notesList.get(i));
					}
				}
				notesList.clear();
				notesList.addAll(templist);
	 		  
			} catch (Exception e) {
				logger.warn("Exception -->"+e.getMessage());
			}
		}
}
