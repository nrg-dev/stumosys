package com.stumosys.managedBean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.stumosys.controler.SmsController;
import com.stumosys.domain.BookSaleDataBean;
import com.stumosys.util.CommonValidate;

@ManagedBean(name = "addBookSaleMB")
@RequestScoped
public class AddBookSaleMB {
	BookSaleDataBean bookSaleDataBean = new BookSaleDataBean();
	private boolean Boxflag = false;
	private boolean Boxflag1 = false;
	final static Logger logger = Logger.getLogger(AddBookSaleMB.class);
	UploadedFile file;
	Date now = new Date();
	SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
	FacesContext context = FacesContext.getCurrentInstance();
	ResourceBundle paths = context.getApplication().evaluateExpressionGet(context, "#{paths}", ResourceBundle.class);
	private boolean bookflag=false;
	private boolean bookflag1=false;
	public boolean isBookflag() {
		return bookflag;
	}

	public void setBookflag(boolean bookflag) {
		this.bookflag = bookflag;
	}

	public boolean isBookflag1() {
		return bookflag1;
	}

	public void setBookflag1(boolean bookflag1) {
		this.bookflag1 = bookflag1;
	}

	public boolean isBoxflag1() {
		return Boxflag1;
	}

	public void setBoxflag1(boolean boxflag1) {
		Boxflag1 = boxflag1;
	}

	private boolean sflag = false;

	public boolean isSflag() {
		return sflag;
	}

	public void setSflag(boolean sflag) {
		this.sflag = sflag;
	}

	public boolean isBoxflag() {
		return Boxflag;
	}

	public void setBoxflag(boolean boxflag) {
		Boxflag = boxflag;
	}

	public BookSaleDataBean getBookSaleDataBean() {
		return bookSaleDataBean;
	}

	public void setBookSaleDataBean(BookSaleDataBean bookSaleDataBean) {
		this.bookSaleDataBean = bookSaleDataBean;
	}

	public String addBookSalePage() {
		logger.info("-----------Inside addBookSalePage method()----------------");
		setBoxflag(false);
		setBoxflag1(false);
		bookSaleDataBean.setBookName("");
		bookSaleDataBean.setBookQuantity("");
		bookSaleDataBean.setBookPrice("");
		bookSaleDataBean.setBookFile(null);		
		try {
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			int schoolid=Integer.parseInt(schoolID);
			String status = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("Role");
			if (personID != null) {
				//status = controller.getRoll(personID);
				logger.debug("-----status----" + status);
				if (status.equalsIgnoreCase("BookShop")) {
					if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
						bookflag=true;bookflag1=false;
					}
					else if(schoolid==35 || schoolid==36 || schoolid==37 || schoolid==38 || schoolid==39 || schoolid==40 || schoolid==41 || schoolid==42 || schoolid==43){
						bookflag1=true;bookflag=false;
					}	
					else{
						bookflag=true;bookflag1=false;
					}					 
					setSflag(true);
				}
				if (status.equalsIgnoreCase("Student") || status.equalsIgnoreCase("Teacher")
						|| status.equalsIgnoreCase("Parent") || status.equalsIgnoreCase("Admin")
						|| status.equalsIgnoreCase("Librarian")) {
					setSflag(false);
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "AddBookView";

	}

	public String submit() {
		logger.info("-----------Inside submit method()----------------");
		SmsController controller = null;
		String status = "";
		setBoxflag(false);
		setBoxflag1(false);
		String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
		int school_ID=Integer.parseInt(schoolID);
		try {
			if(school_ID== 1|| school_ID == 2 || school_ID== 3 || school_ID==4 || school_ID==5 || school_ID==6){
				if (validate(true)) {
					
				}
			}else if(school_ID==35|| school_ID == 36 || school_ID== 37 || school_ID==38 || school_ID==39 || school_ID==40 || school_ID==41 || school_ID==42 || school_ID==43){
				if (productvalidate(true)) {
					
				}
			}
			else{
				if (validate(true)) {
					
				}
			}
			if(validate(true) || productvalidate(true)){
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
				logger.debug("-------person id-------" + personID);
				if (personID != null) {
					if (bookSaleDataBean.getBookFile() != null) {
						String s = bookSaleDataBean.getBookFile().getContentType();
						logger.debug("file Type " + s);
						String type = s.substring(s.lastIndexOf("/") + 1);
						logger.debug(type);
						String fileName = bookSaleDataBean.getBookName();
						fileName.trim();
						copyFile(fileName, bookSaleDataBean.getBookFile().getInputstream(), type);

						String path = ft.format(now) + "/" + fileName + "." + type;
						bookSaleDataBean.setBookfilePath(path);
						logger.debug(bookSaleDataBean.getBookfilePath());
					}
					status = controller.insertAddBook(personID, bookSaleDataBean);
					logger.debug("--------status-----" + status);
					if (status.equalsIgnoreCase("Success")) {
						setBoxflag(true);
					} else if (status.equalsIgnoreCase("Failure")) {
						FacesContext fc = FacesContext.getCurrentInstance();
						String fieldName ="";
						if(school_ID== 1|| school_ID == 2 || school_ID== 3 || school_ID==4 || school_ID==5 || school_ID==6){
							 fieldName = CommonValidate.findComponentInRoot("addBook").getClientId(fc);
							 fc.addMessage(fieldName, new FacesMessage("Book Name is Already Exist."));
						}else if(school_ID== 35|| school_ID == 36 || school_ID== 37 || school_ID==38 || school_ID==39 || school_ID==40 || school_ID==41 || school_ID==42 || school_ID==43){
							 fieldName = CommonValidate.findComponentInRoot("productname").getClientId(fc);
							 fc.addMessage(fieldName, new FacesMessage("Product is Already Exist."));
						}
						else{
							fieldName = CommonValidate.findComponentInRoot("addBook").getClientId(fc);
							 fc.addMessage(fieldName, new FacesMessage("Book Name is Already Exist."));
						}

					}
					return "";
				}
			
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "";
	}

	private void copyFile(String fileName, InputStream inputstream, String type) {
		logger.info("-----------Inside copyFile method()----------------");
		try {

			// Create Directory
			File files = new File(paths.getString("sms.addbook.photo") + ft.format(now));
			if (!files.exists()) {
				files.mkdirs();
			} else {
				logger.debug("Alreday Found");
			}

			// write the inputStream to a FileOutputStream
			OutputStream out = new FileOutputStream(new File(files + paths.getString("path_context").toString() + fileName + "." + type));

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
			logger.warn("Inside Exception",e);
			logger.debug(e.getMessage());
		}

	}
	private boolean validate(boolean flag) {
		logger.info("-----------Inside validate method()----------------");
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (StringUtils.isEmpty(bookSaleDataBean.getBookName())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("addBook").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Book Name."));
			}
			valid = false;

		}else if(!StringUtils.isEmpty(bookSaleDataBean.getBookName())){
			if (!CommonValidate.validateName(bookSaleDataBean.getBookName())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("addBook").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Book Name."));
				}
				valid = false;
			}else if (bookSaleDataBean.getBookName().length() < 3) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("addBook").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Book Name Should be minimum 3 characters"));
				}
				valid = false;
			}
		}
		if (StringUtils.isEmpty(bookSaleDataBean.getBookQuantity())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("addQuantity").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Book Quantity."));
			}
			valid = false;

		}else if(!StringUtils.isEmpty(bookSaleDataBean.getBookQuantity())){
			if (!CommonValidate.validateNumberOnly(bookSaleDataBean.getBookQuantity())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("addQuantity").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Book Quantity."));
				}
				valid = false;
			}else if(Integer.parseInt(bookSaleDataBean.getBookQuantity())<=0){
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("addQuantity").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Book Quantity."));
				}
				valid = false;
			}
			
		}

		if (StringUtils.isEmpty(bookSaleDataBean.getBookPrice())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("addPrice").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Book Price."));
			}
			valid = false;

		}else if(!StringUtils.isEmpty(bookSaleDataBean.getBookPrice())){
			if (!CommonValidate.validateNumberOnly(bookSaleDataBean.getBookPrice())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("addPrice").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Book Price."));
			}
			valid = false;
			}else if(Integer.parseInt(bookSaleDataBean.getBookPrice())<=0){
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("addPrice").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Book Price."));
				}
				valid = false;
			}
		}
		if (bookSaleDataBean.getBookFile() == null) {
			if (flag) {

				fieldName = CommonValidate.findComponentInRoot("bkPhoto").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Choose the Photo"));
			}
			valid = false;
		}


		return valid;
	}
	
	private boolean productvalidate(boolean flag) {
		logger.info("-----------Inside productvalidate method()----------------");
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();

		if (StringUtils.isEmpty(bookSaleDataBean.getBookName())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("productname").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Product Name."));
			}
			valid = false;

		}else if(!StringUtils.isEmpty(bookSaleDataBean.getBookName())){
			if (!CommonValidate.validateName(bookSaleDataBean.getBookName())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("productname").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Product Name."));
				}
				valid = false;
			}else if (bookSaleDataBean.getBookName().length() < 3) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("productname").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Product Name Should be minimum 3 characters"));
				}
				valid = false;
			}
		}
		if (StringUtils.isEmpty(bookSaleDataBean.getBookPrice())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("productprice").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Product Price."));
			}
			valid = false;

		}else if(!StringUtils.isEmpty(bookSaleDataBean.getBookPrice())){
			if (!CommonValidate.validateNumberOnly(bookSaleDataBean.getBookPrice())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("productprice").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Product Price."));
			}
			valid = false;
			}else if(Integer.parseInt(bookSaleDataBean.getBookPrice())<=0){
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("productprice").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Product Price."));
				}
				valid = false;
			}
		}
		if (bookSaleDataBean.getBookFile() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("prodphoto").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Choose the Photo"));
			}
			valid = false;
		}

		return valid;
	}
	

	public String reset() {
		logger.info("-----------Inside reset method()----------------");
		bookSaleDataBean.setBookName("");
		bookSaleDataBean.setBookQuantity("");
		bookSaleDataBean.setBookPrice("");
		return "";
	}

	public String returnToHome() {
		logger.info("-----------Inside returnToHome method()----------------");
		bookSaleDataBean.setBookName("");
		bookSaleDataBean.setBookQuantity("");
		bookSaleDataBean.setBookPrice("");
		setBoxflag(false);
		setBoxflag1(false);
		try {
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				return "bookshopdashboard3";
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "bookshopdashboard3";
	}
	
	public String dummyAction(FileUploadEvent event) throws IOException {
		logger.info("-----------Inside dummyAction method()----------------");
		this.file = event.getFile();
		bookSaleDataBean.setBookFile(event.getFile());
		FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, message);
		return "";
	}
}
