package com.stumosys.managedBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import scala.annotation.meta.setter;

import com.stumosys.controler.SmsController;
import com.stumosys.domain.StaffDataBean;
import com.stumosys.util.CommonValidate;

@ManagedBean(name="staffViewMB")
@RequestScoped
public class StaffViewMB {
	ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
	SmsController controller = (SmsController) ctx.getBean("controller");
	StaffDataBean staffDataBean= new StaffDataBean();
	String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
	String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
	FacesContext context=FacesContext.getCurrentInstance();
	ResourceBundle resource=context.getApplication().evaluateExpressionGet(context, "#{paths}", ResourceBundle.class);

	public StaffDataBean getStaffDataBean() {
		return staffDataBean;
	}

	public void setStaffDataBean(StaffDataBean staffDataBean) {
		this.staffDataBean = staffDataBean;
	}
	
	private List<String> teacherNamelist=null;
	private boolean hideFlag=false;
	private boolean showFlag=false;
	private List<String> studentNamelist=null;
	private List<String> emisnoList=null;
	private boolean tenthFlag=false;
	private boolean twelethFlag=false;
	
	public boolean isTenthFlag() {
		return tenthFlag;
	}

	public void setTenthFlag(boolean tenthFlag) {
		this.tenthFlag = tenthFlag;
	}

	public boolean isTwelethFlag() {
		return twelethFlag;
	}

	public void setTwelethFlag(boolean twelethFlag) {
		this.twelethFlag = twelethFlag;
	}

	public List<String> getEmisnoList() {
		return emisnoList;
	}

	public void setEmisnoList(List<String> emisnoList) {
		this.emisnoList = emisnoList;
	}

	public List<String> getStudentNamelist() {
		return studentNamelist;
	}

	public void setStudentNamelist(List<String> studentNamelist) {
		this.studentNamelist = studentNamelist;
	}

	public List<String> getTeacherNamelist() {
		return teacherNamelist;
	}

	public void setTeacherNamelist(List<String> teacherNamelist) {
		this.teacherNamelist = teacherNamelist;
	}

	public boolean isHideFlag() {
		return hideFlag;
	}

	public void setHideFlag(boolean hideFlag) {
		this.hideFlag = hideFlag;
	}

	public boolean isShowFlag() {
		return showFlag;
	}

	public void setShowFlag(boolean showFlag) {
		this.showFlag = showFlag;
	}

	public String relievingOrderPage() {
		try{
			teacherNamelist=new ArrayList<String>();
			staffDataBean.setCreateDate(null);
			staffDataBean.setTeacherName("");
			staffDataBean.setDesignation("");
			staffDataBean.setNameofDuty("");
			staffDataBean.setPlaceofDuty("");
			staffDataBean.setRelievingDate(null);
			staffDataBean.setReferenceNo("");
			staffDataBean.setStaPhoneNo("");
			setHideFlag(true);
			setShowFlag(false);
			teacherNamelist=controller.getTeachername(schoolID);
		}catch(Exception e){
			e.printStackTrace();
		}
		return"RelievingOrder";
	}

	public void printingForm(){
		setHideFlag(false);
		setShowFlag(true);
	}
	
	public List<String> teachernameAutocomplete(String name) {
		List<String> nameList=null;
		try{
			nameList=new ArrayList<String>();
			for (int i = 0; i < teacherNamelist.size(); i++) {
				if(teacherNamelist.get(i).toLowerCase().contains(name) ||teacherNamelist.get(i).toLowerCase().startsWith(name) ){
					nameList.add(teacherNamelist.get(i));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return nameList;
	}
	
	public String bonafidePage(){
		try{
			studentNamelist=new ArrayList<String>();
			staffDataBean.setStudentName("");
			staffDataBean.setParentName("");
			staffDataBean.setClassName("");
			staffDataBean.setAcademicFromyear("");
			staffDataBean.setAcademicToyear("");
			staffDataBean.setDateofBirth(null);
			staffDataBean.setCreateDate(null);
			studentNamelist=controller.getStudentname(schoolID);
			setHideFlag(true);
			setShowFlag(false);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "BonafideCertificate";
	}
	
	public List<String> studentnameAutocomplete(String name) {
		List<String> nameList=null;
		try{
			nameList=new ArrayList<String>();
			for (int i = 0; i < studentNamelist.size(); i++) { 
				if(studentNamelist.get(i).toLowerCase().contains(name) ||studentNamelist.get(i).toLowerCase().startsWith(name) ){
					nameList.add(studentNamelist.get(i));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return nameList;
	}
	
	public void nameValueChange(ValueChangeEvent event){
		String value="";
		try{
			value=event.getNewValue().toString();
			if (value!=null || value!="") {
				staffDataBean.setStudentName(value);
				controller.getStudentdate(personID,staffDataBean);
				staffDataBean.setAcademicFromyear(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
				staffDataBean.setAcademicToyear(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)+1));
				staffDataBean.setStudentName(value.split("/")[0]);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String conductPage(){
		try{
			studentNamelist=new ArrayList<String>();
			staffDataBean.setStudentName("");
			staffDataBean.setParentName("");
			staffDataBean.setCreateDate(null);
			staffDataBean.setConduct("");
			staffDataBean.setName("");
			studentNamelist=controller.getStudentname(schoolID);
			setHideFlag(true);
			setShowFlag(false);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "ConductCertificate";
	}
	
	public String transferCertificatePage() {
		try{
			staffDataBean=new StaffDataBean();
			setHideFlag(false);
			setShowFlag(false);
			setTenthFlag(false);
			setTwelethFlag(false);
			staffDataBean.setSchoolID(Integer.parseInt(schoolID));
			staffDataBean.setPersonID(personID);
		}catch(Exception e){
			e.printStackTrace();
		}
		return"TransferCertificate";
	}
	
	public String emisnoSearch(){
		boolean valid = false;
		FacesMessage msg;
		setHideFlag(false);
		setShowFlag(false);
		setTenthFlag(false);
		setTwelethFlag(false);
		try{
			if(StringUtils.isEmpty(staffDataBean.getEmisNo())){
				msg=new FacesMessage("Please Enter the EMIS No");
				FacesContext.getCurrentInstance().addMessage(CommonValidate.findComponentInRoot("panelgridid").getClientId(),msg);
				valid=false;
			}else if(!StringUtils.isEmpty(staffDataBean.getEmisNo())){
				if(!CommonValidate.validateNumberOnly(staffDataBean.getEmisNo())){
					msg=new FacesMessage("Please Enter the valid EMIS No");
					FacesContext.getCurrentInstance().addMessage(CommonValidate.findComponentInRoot("panelgridid").getClientId(),msg);
					valid=false;
				}else{
					valid=true;
				}
			}else{
				valid=true;
			}
			if(valid==true){
				String status=controller.emisnoDetails(staffDataBean);
				if("Success".equalsIgnoreCase(status)){
					if(resource.getString("SMRV.CLASSNAME").equals(staffDataBean.getClassName())){
					setTwelethFlag(true);
					}else{
					setTenthFlag(true);
					}
				}else{
					msg=new FacesMessage("No Records Found");
					FacesContext.getCurrentInstance().addMessage(CommonValidate.findComponentInRoot("panelgridid").getClientId(),msg);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return"";
	}
	
	public String saveTC(){
		String status="";
		setTwelethFlag(false);
		setTenthFlag(false);
		setShowFlag(false);
		setHideFlag(false);
		try{
			status=controller.saveTC(staffDataBean);
			if("Success".equalsIgnoreCase(status)){
				if(resource.getString("SMRV.CLASSNAME").equals(staffDataBean.getClassName())){
					setShowFlag(true);setHideFlag(false);
				}else{
					setShowFlag(false);setHideFlag(true);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return"";
	}

}
