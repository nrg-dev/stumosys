package com.stumosys.managedBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.Serializable;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean(name = "NavigationMB")
@RequestScoped
public class NavigationBean implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	private String pageName = "/pages/xhtml/noticeboard";
	private String filename="C://Photos/Header/ReportCard.pdf?pfdrid_c=true";
	 private StreamedContent streamedContent;
	 private StreamedContent stream;
	 final static Logger logger = Logger.getLogger(NavigationBean.class);
	 private String email;
	 private String email1;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the pageName
	 */
	public String getPageName() {
		return pageName;
	}

	/**
	 * @param pageName
	 *            the pageName to set
	 */
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public NavigationBean() throws FileNotFoundException {
		FileInputStream fis;
		fis = new FileInputStream(new File("C://Photos/Header/nrg.png"));
		 streamedContent = new DefaultStreamedContent(fis, "image/jpg", "downloaded_optimus.png");
		 
	FileInputStream fis1;
			fis1 = new FileInputStream(new File("C://Photos/Header/ReportCard.pdf"));
			 stream = new DefaultStreamedContent(fis1, "application/pdf");
	}

	public void doNav() {
		String str = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("test");
		logger.debug("Str" + str);
		this.pageName = str;

	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public StreamedContent getStreamedContent() {
		return streamedContent;
	}

	public void setStreamedContent(StreamedContent streamedContent) {
		this.streamedContent = streamedContent;
	}

	public StreamedContent file() throws FileNotFoundException {
		
		FileInputStream fis;
		fis = new FileInputStream(new File("C://Photos/Header/apitest.pdf"));
		 streamedContent = new DefaultStreamedContent(fis, "application/pdf", "API.pdf");
		 
		  return streamedContent;
		}

	public StreamedContent getStream(){
		return stream;
	}

	public void setStream(StreamedContent stream) {
		this.stream = stream;
	}
	
	public String name1() {
		logger.debug("Calling 123");
		FacesContext fc = FacesContext.getCurrentInstance();
		if(email.equalsIgnoreCase("")){
			 logger.debug("Enside");
			fc.addMessage("myform:t", new FacesMessage("Error"));
		}else{
			String patent="\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.[a-zA-Z]{1,20}+";
			boolean b=Pattern.matches(patent,email);
			logger.debug(b);
		}
		if(email1.equalsIgnoreCase("")){
			 logger.debug("Enside");
			fc.addMessage("myform:t1", new FacesMessage("Error1"));
		}else{
			logger.debug("OutSide");
		}
		return "";
		
	}

	public String getEmail1() {
		return email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}

}
