package com.stumosys.util;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

@ManagedBean(name = "zipValidationMB")
@RequestScoped
public class ZipValidationMB {
	public String myName1;
	public String myName2;
	public String myName3;
	public String myName4;
	public String myName5;
	public String myName6;
	public String myName7;
	public String myName8;
	public String myName9;
	public String myName10;
	public String myName11;
	public String myName12;
	public String myName13;
	public String myName14;
	public String code;
	public String status;
	final static Logger logger = Logger.getLogger(ZipValidationMB.class);
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void sample() {
		logger.debug("---------inside sample--------");
		String fieldName;
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> params = context.getExternalContext().getRequestParameterMap();
		myName1 = params.get("x");
		myName2 = params.get("y");
		myName3 = params.get("a");
		myName4 = params.get("b");
		code = Validation.zipvalidation(myName1);
		if (!myName2.equals(code)) {
			logger.debug("----inside if-----");
			fieldName = CommonValidate.findComponentInRoot("myTextBox").getClientId(context);
			context.addMessage(fieldName, new FacesMessage("Please Enter the valid Present Zip."));

		}
		code = Validation.zipvalidation(myName3);
		logger.debug("---input state-----" + myName2);
		logger.debug("---zip code state----" + code);
		if (!myName4.equals(code)) {
			fieldName = CommonValidate.findComponentInRoot("myTextBox3").getClientId(context);
			context.addMessage(fieldName, new FacesMessage("Please Enter the valid Permanent Zip."));
		}
		logger.debug("-----validation completed-----");
	}

	public void numberValidation() {
		logger.debug("---------inside numberValidation--------");
		String fieldName;
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> params = context.getExternalContext().getRequestParameterMap();
		myName1 = params.get("a");
		logger.debug("------roll number--------" + myName1);
		String status = Validation.rollValidation(myName1);
		if (StringUtils.isEmpty(myName1)) {
			fieldName = CommonValidate.findComponentInRoot("sRollNo").getClientId(context);
			context.addMessage(fieldName, new FacesMessage("Please Enter the roll number"));
		} else if (status.equalsIgnoreCase("Exist")) {
			fieldName = CommonValidate.findComponentInRoot("sRollNo").getClientId(context);
			context.addMessage(fieldName, new FacesMessage("This Roll Number Alreday Exsist"));

		}

	}

	public void nameValidation() {
		logger.debug("---------inside nameValidation--------");
		String fieldName;
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> params = context.getExternalContext().getRequestParameterMap();
		myName1 = params.get("a");
		myName2 = params.get("b");
		myName3 = params.get("c");
		myName4 = params.get("d");
		myName5 = params.get("e");
		myName6 = params.get("f");
		myName7 = params.get("g");
		myName8 = params.get("h");
		myName9 = params.get("i");
		myName10 = params.get("j");
		myName11 = params.get("k");
		myName12 = params.get("l");
		myName13 = params.get("m");
		myName14 = params.get("n");
		logger.debug("------father occupation--------" + myName11);
		logger.debug("------mother occupation--------" + myName13);
		logger.debug("------ class--------" + myName14);
		Pattern pattern = Pattern.compile("[a-zA-Z]+");
		Matcher matcher = pattern.matcher(myName1);
		Pattern pattern1 = Pattern.compile("\\(\\+\\d{2}\\)\\d{3}-\\d{3}-\\d{4}");
		Matcher matcher1 = pattern1.matcher(myName9);
		if (StringUtils.isEmpty(myName1)) {
			fieldName = CommonValidate.findComponentInRoot("sFName").getClientId(context);
			context.addMessage(fieldName, new FacesMessage("Please Enter the Student First Name."));
		} else if (!matcher.matches()) {
			logger.debug("----inside if-----");
			fieldName = CommonValidate.findComponentInRoot("sFName").getClientId(context);
			context.addMessage(fieldName, new FacesMessage("Please Enter only Albhabets"));
		}
		if (StringUtils.isEmpty(myName2)) {
			fieldName = CommonValidate.findComponentInRoot("sLName").getClientId(context);
			context.addMessage(fieldName, new FacesMessage("Please Enter the Student Last Name."));
		}
		if (StringUtils.isEmpty(myName3)) {
			fieldName = CommonValidate.findComponentInRoot("sFaName").getClientId(context);
			context.addMessage(fieldName, new FacesMessage("Please Enter the Student Father Name."));
		}
		if (StringUtils.isEmpty(myName4)) {
			fieldName = CommonValidate.findComponentInRoot("sMoName").getClientId(context);
			context.addMessage(fieldName, new FacesMessage("Please Enter the Student Mother Name."));
		}

		if (StringUtils.isEmpty(myName5)) {
			fieldName = CommonValidate.findComponentInRoot("sDob").getClientId(context);
			context.addMessage(fieldName, new FacesMessage("Please Enter the Student Date Of Birth."));
		}
		if (StringUtils.isEmpty(myName6)) {
			fieldName = CommonValidate.findComponentInRoot("sGender").getClientId(context);
			context.addMessage(fieldName, new FacesMessage("Please Enter the Student Gender."));
		}
		if (StringUtils.isEmpty(myName7)) {
			fieldName = CommonValidate.findComponentInRoot("SPAddress").getClientId(context);
			context.addMessage(fieldName, new FacesMessage("Please Enter the Student Present Address."));
		}
		if (StringUtils.isEmpty(myName8)) {
			fieldName = CommonValidate.findComponentInRoot("SpermantAddress").getClientId(context);
			context.addMessage(fieldName, new FacesMessage("Please Enter the Student Permanent Address."));
		}
		if (!matcher1.matches()) {
			logger.debug("----inside if-----");
			fieldName = CommonValidate.findComponentInRoot("sPhoneNo").getClientId(context);
			context.addMessage(fieldName, new FacesMessage("Please Enter Student Phone number."));
		}

		if (StringUtils.isEmpty(myName10)) {
			fieldName = CommonValidate.findComponentInRoot("sEmail").getClientId(context);
			context.addMessage(fieldName, new FacesMessage("Please Enter the Student Email Id."));
		} else if (!StringUtils.isEmpty(myName10)) {
			if (!CommonValidate.validateEmail(myName10)) {
				fieldName = CommonValidate.findComponentInRoot("sEmail").getClientId(context);
				context.addMessage(fieldName, new FacesMessage("Please Enter the valid EmailID."));
			}
		}

		if (StringUtils.isEmpty(myName11)) {
			fieldName = CommonValidate.findComponentInRoot("sFatherOccu").getClientId(context);
			context.addMessage(fieldName, new FacesMessage("Please Enter the Student Father Occupation."));
		}
		if (StringUtils.isEmpty(myName12)) {
			fieldName = CommonValidate.findComponentInRoot("sFatherInRange").getClientId(context);
			context.addMessage(fieldName, new FacesMessage("Please Enter the Student Father Income."));
		}
		if (StringUtils.isEmpty(myName13)) {
			fieldName = CommonValidate.findComponentInRoot("sMotherOccu").getClientId(context);
			context.addMessage(fieldName, new FacesMessage("Please Enter Mother Occupation."));
		}
		if (StringUtils.isEmpty(myName14)) {
			fieldName = CommonValidate.findComponentInRoot("sClass").getClientId(context);
			context.addMessage(fieldName, new FacesMessage("Please Enter the Class name."));
		}
		logger.debug("-----validation completed-----");

	}

	public void regValid1() {
		logger.debug("---------inside regvalid1--------");
		String fieldName;
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> params = context.getExternalContext().getRequestParameterMap();
		myName1 = params.get("a");
		myName2 = params.get("b");
		myName3 = params.get("c");
		myName4 = params.get("d");
		myName5 = params.get("e");
		myName6 = params.get("f");
		logger.debug("-----Gender--------" + myName6);
		Pattern pattern = Pattern.compile("[a-zA-Z]+");
		Matcher matcher = pattern.matcher(myName1);
		if (StringUtils.isEmpty(myName1)) {
			fieldName = CommonValidate.findComponentInRoot("sFName").getClientId(context);
			context.addMessage(fieldName, new FacesMessage("Please Enter the Student First Name."));
		} else if (!matcher.matches()) {
			logger.debug("----inside if-----");
			fieldName = CommonValidate.findComponentInRoot("sFName").getClientId(context);
			context.addMessage(fieldName, new FacesMessage("Please Enter only Albhabets"));
		}
		if (StringUtils.isEmpty(myName2)) {
			fieldName = CommonValidate.findComponentInRoot("sLName").getClientId(context);
			context.addMessage(fieldName, new FacesMessage("Please Enter the Student Last Name."));
		}
		if (StringUtils.isEmpty(myName3)) {
			fieldName = CommonValidate.findComponentInRoot("sFaName").getClientId(context);
			context.addMessage(fieldName, new FacesMessage("Please Enter the Student Father Name."));
		}
		if (StringUtils.isEmpty(myName4)) {
			fieldName = CommonValidate.findComponentInRoot("sMoName").getClientId(context);
			context.addMessage(fieldName, new FacesMessage("Please Enter the Student Mother Name."));
		}
		if (StringUtils.isEmpty(myName5)) {
			fieldName = CommonValidate.findComponentInRoot("sDob").getClientId(context);
			context.addMessage(fieldName, new FacesMessage("Please Enter the Student Date Of Birth."));
		}
		if (StringUtils.isEmpty(myName6)) {
			fieldName = CommonValidate.findComponentInRoot("sGender").getClientId(context);
			context.addMessage(fieldName, new FacesMessage("Please Enter the Student Gender."));
		}

	}

	public void regValid2() {
		logger.debug("---------inside regValid2--------");
		String fieldName;
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> params = context.getExternalContext().getRequestParameterMap();
		myName1 = params.get("a");
		myName2 = params.get("b");
		myName3 = params.get("c");
		myName4 = params.get("d");
		logger.debug("------father occupation--------" + myName7);
		Pattern pattern1 = Pattern.compile("\\(\\+\\d{2}\\)\\d{3}-\\d{3}-\\d{4}");
		Matcher matcher1 = pattern1.matcher(myName3);
		if (StringUtils.isEmpty(myName1)) {
			fieldName = CommonValidate.findComponentInRoot("SPAddress").getClientId(context);
			context.addMessage(fieldName, new FacesMessage("Please Enter the Student Present State."));
		}
		if (StringUtils.isEmpty(myName2)) {
			fieldName = CommonValidate.findComponentInRoot("SpermantAddress").getClientId(context);
			context.addMessage(fieldName, new FacesMessage("Please Enter the Student Permanent State."));
		}
		if (!matcher1.matches()) {
			logger.debug("----inside if-----");
			fieldName = CommonValidate.findComponentInRoot("sPhoneNo").getClientId(context);
			context.addMessage(fieldName, new FacesMessage("Please Enter Student Phone number."));
		}
		if (StringUtils.isEmpty(myName4)) {
			fieldName = CommonValidate.findComponentInRoot("sEmail").getClientId(context);
			context.addMessage(fieldName, new FacesMessage("Please Enter the Student Email Id."));
		} else if (!StringUtils.isEmpty(myName4)) {
			if (!CommonValidate.validateEmail(myName4)) {
				fieldName = CommonValidate.findComponentInRoot("sEmail").getClientId(context);
				context.addMessage(fieldName, new FacesMessage("Please Enter the valid EmailID."));
			}
		}
	}

	public void regValid3() {
		logger.debug("---------inside regValid3--------");
		String fieldName;
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> params = context.getExternalContext().getRequestParameterMap();
		myName1 = params.get("a");
		myName2 = params.get("b");
		myName3 = params.get("c");
		myName4 = params.get("d");
		if (StringUtils.isEmpty(myName1)) {
			fieldName = CommonValidate.findComponentInRoot("sFatherOccu").getClientId(context);
			context.addMessage(fieldName, new FacesMessage("Please Enter the Father Occupation."));
		}
		if (StringUtils.isEmpty(myName2)) {
			fieldName = CommonValidate.findComponentInRoot("sFatherInRange").getClientId(context);
			context.addMessage(fieldName, new FacesMessage("Please Enter the Father Income."));
		}
		if (StringUtils.isEmpty(myName3)) {
			fieldName = CommonValidate.findComponentInRoot("sMotherOccu").getClientId(context);
			context.addMessage(fieldName, new FacesMessage("Please Enter Mother Occupation."));
		}
		if (StringUtils.isEmpty(myName4)) {
			fieldName = CommonValidate.findComponentInRoot("sClass").getClientId(context);
			context.addMessage(fieldName, new FacesMessage("Please Enter the Class name."));
		}

	}
}