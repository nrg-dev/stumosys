package com.stumosys.managedBean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.UploadedFile;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.stumosys.controler.SmsController;
import com.stumosys.domain.LibraryDataBean;
import com.stumosys.shared.Library;
import com.stumosys.shared.Student;
import com.stumosys.util.CommonValidate;
import com.stumosys.util.MailSendJDBC;

@ManagedBean(name = "libraryMB")
@RequestScoped
public class LibraryMB {

	LibraryDataBean libraryDataBean = new LibraryDataBean();
	private static Logger logger = Logger.getLogger(LibraryMB.class);
	FacesContext context = FacesContext.getCurrentInstance();
	ResourceBundle text = context.getApplication().evaluateExpressionGet(context, "#{text}", ResourceBundle.class);
	ResourceBundle paths = context.getApplication().evaluateExpressionGet(context, "#{paths}", ResourceBundle.class);
	private boolean boxflag = false;
	private UploadedFile file;
	Date now = new Date();
	SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
	private List<String> classSectionList = null;
	private List<String> studentIDList = null;
	private DualListModel<String> availableBookList;
	private boolean renderFlag = false;
	private List<String> categoryList = null;
	private boolean boxDialogflag = false;
	private List<String> tempAvailableTarget = null;
	private List<String> langCategoryList=null;
	private List<LibraryDataBean> borrowerdetails=null;
	private List<Library> booklist=null;
	private List<Library> authername=null;
	public String flag1="none";
	public String flag2="none";
	 private List<LibraryDataBean> studentdtail=null;
	 public String addbookflag="none";
	 public String addbookflag1="none";
	public String getAddbookflag() {
		return addbookflag;
	}

	public void setAddbookflag(String addbookflag) {
		this.addbookflag = addbookflag;
	}

	public String getAddbookflag1() {
		return addbookflag1;
	}

	public void setAddbookflag1(String addbookflag1) {
		this.addbookflag1 = addbookflag1;
	}

	public List<LibraryDataBean> getStudentdtail() {
		return studentdtail;
	}

	public void setStudentdtail(List<LibraryDataBean> studentdtail) {
		this.studentdtail = studentdtail;
	}

	public String getFlag1() {
		return flag1;
	}

	public void setFlag1(String flag1) {
		this.flag1 = flag1;
	}

	public String getFlag2() {
		return flag2;
	}

	public void setFlag2(String flag2) {
		this.flag2 = flag2;
	}

	public List<Library> getBooklist() {
		return booklist;
	}

	public void setBooklist(List<Library> booklist) {
		this.booklist = booklist;
	}

	public List<Library> getAuthername() {
		return authername;
	}

	public void setAuthername(List<Library> authername) {
		this.authername = authername;
	}

	public List<LibraryDataBean> getBorrowerdetails() {
		return borrowerdetails;
	}

	public void setBorrowerdetails(List<LibraryDataBean> borrowerdetails) {
		this.borrowerdetails = borrowerdetails;
	}

	public List<String> getTempAvailableTarget() {
		return tempAvailableTarget;
	}

	public void setTempAvailableTarget(List<String> tempAvailableTarget) {
		this.tempAvailableTarget = tempAvailableTarget;
	}

	/**
	 * @return the renderFlag
	 */
	public boolean isRenderFlag() {
		return renderFlag;
	}

	/**
	 * @param renderFlag
	 *            the renderFlag to set
	 */
	public void setRenderFlag(boolean renderFlag) {
		this.renderFlag = renderFlag;
	}

	/**
	 * @return the categoryList
	 */
	public List<String> getCategoryList() {
		return categoryList;
	}

	/**
	 * @param categoryList
	 *            the categoryList to set
	 */
	public void setCategoryList(List<String> categoryList) {
		this.categoryList = categoryList;
	}

	/**
	 * @return the availableBookList
	 */
	public DualListModel<String> getAvailableBookList() {
		return availableBookList;
	}

	/**
	 * @param availableBookList
	 *            the availableBookList to set
	 */
	public void setAvailableBookList(DualListModel<String> availableBookList) {
		this.availableBookList = availableBookList;
	}

	/**
	 * @return the classSectionList
	 */
	public List<String> getClassSectionList() {
		return classSectionList;
	}

	/**
	 * @param classSectionList
	 *            the classSectionList to set
	 */
	public void setClassSectionList(List<String> classSectionList) {
		this.classSectionList = classSectionList;
	}

	/**
	 * @return the studentIDList
	 */
	public List<String> getStudentIDList() {
		return studentIDList;
	}

	/**
	 * @param studentIDList
	 *            the studentIDList to set
	 */
	public void setStudentIDList(List<String> studentIDList) {
		this.studentIDList = studentIDList;
	}

	/**
	 * @return the libraryDataBean
	 */
	public LibraryDataBean getLibraryDataBean() {
		return libraryDataBean;
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
	 * @param libraryDataBean
	 *            the libraryDataBean to set
	 */
	public void setLibraryDataBean(LibraryDataBean libraryDataBean) {
		this.libraryDataBean = libraryDataBean;
	}

	public String loadAddBook() 
	{
		logger.info("-----------Inside loadAddBook method()----------------");
		   String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
		   int schoolid=Integer.parseInt(schoolID);
	    logger.debug("----------sdsd-----");
		libraryDataBean.setBookName("");
		libraryDataBean.setAuthorName("");
		libraryDataBean.setBookeditions("");
		libraryDataBean.setBookCategory("");
		libraryDataBean.setBookPages(null);
		libraryDataBean.setBookDescription("");
		libraryDataBean.setBookPrice("");
		libraryDataBean.setBookFile(null);
		libraryDataBean.setBookDueType("");
		libraryDataBean.setBookFee("");
		setBoxflag(false);
		String langnames=text.getString("international.lang");
		langCategoryList= new ArrayList<String>(Arrays.asList(langnames.split(",")));
		 if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6)
		 {
			 setAddbookflag("1");
			 setAddbookflag1("none");
		 }
		 else if(schoolid==35 || schoolid==36 || schoolid==37 || schoolid==38 || schoolid==39 || schoolid==40 || schoolid==41 || schoolid==42 || schoolid==43){
			 setAddbookflag("none");
			 setAddbookflag1("1");
		 }
		 else
		 {
			 setAddbookflag("1");
			 setAddbookflag1("none");
		 }
		 return "Addbook";	
	}

	private boolean validate(boolean flag) {
		logger.info("-----------Inside validate method()----------------");
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();

		if (StringUtils.isEmpty(libraryDataBean.getBookName())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("addBookName").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Book Name."));
			}
			valid = false;

		} else if (!StringUtils.isEmpty(libraryDataBean.getBookName())) {
			if (!CommonValidate.validateName(libraryDataBean.getBookName())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("addBookName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Book Name."));
				}
				valid = false;
			} else if (libraryDataBean.getBookName().length() < 3) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("addBookName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Book Name."));
				}
				valid = false;
			}
		}
		if (StringUtils.isEmpty(libraryDataBean.getAuthorName())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("addAuthorName").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Author Name."));
			}
			valid = false;
		} else if (!StringUtils.isEmpty(libraryDataBean.getAuthorName())) {
			if (!CommonValidate.validateName(libraryDataBean.getAuthorName())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("addAuthorName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Author Name."));
				}
				valid = false;
			} else if (libraryDataBean.getAuthorName().length() < 3) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("addAuthorName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Author Name."));
				}
				valid = false;
			}
		}

		if (StringUtils.isEmpty(libraryDataBean.getBookeditions())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("addBookeditions").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Book Editions."));
			}
			valid = false;
		}

		if (StringUtils.isEmpty(libraryDataBean.getBookPrice())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("addBookPrice").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Book Price."));
			}
			valid = false;
		} else if (!StringUtils.isEmpty(libraryDataBean.getBookPrice())) {
			if (!CommonValidate.validateNumber(libraryDataBean.getBookPrice())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("addBookPrice").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the Book Price."));
				}
				valid = false;
			}
		}
		
		if (StringUtils.isEmpty(libraryDataBean.getBookQty())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("addBookQuantity").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Book Quantity."));
			}
			valid = false;
		} else if (!StringUtils.isEmpty(libraryDataBean.getBookQty())) {
			if (!CommonValidate.validateNumberOnly(libraryDataBean.getBookQty())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("addBookQuantity").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Book Quantity."));
				}
				valid = false;
			}else if(Integer.parseInt(libraryDataBean.getBookQty()) <= 0){
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("addBookQuantity").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Book Quantity."));
				}
				valid = false;
			}
		}


		if (libraryDataBean.getBookFile() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("bookPhoto").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Book Photo."));
			}
			valid = false;
		}

		if (StringUtils.isEmpty(libraryDataBean.getBookDueType())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("addBookdue").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Choose the Book Editions."));
			}
			valid = false;
		}

		if (StringUtils.isEmpty(libraryDataBean.getBookCategory())) {
			{
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("addBookCategory").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Select the Book Category."));
				}
				valid = false;
			}
		}

		if (StringUtils.isEmpty(libraryDataBean.getBookPages())) {
			if (flag) {

				fieldName = CommonValidate.findComponentInRoot("addBookPages").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Book Pages."));
			}
			valid = false;
		} else if (!StringUtils.isEmpty(libraryDataBean.getBookPages())) {
			if (!CommonValidate.validateNumber(libraryDataBean.getBookPages())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("addBookPages").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid BookPages."));
				}
				valid = false;
			}
		}
		if (StringUtils.isEmpty(libraryDataBean.getBookDescription())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("addBookDescription").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Book Descripition."));
			}
			valid = false;

		} else if (!StringUtils.isEmpty(libraryDataBean.getBookDescription())) {
			if (libraryDataBean.getBookDescription().length() < 3) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("addBookDescription").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Book Descripition."));
				}
				valid = false;
			}
		}
		if (StringUtils.isEmpty(libraryDataBean.getBookFee())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("addBookfee").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Book Penalty Fee."));
			}
			valid = false;
		} else if (!StringUtils.isEmpty(libraryDataBean.getBookFee())) {
			if (!CommonValidate.validateNumber(libraryDataBean.getBookFee())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("addBookfee").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Book Penalty Fee."));
				}
				valid = false;
			}
		}
		return valid;
	}
	
	
	private boolean validate2(boolean flag) {
		logger.info("-----------Inside validate2 method()----------------");
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();

		if (StringUtils.isEmpty(libraryDataBean.getBookName())) 
		{
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("addBookName1").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Book Name."));
			}
			valid = false;

		} 
		
		else if (!StringUtils.isEmpty(libraryDataBean.getBookName())) {
			if (!CommonValidate.validateName(libraryDataBean.getBookName())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("addBookName1").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Book Name."));
				}
				valid = false;
			} else if (libraryDataBean.getBookName().length() < 3) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("addBookName1").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Book Name."));
				}
				valid = false;
			}
		}
		if (StringUtils.isEmpty(libraryDataBean.getAuthorName())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("addAuthorName1").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Author Name."));
			}
			valid = false;
		} else if (!StringUtils.isEmpty(libraryDataBean.getAuthorName())) {
			if (!CommonValidate.validateName(libraryDataBean.getAuthorName())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("addAuthorName1").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Author Name."));
				}
				valid = false;
			} else if (libraryDataBean.getAuthorName().length() < 3) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("addAuthorName1").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Author Name."));
				}
				valid = false;
			}
		}

		if (StringUtils.isEmpty(libraryDataBean.getBookPrice())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("addBookPrice1").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Book Price."));
			}
			valid = false;
		} else if (!StringUtils.isEmpty(libraryDataBean.getBookPrice())) {
			if (!CommonValidate.validateNumber(libraryDataBean.getBookPrice())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("addBookPrice1").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the Book Price."));
				}
				valid = false;
			}
		}


		if (libraryDataBean.getBookFile() == null) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("bookPhoto1").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Book Photo."));
			}
			valid = false;
		}

		if (StringUtils.isEmpty(libraryDataBean.getBookFee())) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("addBookfee1").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Book Penalty Fee."));
			}
			valid = false;
		} else if (!StringUtils.isEmpty(libraryDataBean.getBookFee())) {
			if (!CommonValidate.validateNumber(libraryDataBean.getBookFee())) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("addBookfee1").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Book Penalty Fee."));
				}
				valid = false;
			}
		}
		return valid;
	}

	public String submit() {
		logger.info("-----------Inside submit method()----------------");

		setBoxflag(false);
		try {
			logger.debug("---------- submit Method Calling-----" + libraryDataBean.getBookCategory());
			 String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			   int schoolid=Integer.parseInt(schoolID);
			   if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6)
			   {
			if (validate(true)) {

				String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
						.get("LogID");
				if (personID != null) {
					RequestContext reqcontext1 = RequestContext.getCurrentInstance();
					reqcontext1.execute("libBookeBlk()");
                	}

			}
			   }
			   else
			   {
				   if (validate2(true)) {

						String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
								.get("LogID");
						if (personID != null) {
							RequestContext reqcontext1 = RequestContext.getCurrentInstance();
							reqcontext1.execute("libBookeBlk()");

						}

					}   
			   }
		} catch (Exception e) {
			logger.debug("---------- submit Method Exception Calling-----");

			logger.warn("Exception -->"+e.getMessage());
			return "";
		}
		return "";

	}
		public String submitConfirm() {
			logger.info("-----------Inside submitConfirm method()----------------");
		SmsController controller = null;
		setBoxflag(false);
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			   logger.debug("---------- submit Method Callinggg-----" + libraryDataBean.getBookCategory());
				
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
						.get("LogID");
				String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
						.get("schoolID");
				int schoolid=Integer.parseInt(schoolID);
				if (personID != null) {
					logger.debug(libraryDataBean.getBookFile().getContentType());
					String s = libraryDataBean.getBookFile().getContentType();
					logger.debug("file Type " + s);
					String type = s.substring(s.lastIndexOf("/") + 1);
					logger.debug("check type"+type);
					String fileName = libraryDataBean.getBookName() + libraryDataBean.getAuthorName()
							+ libraryDataBean.getBookeditions();
					fileName.trim();
					logger.debug("check field name"+fileName);
					copyFile(fileName, libraryDataBean.getBookFile().getInputstream(), type);

					String path = ft.format(now) + "/" + fileName + "." + type;
					libraryDataBean.setParfilePath(path);
					logger.debug(libraryDataBean.getParfilePath());
					String status = controller.insertLibrary(personID, libraryDataBean);
					if (status.equalsIgnoreCase("Success")) {
						RequestContext reqcontext = RequestContext.getCurrentInstance();
						reqcontext.execute("PF('libraryBookblockUI').hide();");
						reqcontext.execute("PF('bookDialog').show();");
						setBoxflag(true);
					} else if (status.equalsIgnoreCase("Exsist")) {
						RequestContext reqcontext = RequestContext.getCurrentInstance();
						reqcontext.execute("PF('libraryBookblockUI').hide();");
						  if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6)
						   {
							  fieldName = CommonValidate.findComponentInRoot("addBookName").getClientId(fc);
								fc.addMessage(fieldName, new FacesMessage("Already Exsist"));	
						   }else{
							   fieldName = CommonValidate.findComponentInRoot("addBookName1").getClientId(fc);
								fc.addMessage(fieldName, new FacesMessage("Already Exsist"));	
						   }
						setBoxflag(false);
					} 
					else {
						RequestContext reqcontext = RequestContext.getCurrentInstance();
						reqcontext.execute("PF('libraryBookblockUI').hide();");
						reqcontext.execute("PF('bookDialog').hide();");
					}
				}

		
		} catch (Exception e) {
			logger.debug("---------- submit Method Exception Calling-----");

			logger.warn("Exception -->"+e.getMessage());
			return "";
		}
		return "";

	}

	private void copyFile(String fileName, InputStream inputstream, String type) {
		logger.info("-----------Inside copyFile method()----------------");
		try {

			// Create Directory
			File files = new File(paths.getString("sms.library.photo") + ft.format(now));
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
			logger.debug(e.getMessage());
		}

	}

	public String reset() {
		logger.info("-----------Inside reset method()----------------");
		libraryDataBean.setBookName("");
		libraryDataBean.setAuthorName("");
		libraryDataBean.setBookeditions("");
		libraryDataBean.setBookCategory("");
		libraryDataBean.setBookPages(null);
		libraryDataBean.setBookDescription("");
		libraryDataBean.setBookPrice("");
		libraryDataBean.setBookFile(null);
		setBoxflag(false);
		libraryDataBean.setBookDueType("");
		libraryDataBean.setBookFee("");
		return "";

	}

	public String returnToHome() {
		logger.info("-----------Inside returnToHome method()----------------");
		try {
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (personID != null) {
				return "SuccessHome4";
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "";

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

	public String dummyAction(FileUploadEvent event) throws IOException {
		logger.info("-----------Inside dummyAction method()----------------");
		this.setFile(event.getFile());
		libraryDataBean.setBookFile(event.getFile());
		FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, message);

		return "";
	}

	public String bookBorrowerListPage() {
		logger.info("-----------Inside bookBorrowerListPage method()----------------");
		classSectionList = null;
		studentIDList = null;
		SmsController controller = null;
		setBoxDialogflag(false);
		libraryDataBean.setLibStuClass("");
		libraryDataBean.setLibStudID("");
		libraryDataBean.setBookCategory("");
		libraryDataBean.setLibStudID("");
	    libraryDataBean.setBorroweDate(null);
		libraryDataBean.setBookName("");
		libraryDataBean.setStudentname("");
		libraryDataBean.setLibStuClass("");
		libraryDataBean.setStudentid("");
		tempAvailableTarget = null;
		setFlag1("none");
		setFlag2("none");
		setRenderFlag(false);
		try {
			classSectionList = new ArrayList<String>();
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			   int schoolid=Integer.parseInt(schoolID);
			if (personID != null) 
			{
				if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6)
				{
					classSectionList = controller.getClassSection(personID);
					setFlag1("1");
					setFlag2("none");
				}
				else
				{
					setStudentlist(controller.studentidlist(personID));
					setFlag1("none");
					setFlag2("1");
				}
				return "borrowerPage";
				
				
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			libraryDataBean.setLibStuClass("");
			libraryDataBean.setLibStudID("");
			setBoxDialogflag(false);
		}
		return "";
	}

	public void classChange(ValueChangeEvent event) {
		logger.info("-----------Inside classChange method()----------------");
		SmsController controller = null;
		setBoxDialogflag(false);
		studentIDList = null;
		setRenderFlag(false);
		categoryList = null;
		availableBookList = null;
		libraryDataBean.setLibStudID("");
		libraryDataBean.setBookCategory("");
		tempAvailableTarget = null;
		try {
			studentIDList = new ArrayList<String>();
			logger.debug("classChange Calling");
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			String name = event.getNewValue().toString();
			logger.debug(name);
			if (personID != null && name != null) {
				studentIDList = controller.getStudentRollNumber(personID, name);
				logger.debug(studentIDList);
			}

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			setRenderFlag(false);
			availableBookList = null;
			setBoxDialogflag(false);
		}
	}	
	
	public void CatogoryChange(ValueChangeEvent event) {
		logger.info("-----------Inside CatogoryChange method()----------------");
		SmsController controller = null;
		List<LibraryDataBean> resultList = null;
		setRenderFlag(false);
		List<String> availableBook = null;
		List<String> borrwoedBook = null;
		setBoxDialogflag(false);
		tempAvailableTarget = null;
		try {
			resultList = new ArrayList<LibraryDataBean>();
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (personID != null) {
				String categoryname = event.getNewValue().toString();
				availableBook = new ArrayList<String>();
				borrwoedBook = new ArrayList<String>();
				resultList = controller.getBookListView1(personID, categoryname);
				if (resultList.size() > 0) {
					for (int i = 0; i < resultList.size(); i++) {
						logger.debug("----" + resultList.get(i).getAuthorName());
						String book = resultList.get(i).getBookName() + "~" + resultList.get(i).getAuthorName() + "/"
								+ resultList.get(i).getBookeditions();
						availableBook.add(book);
					}
				}
				if (libraryDataBean.getLibStudID() != null || !libraryDataBean.getLibStudID().equalsIgnoreCase("")) {
					borrwoedBook = controller.getBorrowedBook(personID, categoryname, libraryDataBean.getLibStudID());
					logger.debug("Book Size" + borrwoedBook.size());

				}
				if (borrwoedBook.size() > 0) {
					for (int i = 0; i < borrwoedBook.size(); i++) {
						for (int j = 0; j < availableBook.size(); j++) {
							String temp1 = borrwoedBook.get(i);
							String temp2 = availableBook.get(j);
							if (temp2.equalsIgnoreCase(temp1)) {
								availableBook.remove(temp2);
							}
						}
					}
					if (borrwoedBook.size() == 1) {
						tempAvailableTarget = new ArrayList<String>();
						tempAvailableTarget.add(borrwoedBook.get(0));

					}
				}
				availableBookList = new DualListModel<String>(availableBook, borrwoedBook);
				setRenderFlag(true);
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			setRenderFlag(false);
			setBoxDialogflag(false);
		}
	}

	public void studentIDChange(ValueChangeEvent event) {
		logger.info("-----------Inside studentIDChange method()----------------");
		String s = event.getNewValue().toString();
		setBoxDialogflag(false);
		categoryList = null;
		categoryList = new ArrayList<String>();
		setRenderFlag(false);
		availableBookList = null;
		libraryDataBean.setBookCategory("");
		tempAvailableTarget = null;
		try {
			if (s.equalsIgnoreCase("SS")) {
				logger.debug("Inside.......");
				categoryList = null;
			} else {
				String langnames=text.getString("international.lang");
				categoryList=new ArrayList<String>(Arrays.asList(langnames.split(",")));
			}

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			setRenderFlag(false);
			setBoxDialogflag(false);
			availableBookList = null;
		}
	}

	public String insertBorrowed() {
		logger.info("-----------Inside insertBorrowed method()----------------");
		SmsController controller = null;
		setBoxflag(false);
		setBoxDialogflag(false);
		try {
			if (validate1(true)) {
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
						.get("LogID");
				if (personID != null) {
					
					libraryDataBean.setTargetList(availableBookList.getTarget());
					String status = controller.insertBookBorrowed(personID, libraryDataBean);
					if (status.equalsIgnoreCase("Success")) {
						setBoxDialogflag(true);
					} else {
						setBoxDialogflag(false);

					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "";

	}

	private boolean validate1(boolean flag) {
		logger.info("-----------Inside validate1 method()----------------");
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();

		if (libraryDataBean.getLibStuClass().equalsIgnoreCase("Choose")) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("borrowerClassID").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Choose the Class."));
			}
			valid = false;

		}
		if (libraryDataBean.getLibStudID().equalsIgnoreCase("SS")) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("borrowerStudentID").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Choose the Student ID."));
			}
			valid = false;

		}
		if (libraryDataBean.getBookCategory().equalsIgnoreCase("")) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("borrowerCategory").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Choose the Category."));
			}
			valid = false;

		}
		
		return valid;
	}

	/**
	 * @return the boxDialogflag
	 */
	public boolean isBoxDialogflag() {
		return boxDialogflag;
	}

	/**
	 * @param boxDialogflag
	 *            the boxDialogflag to set
	 */
	public void setBoxDialogflag(boolean boxDialogflag) {
		this.boxDialogflag = boxDialogflag;
	}

	public List<String> getLangCategoryList() {
		return langCategoryList;
	}

	public void setLangCategoryList(List<String> langCategoryList) {
		this.langCategoryList = langCategoryList;
	}
	
	public String returnBookCall(){
		logger.info("-----------Inside returnBookCall method()----------------");
		setBoxflag(false);
		SmsController controller = null;
		try{
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				borrowerdetails=controller.getborrowerDetails(personID,libraryDataBean);
			}
		}catch(Exception e){
			logger.warn(" exception - "+e);
		}
		return "returnBook";
	}
	
	public String returnBook(){
		logger.info("-----------Inside returnBook method()----------------");
		SmsController controller = null;
		try{
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				String status=controller.toReturnBook(personID,libraryDataBean);
				if(status.equalsIgnoreCase("Success")){
					setBoxflag(true);
				}
			}
		}catch(Exception e){
			logger.warn(" exception - "+e);
		}
		return "";
	}
	
	public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
		logger.info("-----------Inside preProcessPDF method()----------------");
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
	
	public String loadstockin() 
	 {
	logger.info("-----------Inside loadstockin method()----------------");
	  SmsController controller = null;
	  try
	  {
	   String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
	     .get("LogID");
	   if(personID!=null)
	   {
	  
	   booklist=new ArrayList<Library>();
	   libraryDataBean.setBookName("");
	   libraryDataBean.setAuthorName("");
	   ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
	   controller = (SmsController) ctx.getBean("controller");
    setBooklist(controller.booknamelist(personID));
    logger.debug("check book list size"+getBooklist().size());
    libraryDataBean.setBookName(booklist.get(0).getBookName());
	   
	  }
	  }
	  catch(Exception e)
	  {
	   
	  }

	return "stockin";
	}
	
	public String f1="none";
	
	public String getF1() {
		return f1;
	}

	public void setF1(String f1) {
		this.f1 = f1;
	}
	  public void onRowCancel(RowEditEvent event) {
	        
	    }

	public void addquantitybook(RowEditEvent event)
	{
		logger.info("-----------Inside addquantitybook method()----------------");		
		SmsController controller = null;
		String status="";
		//String fieldName;
   		FacesContext fc = null;//FacesContext.getCurrentInstance();
		try
		{
			fc = FacesContext.getCurrentInstance();
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");

		          if(personID!=null)
		          {
		        	  String quentity=((Library) event.getObject()).getQuantity().toString();
		        	  if (!CommonValidate.validateNumber(quentity))
		        	  {
		        		 FacesMessage msg = new FacesMessage("Please Enter the valid Quantity");
		  		        FacesContext.getCurrentInstance().addMessage(null, msg);
		  				}
		        	  else
		        	  {
				   	  String bookname=((Library) event.getObject()).getBookName().toString();
	  				  String authername=((Library) event.getObject()).getAuthorName().toString();
	  				 logger.debug("quantity"+quentity);
	  				String price=((Library) event.getObject()).getPrice().toString();
	  		         libraryDataBean.setAddQuentity(quentity);
	  		         libraryDataBean.setAuthorName(authername);
	  		         libraryDataBean.setBookName(bookname);
	  		         libraryDataBean.setBookPrice(price);
	  		         ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
	  				controller = (SmsController) ctx.getBean("controller");
	  				status=controller.stockinbookQuentity(libraryDataBean,personID);
	  				
	  				if(status.equalsIgnoreCase("success"))
	  				{
	  					setBooklist(controller.booknamelist(personID));
	  					RequestContext r=RequestContext.getCurrentInstance();
	  		  			r.execute("PF('bookedit').show()");	
	  				}
		          }
		          }
	  		   			
		}
		catch(Exception ee)
		{
			logger.warn(" exception - "+ee);
		}
		finally
		{
			if(fc!=null){
				fc=null;
			}
		}
	}
	List<Student> student=null;
	public List<Student> getStudent() {
		return student;
	}

	public void setStudent(List<Student> student) {
		this.student = student;
	}
public List<String> studentlist=null;
	public List<String> getStudentlist() {
	return studentlist;
}

public void setStudentlist(List<String> studentlist) {
	this.studentlist = studentlist;
}
public LibraryMB() {
	
}

	public String studentnamechange(ValueChangeEvent event) {
		logger.info("-----------Inside studentnamechange method()----------------");
		SmsController controller = null;
		String sturollno = event.getNewValue().toString();
		try {
			studentdtail = new ArrayList<LibraryDataBean>();
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			studentdtail = controller.stuinfo(personID, sturollno);
			if (studentdtail.size() > 0) {
				libraryDataBean.setStudentname(studentdtail.get(0).getStudentname());
				libraryDataBean.setStudentid(studentdtail.get(0).getStudentid());
				libraryDataBean.setLibStuClass(studentdtail.get(0).getLibStuClass());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
public String borrowerbookinsert()
{
	logger.info("-----------Inside borrowerbookinsert method()----------------");
	SmsController controller = null;
	String status="";
	String fieldName;
	FacesContext fc = FacesContext.getCurrentInstance();

	try
	{
		
		String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("LogID");
		if(personID!=null)
		{
		if(validate4(true))
		{
			
	    ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		controller = (SmsController) ctx.getBean("controller");		
		status=controller.borrowrinsertbook(personID,libraryDataBean);
		logger.debug("check staus"+status);
		if(status.equalsIgnoreCase("Exsist")){			
			fieldName = CommonValidate.findComponentInRoot("addborowbook").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("This Student Already borrowed the book"));
		}
		else if(status.equalsIgnoreCase("NoStock")){			
			fieldName = CommonValidate.findComponentInRoot("addborowbook").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("No stock in Library"));
		}
		else if(status.equalsIgnoreCase("Success"))
		{
			  RequestContext r=RequestContext.getCurrentInstance();
	  			r.execute("PF('borrowbook').show()");
	  			libraryDataBean.setLibStudID("");
	  			libraryDataBean.setBorroweDate(null);
	  			libraryDataBean.setBookName("");
	  			libraryDataBean.setStudentname("");
	  			libraryDataBean.setLibStuClass("");
	  			libraryDataBean.setStudentid("");
	  			
		}else if(status.equalsIgnoreCase("Fail"))
		{
			fieldName = CommonValidate.findComponentInRoot("addborowbook").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("This Book not in Library"));
		}
		}
		}
	
	}
	catch(Exception e)
	{
		logger.warn(" exception - "+e);
	}
	return null;
}
private boolean validate4(boolean flag) 
{
	logger.info("-----------Inside validate4 method()----------------");
	boolean valid = true;
	String fieldName;
	FacesContext fc = FacesContext.getCurrentInstance();
	if (libraryDataBean.getLibStudID().equalsIgnoreCase("Choose")) 
	 {
	  if (flag) {
	   fieldName = CommonValidate.findComponentInRoot("studentids").getClientId(fc);
	   fc.addMessage(fieldName, new FacesMessage("Please Choose Student Name"));
	  }
	  valid = false;

	 }
	if (StringUtils.isEmpty(libraryDataBean.getBookName())) 
	{
		if (flag) {
			fieldName = CommonValidate.findComponentInRoot("bname").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Enter Book Name"));
		}
		valid = false;

	}
	if ((libraryDataBean.getBorroweDate())==null) 
	{
		if (flag) {
			fieldName = CommonValidate.findComponentInRoot("duecal").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Choose Due Date"));
		}
		valid = false;

	}
	
	return valid;


	}
}
