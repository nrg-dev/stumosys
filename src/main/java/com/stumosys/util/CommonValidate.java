package com.stumosys.util;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * 
 * @author Robert Arjun
 * @date 16-10-2015 CommonValidate Class is used set the error message to UI
 *
 */
public class CommonValidate {

	public static UIComponent findComponentInRoot(String id) {
		UIComponent ret = null;

		FacesContext context = FacesContext.getCurrentInstance();
		if (context != null) {
			UIComponent root = context.getViewRoot();
			ret = findComponent(root, id);
		}

		return ret;
	}

	public static UIComponent findComponent(UIComponent base, String id) {

		UIComponent kid = null;
		UIComponent result = null;
		// Is the "base" component itself the match we are looking for?
		if (id.equals(base.getId())) {
			result = base;
		} else {
			// Search through our facets and children
			Iterator kids = base.getFacetsAndChildren();
			while (kids.hasNext() && (result == null)) {
				kid = (UIComponent) kids.next();
				if (id.equals(kid.getId())) {
					result = kid;
					break;
				}
				result = findComponent(kid, id);
				if (result != null) {
					break;
				}
			}
		}

		return result;

	}

	/*
	 * namePattern allows atleast one alphabet and optional number, optional
	 * special characters ('.-)
	 */
	public static final String namePattern = "^(?=.*[A-Za-z])([a-zA-Z0-9,\\'\\-\\.\\&\\/(\\s)+]*)$";

	public static final String numberPattern = "^(?=.*[0-9])([a-zA-Z0-9,\\'\\-\\.\\,(\\s)+]*)$";

	public static final String pricePattern = "([0-9,]+)";
	
	public static final String numberOnlyPattern = "([0-9]+)";

	public static boolean validateName(String name) {
		if (name == null)
			return false;
		Pattern pattern = Pattern.compile(namePattern);
		Matcher matcher = pattern.matcher(name);
		if (!matcher.matches()) {
			return false;
		}
		return true;
	}

	public static boolean validateNumber(String name) {
		if (name == null)
			return false;
		Pattern pattern = Pattern.compile(numberPattern);
		Matcher matcher = pattern.matcher(name);
		if (!matcher.matches()) {
			return false;
		}
		return true;
	}

	public static boolean validatePrice(String name) {
		if (name == null)
			return false;
		Pattern pattern = Pattern.compile(pricePattern);
		Matcher matcher = pattern.matcher(name);
		if (!matcher.matches()) {
			return false;
		}
		return true;
	}

	/*
	 * public static boolean validatePhone(String phone) { phone = phone.trim();
	 * if (phone.contains("-")) { Pattern pattern =
	 * Pattern.compile("\\d{5}-\\d{5}-\\d{5}"); Matcher matcher =
	 * pattern.matcher(phone); if (!matcher.matches()) { return false; } } else
	 * { Pattern pattern = Pattern.compile("\\d{15}"); Matcher matcher =
	 * pattern.matcher(phone); if (!matcher.matches()) { return false; } }
	 * return true; }
	 */

	/*
	 * Validate if the string is Numeric
	 */
	public static boolean isNumeric(String value) {
		try {
			Double.parseDouble(value);
			return true;
		} catch (Exception err) {
			return false;
		}
	}

	public static boolean validateFax(String value) {
		try {
			Double.parseDouble(value);
			return true;
		} catch (Exception err) {
			return false;
		}
	}

	public static boolean validateTax(String value) {
		try {
			Double.parseDouble(value);
			return true;
		} catch (Exception err) {
			return false;
		}
	}

	public static boolean validatePhone(String value) {
		try {
			Double.parseDouble(value);
			return true;
		} catch (Exception err) {
			return false;
		}
	}

	
	public static boolean validateNumberOnly(String name) {
		if (name == null)
			return false;
		Pattern pattern = Pattern.compile(numberOnlyPattern);
		Matcher matcher = pattern.matcher(name);
		if (!matcher.matches()) {
			return false;
		}
		return true;
	}
	public static final String phoneNumberValIndia = "^[0-9]{10}$";
	public static final String phoneNumberValUAE = "^[0-9]{9}$";
	public static final String phoneNumberValIndonesia= "^[0-9]{11,12}$";
	public static final String phoneNumberValMalaysia = "^[0-9]{9,10}$";
	
	public static boolean countryValidateNumber(String number,String country) {
		if (number == null)
			return false;
		
		Pattern pattern = null;
		
		if (country.equalsIgnoreCase("INDIA")) {
			 pattern = Pattern.compile(phoneNumberValIndia);
		}
		else if (country.equalsIgnoreCase("UAE")) {
		 pattern = Pattern.compile(phoneNumberValUAE);
		}
		else if (country.equalsIgnoreCase("INDONESIA")) {
		 pattern = Pattern.compile(phoneNumberValIndonesia);
		}
		else if (country.equalsIgnoreCase("MALAYSIA")) {
		 pattern = Pattern.compile(phoneNumberValMalaysia);
		}
		Matcher matcher = pattern.matcher(number);
		if (!matcher.matches()) {
			return false;
		}
		return true;
	}
	
	public static boolean countryCodeValidation(String number,String countryCode) {
		if (number == null)
			return false;
		
		Pattern pattern = null;
		
		if (countryCode.equalsIgnoreCase("+91")) {
			 pattern = Pattern.compile(phoneNumberValIndia);
		}
		else if (countryCode.equalsIgnoreCase("+971")) {
		 pattern = Pattern.compile(phoneNumberValUAE);
		}
		else if (countryCode.equalsIgnoreCase("+62")) {
		 pattern = Pattern.compile(phoneNumberValIndonesia);
		}
		else if (countryCode.equalsIgnoreCase("+60")) {
		 pattern = Pattern.compile(phoneNumberValMalaysia);
		}
		Matcher matcher = pattern.matcher(number);
		if (!matcher.matches()) {
			return false;
		}
		return true;
	}
	
	/*
	 * This method will validate the email address.
	 * 
	 * @param expression : Email Address
	 * 
	 * @return boolean : true if the expression matches the pattern w@w.w format
	 */
	public static boolean validateEmail(String expression) {
		String REGEXP_EMAIL = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.[a-zA-Z]{1,20}+";
		Pattern p = Pattern.compile(REGEXP_EMAIL);
		Matcher m = p.matcher(expression);
		return m.matches();
	}

	public static String phonenumbercode(String code){
		String countrycode = "";
		try {
			countrycode="";
			if (code==null) {
				countrycode="";
			}
			if (code.equalsIgnoreCase("+91")) {
				countrycode=code+"-INDIA";
			}
			else if (code.equalsIgnoreCase("+971")) {
				countrycode=code+"-UAE";
			}
			else if (code.equalsIgnoreCase("+60")) {
				countrycode=code+"-MALAYSIA";
			}
			else if (code.equalsIgnoreCase("+62")) {
				countrycode=code+"-INDONESIA";
			}
			else{
				countrycode="";
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return countrycode;
	}
	
	
	/*
	 * public static boolean validateTax(String tax) { Pattern pattern =
	 * Pattern.compile("\\d{20}"); Matcher matcher = pattern.matcher(tax); if
	 * (!matcher.matches()) { return false; } return true; }
	 */
	/*
	 * public static boolean validateFax(String fax) { Pattern pattern =
	 * Pattern.compile("\\d{10}"); Matcher matcher = pattern.matcher(fax); if
	 * (!matcher.matches()) { return false; } return true; }
	 */
}
