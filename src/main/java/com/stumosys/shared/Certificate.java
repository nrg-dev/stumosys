package com.stumosys.shared;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


/**
 * The persistent class for the certificate database table.
 * 
 */
@Entity
@NamedQuery(name="Certificate.findAll", query="SELECT c FROM Certificate c")
public class Certificate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="certificate_id")
	private int certificateId;

	private String emisno;
	
	private String schoolID;
	
	@Temporal(TemporalType.DATE)
	private Date creaateDate;
	
	private String serialNo;

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getSchoolID() {
		return schoolID;
	}

	public void setSchoolID(String schoolID) {
		this.schoolID = schoolID;
	}

	public Date getCreaateDate() {
		return creaateDate;
	}

	public void setCreaateDate(Date creaateDate) {
		this.creaateDate = creaateDate;
	}

	public Certificate() {
	}

	public int getCertificateId() {
		return this.certificateId;
	}

	public void setCertificateId(int certificateId) {
		this.certificateId = certificateId;
	}

	public String getEmisno() {
		return this.emisno;
	}

	public void setEmisno(String emisno) {
		this.emisno = emisno;
	}

}