package com.stumosys.shared;

import java.io.Serializable;

import javax.persistence.*;

import com.stumosys.domain.LoginAccess;


/**
 * The persistent class for the enquiryform_login database table.
 * 
 */
@Entity
@Table(name="enquiryform_login")
@NamedQuery(name="Enquiryform.findAll", query="SELECT b FROM Enquiryform b")
public class Enquiryform implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="enquiryform_id")
	private int enquiryformId;
    
	private String organizationname;
	private String email;
    private String country;
    private String details;
    private String phonenumber;
    private String contact;
   
	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public Enquiryform() {
	}

	public Enquiryform(LoginAccess loginaccess) {
		this.country=loginaccess.getCountryName();
		this.phonenumber=loginaccess.getOrgPhone();
		this.organizationname=loginaccess.getOrganizationName();
		this.email=loginaccess.getOrgEmail();
		this.details=loginaccess.getOrgDetail();
		this.contact=loginaccess.getContact();
	}

    
    public int getEnquiryformId() {
		return enquiryformId;
	}

	public void setEnquiryformId(int enquiryformId) {
		this.enquiryformId = enquiryformId;
	}

	public String getOrganizationname() {
    	return organizationname;
    }

    public void setOrganizationname(String organizationname) {
	   this.organizationname = organizationname;
    }

    public String getEmail() {
	   return email;
    }

    public void setEmail(String email) {
	   this.email = email;
    }
    
	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
    public String getDetails() {
	   return details;
    }

    public void setDetails(String details) {
	   this.details = details;
    }

    public String getPhonenumber() {
	   return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
	   this.phonenumber = phonenumber;
    }
  
}