package com.stumosys.managedBean;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
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
import com.stumosys.util.CommonValidate;
import com.stumosys.util.MailSendJDBC;

@ManagedBean(name = "libraryViewMB")
@RequestScoped
public class LibraryViewMB {
	Date now = new Date();
	SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
	LibraryDataBean libraryDataBean = new LibraryDataBean();
	private boolean tableflag = false;
	private List<LibraryDataBean> bookList = null;
	private List<LibraryDataBean> bookFilter;
	private List<LibraryDataBean> bookDetailsList = null;
	private UploadedFile file;
	private boolean boxflag = false;
	private boolean oldschlflag = false;
	private boolean newschlflag = false;
	private boolean delBoxflag = false;
	private boolean actionflag = true;
	private List<String> classSectionList = null;
	private List<String> studentIDList = null;
	private boolean tableFlag = false;
	private List<LibraryDataBean> BorrowerBookList = null;
	private List<LibraryDataBean> returnBookList = null;
	private List<LibraryDataBean> BorrowerfilterList;
	private static Logger logger = Logger.getLogger(LibraryViewMB.class);
	FacesContext context = FacesContext.getCurrentInstance();
	ResourceBundle text = context.getApplication().evaluateExpressionGet(context, "#{text}", ResourceBundle.class);
	ResourceBundle paths = context.getApplication().evaluateExpressionGet(context, "#{paths}", ResourceBundle.class);
	private List<String> langCategoryList = null;
	
	
	public List<LibraryDataBean> getReturnBookList() {
		return returnBookList;
	}

	public void setReturnBookList(List<LibraryDataBean> returnBookList) {
		this.returnBookList = returnBookList;
	}

	public boolean isActionflag() {
		return actionflag;
	}

	public void setActionflag(boolean actionflag) {
		this.actionflag = actionflag;
	}

	public boolean isOldschlflag() {
		return oldschlflag;
	}

	public void setOldschlflag(boolean oldschlflag) {
		this.oldschlflag = oldschlflag;
	}

	public boolean isNewschlflag() {
		return newschlflag;
	}

	public void setNewschlflag(boolean newschlflag) {
		this.newschlflag = newschlflag;
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
	 * @return the bookFilter
	 */
	public List<LibraryDataBean> getBookFilter() {
		return bookFilter;
	}

	/**
	 * @return the bookDetailsList
	 */
	public List<LibraryDataBean> getBookDetailsList() {
		return bookDetailsList;
	}

	/**
	 * @param bookDetailsList
	 *            the bookDetailsList to set
	 */
	public void setBookDetailsList(List<LibraryDataBean> bookDetailsList) {
		this.bookDetailsList = bookDetailsList;
	}

	/**
	 * @param bookFilter
	 *            the bookFilter to set
	 */
	public void setBookFilter(List<LibraryDataBean> bookFilter) {
		this.bookFilter = bookFilter;
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
	 * @return the borrowerBookList
	 */
	public List<LibraryDataBean> getBorrowerBookList() {
		return BorrowerBookList;
	}

	/**
	 * @param borrowerBookList
	 *            the borrowerBookList to set
	 */
	public void setBorrowerBookList(List<LibraryDataBean> borrowerBookList) {
		BorrowerBookList = borrowerBookList;
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

	/**
	 * @return the tableflag
	 */
	public boolean isTableflag() {
		return tableflag;
	}

	/**
	 * @return the bookList
	 */
	public List<LibraryDataBean> getBookList() {
		return bookList;
	}

	/**
	 * @param bookList
	 *            the bookList to set
	 */
	public void setBookList(List<LibraryDataBean> bookList) {
		this.bookList = bookList;
	}

	/**
	 * @param tableflag
	 *            the tableflag to set
	 */
	public void setTableflag(boolean tableflag) {
		this.tableflag = tableflag;
	}

	/**
	 * @param libraryDataBean
	 *            the libraryDataBean to set
	 */
	public void setLibraryDataBean(LibraryDataBean libraryDataBean) {
		this.libraryDataBean = libraryDataBean;
	}
	public boolean checkflag=false;
	public boolean isCheckflag() {
		return checkflag;
	}

	public void setCheckflag(boolean checkflag) {
		this.checkflag = checkflag;
	}

	public String bookListPage() {
		logger.info("-----------Inside  bookListPage method()----------------");
		try
		{
			
		setTableflag(false);
		setBookList(null);newschlflag=false;oldschlflag=false;
		libraryDataBean.setBookCategory("");
		SmsController controller = null;
		setDelBoxflag(false);
		setBoxflag(false);
		String langnames=text.getString("international.lang");
		langCategoryList= new ArrayList<String>(Arrays.asList(langnames.split(",")));
		String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("schoolID");
		String roll = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("Role");
		   int schoolid=Integer.parseInt(schoolID);
		   ApplicationContext ctx = FacesContextUtils
					.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
		   if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
			   checkflag=true;
		   }else{
			   String name="";
			   bookList = controller.getBookListView(personID, name);
			   checkflag=false;
			   if(roll.equalsIgnoreCase("Parent")){
		    		 actionflag=false;
		    	 }
		    	 else actionflag=true;
		    	 newschlflag=true;oldschlflag=false;
		   }
		   
		}catch(Exception e){
			logger.warn("Inside Exception",e);
		}
		return "Bookview";
		

	}

	public void categoryChange(ValueChangeEvent event) {
		logger.info("-----------Inside categoryChange method()----------------");
		SmsController controller = null;
		bookList = null;
		try {
			bookList = new ArrayList<LibraryDataBean>();
			String name = event.getNewValue().toString();
			logger.debug("name" + name);
			if (name != null) {
				if (name.equalsIgnoreCase("NoSelection")) {
					newschlflag=false;oldschlflag=false;
				} else {
					ApplicationContext ctx = FacesContextUtils
							.getWebApplicationContext(FacesContext.getCurrentInstance());
					controller = (SmsController) ctx.getBean("controller");
					String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
							.get("LogID");
					if (personID != null) {
						libraryDataBean.setBookCategory(name);
						bookList = controller.getBookListView(personID, name);
						logger.debug("library size  "+bookList.size());
						String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
								.get("schoolID");
						String roll = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
								.get("Role");
						   int schoolid=Integer.parseInt(schoolID);
						   if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
							      oldschlflag=true;newschlflag=false;
							     }else{
							    	 if(roll.equalsIgnoreCase("Parent")){
							    		 actionflag=false;
							    	 }
							    	 else actionflag=true;
							    	 newschlflag=true;oldschlflag=false;
							     }
					} else {
						newschlflag=false;oldschlflag=false;

					}
				}
			}

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
	}

	public String view() {
		logger.info("-----------Inside view method()----------------");
		SmsController controller = null;
		bookDetailsList = null;

		try {
			bookDetailsList = new ArrayList<LibraryDataBean>();

			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (personID != null) {
				bookDetailsList = controller.getBookDetailsView(personID, libraryDataBean);
				logger.debug(bookDetailsList.size());
				if (bookDetailsList.size() > 0) {
					if (bookDetailsList.get(0).getLibrayList() != null) {
						if (bookDetailsList.get(0).getLibrayList().size() > 0) {
							logger.debug("Inside Libray Details");
							libraryDataBean
									.setAuthorName(bookDetailsList.get(0).getLibrayList().get(0).getAuthorName());
							libraryDataBean
									.setBookCategory(bookDetailsList.get(0).getLibrayList().get(0).getCategory());
							libraryDataBean
									.setBookDescription(bookDetailsList.get(0).getLibrayList().get(0).getDescription());
							libraryDataBean
									.setBookeditions(bookDetailsList.get(0).getLibrayList().get(0).getBookEdition());
							libraryDataBean.setBookName(bookDetailsList.get(0).getLibrayList().get(0).getBookName());
							libraryDataBean.setBookPages(bookDetailsList.get(0).getLibrayList().get(0).getPages());
							libraryDataBean.setBookPrice(bookDetailsList.get(0).getLibrayList().get(0).getPrice());
							libraryDataBean.setBookDueType(bookDetailsList.get(0).getLibrayList().get(0).getDueType());
							libraryDataBean.setBookFee(bookDetailsList.get(0).getLibrayList().get(0).getPenaltyFee());
							BigInteger tempQty=BigInteger.ZERO;
							if(bookDetailsList.get(0).getLibrayList().get(0).getQuantity() == null){
								tempQty=new BigInteger("0");
							}else{
								tempQty=new BigInteger(bookDetailsList.get(0).getLibrayList().get(0).getQuantity());
							}
							libraryDataBean.setBookQty(tempQty.toString());
						} else {
							libraryDataBean.setAuthorName("");
							libraryDataBean.setBookCategory("");
							libraryDataBean.setBookDescription("");
							libraryDataBean.setBookeditions("");
							libraryDataBean.setBookName("");
							libraryDataBean.setBookPages("");
							libraryDataBean.setBookPrice("");
							libraryDataBean.setBookDueType("");
							libraryDataBean.setBookFee("");
							libraryDataBean.setBookQty("");
						}
					} else {
						libraryDataBean.setAuthorName("");
						libraryDataBean.setBookCategory("");
						libraryDataBean.setBookDescription("");
						libraryDataBean.setBookeditions("");
						libraryDataBean.setBookName("");
						libraryDataBean.setBookPages("");
						libraryDataBean.setBookPrice("");
						libraryDataBean.setBookDueType("");
						libraryDataBean.setBookFee("");
						libraryDataBean.setBookQty("");
					}
					if (bookDetailsList.get(0).getLibrayImageList() != null) {
						if (bookDetailsList.get(0).getLibrayImageList().size() > 0) {
							logger.debug("Inside ImagePath");
							libraryDataBean
									.setParfilePathDate(bookDetailsList.get(0).getLibrayImageList().get(0).getDate());
							libraryDataBean
									.setParfilePath(bookDetailsList.get(0).getLibrayImageList().get(0).getPath());
						} else {
							libraryDataBean.setParfilePathDate(null);
							libraryDataBean.setParfilePath("");
						}
					} else {
						libraryDataBean.setParfilePathDate(null);
						libraryDataBean.setParfilePath("");
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return null;
	}

	public void imageview(OutputStream out, Object data) throws IOException {
		logger.info("-----------Inside imageview method()----------------");
		String s = paths.getString("sms.library.photo");
		BufferedImage img = ImageIO.read(
				new File(s + ft.format(libraryDataBean.getParfilePathDate()) + "/" + libraryDataBean.getParfilePath()));
		ImageIO.write(img, "png", out);
	}

	public String returnToHome() {

		try {
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (personID != null) {
				return "SuccessHome";
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "";

	}

	public String returnListToHome() {
		logger.info("-----------Inside returnListToHome method()----------------");
		SmsController controller = null;
		delBoxflag = false;
		bookList = null;
		try {
			bookList = new ArrayList<LibraryDataBean>();
			if (libraryDataBean.getBookCategory() != null) {
				if (libraryDataBean.getBookCategory().equalsIgnoreCase("NoSelection")) {
					setTableflag(false);
				} else {
					ApplicationContext ctx = FacesContextUtils
							.getWebApplicationContext(FacesContext.getCurrentInstance());
					controller = (SmsController) ctx.getBean("controller");
					String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
							.get("LogID");
					if (personID != null) {
						// libraryDataBean.setBookCategory(name);
						bookList = controller.getBookListView(personID, libraryDataBean.getBookCategory());
						setTableflag(true);
					} else {
						setTableflag(false);

					}
				}
			}
			return "SuccessHome";
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "";
	}

	public String edit() {
		logger.info("-----------Inside edit method()----------------");
		SmsController controller = null;
		bookDetailsList = null;
		setBoxflag(false);
		setDelBoxflag(false);
		try {
			bookDetailsList = new ArrayList<LibraryDataBean>();
			String langnames=text.getString("international.lang");
			langCategoryList= new ArrayList<String>(Arrays.asList(langnames.split(",")));
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (personID != null) {
				bookDetailsList = controller.getBookDetailsView(personID, libraryDataBean);
				logger.debug(bookDetailsList.size());
				if (bookDetailsList.size() > 0) {
					if (bookDetailsList.get(0).getLibrayList() != null) {
						if (bookDetailsList.get(0).getLibrayList().size() > 0) {
							logger.debug("Inside Libray Details");
							libraryDataBean
									.setAuthorName(bookDetailsList.get(0).getLibrayList().get(0).getAuthorName());
							libraryDataBean
									.setBookCategory(bookDetailsList.get(0).getLibrayList().get(0).getCategory());
							libraryDataBean
									.setBookDescription(bookDetailsList.get(0).getLibrayList().get(0).getDescription());
							libraryDataBean
									.setBookeditions(bookDetailsList.get(0).getLibrayList().get(0).getBookEdition());
							libraryDataBean.setBookName(bookDetailsList.get(0).getLibrayList().get(0).getBookName());
							libraryDataBean.setBookPages(bookDetailsList.get(0).getLibrayList().get(0).getPages());
							libraryDataBean.setBookPrice(bookDetailsList.get(0).getLibrayList().get(0).getPrice());
							libraryDataBean.setBookDueType(bookDetailsList.get(0).getLibrayList().get(0).getDueType());
							libraryDataBean.setBookFee(bookDetailsList.get(0).getLibrayList().get(0).getPenaltyFee());
							BigInteger tempQty=BigInteger.ZERO;
							if(bookDetailsList.get(0).getLibrayList().get(0).getQuantity() == null){
								tempQty=new BigInteger("0");
							}else{
								tempQty=new BigInteger(bookDetailsList.get(0).getLibrayList().get(0).getQuantity());
							}
							libraryDataBean.setBookQty(tempQty.toString());
						} else {
							libraryDataBean.setAuthorName("");
							libraryDataBean.setBookCategory("");
							libraryDataBean.setBookDescription("");
							libraryDataBean.setBookeditions("");
							libraryDataBean.setBookName("");
							libraryDataBean.setBookPages("");
							libraryDataBean.setBookPrice("");
							libraryDataBean.setBookDueType("");
							libraryDataBean.setBookFee("");
							libraryDataBean.setBookQty("");
						}
					} else {
						libraryDataBean.setAuthorName("");
						libraryDataBean.setBookCategory("");
						libraryDataBean.setBookDescription("");
						libraryDataBean.setBookeditions("");
						libraryDataBean.setBookName("");
						libraryDataBean.setBookPages("");
						libraryDataBean.setBookPrice("");
						libraryDataBean.setBookDueType("");
						libraryDataBean.setBookFee("");
						libraryDataBean.setBookQty("");
					}
					if (bookDetailsList.get(0).getLibrayImageList() != null) {
						if (bookDetailsList.get(0).getLibrayImageList().size() > 0) {
							logger.debug("Inside ImagePath");
							libraryDataBean
									.setParfilePathDate(bookDetailsList.get(0).getLibrayImageList().get(0).getDate());
							libraryDataBean
									.setParfilePath(bookDetailsList.get(0).getLibrayImageList().get(0).getPath());
						} else {
							libraryDataBean.setParfilePathDate(null);
							libraryDataBean.setParfilePath("");
						}
					} else {
						libraryDataBean.setParfilePathDate(null);
						libraryDataBean.setParfilePath("");
					}
				}
				return "pageBookEdit";
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "";
	}

	public String dummyAction(FileUploadEvent event) throws IOException {
		logger.info("-----------Inside dummyAction method()----------------");
		this.setFile(event.getFile());
		libraryDataBean.setBookFile(event.getFile());
		FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, message);

		return "";
	}

	public String update() {
		logger.info("-----------Inside update method()----------------");
		SmsController controller = null;
		setBoxflag(false);
		setDelBoxflag(false);
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			logger.debug("---------- submit Method Calling-----" + libraryDataBean.getBookCategory());
			if (validate(true)) {
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
						.get("LogID");
				if (personID != null) {
					if (libraryDataBean.getBookFile() != null) {
						String s = libraryDataBean.getBookFile().getContentType();
						logger.debug("file Type " + s);
						String type = s.substring(s.lastIndexOf("/") + 1);
						logger.debug(type);
						String fileName = libraryDataBean.getBookName() + libraryDataBean.getAuthorName()
								+ libraryDataBean.getBookeditions() + "Edited";
						fileName.trim();
						copyFile(fileName, libraryDataBean.getBookFile().getInputstream(), type);

						String path = ft.format(now) + "/" + fileName + "." + type;
						libraryDataBean.setParfilePath(path);
						logger.debug(libraryDataBean.getParfilePath());
					}
					String status = controller.updateLibrary(personID, libraryDataBean);
					logger.debug("status"+status);
					if (status.equalsIgnoreCase("Success")) {
						setBoxflag(true);
					}else if(status.equalsIgnoreCase("Borrowed")){
						setBoxflag(false);
						fieldName = CommonValidate.findComponentInRoot("addBookQuantity").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage("Borrowed Quantity is: "+libraryDataBean.getBorrowedBookQty() +"Please Enter Valid Quantity"));
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

	private boolean validate(boolean flag) {
		logger.info("-----------Inside update validate()----------------");
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

	public String delete() {
		logger.info("-----------Inside delete method()----------------");
		SmsController controller = null;
		setDelBoxflag(false);
		setBoxflag(false);
		try {

			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (personID != null) {
				String status = controller.deleteBook(personID, libraryDataBean);
				if (status.equalsIgnoreCase("Success")) {
					setDelBoxflag(true);
				} else {
					setDelBoxflag(false);
				}
			}

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return null;

	}

	public String BorrowerViewListPage() {
		logger.info("-----------Inside BorrowerViewListPage method()----------------");
		classSectionList = null;
		studentIDList = null;
		SmsController controller = null;
		libraryDataBean.setLibStuClass("");
		libraryDataBean.setLibStudID("");
		setTableflag(false);

		try {
			classSectionList = new ArrayList<String>();
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (personID != null)
			{
				String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
						.get("schoolID");
				 int schoolid=Integer.parseInt(schoolID);
				 if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6)
				 {
					 classSectionList = controller.getClassNameList(personID);
					 HashSet<String> hashclass = new HashSet<String>(classSectionList);
				      classSectionList.clear();
				      classSectionList.addAll(hashclass);
				      Collections.sort(classSectionList);
				      oldschlflag=true;newschlflag=false;
				 }else{
					 	BorrowerBookList = controller.getBoorowerBookView1(personID);
				    	newschlflag=true;oldschlflag=false;
				     }
				
			}logger.debug("newschlflag --- "+newschlflag+"oldschlflag ----"+oldschlflag);
			return "viewBorrower";
			
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());

		}
		return "";
	}

	public void classChange(ValueChangeEvent event) {
		logger.info("-----------Inside classChange method()----------------");
		SmsController controller = null;
		studentIDList = null;
		libraryDataBean.setLibStudID("");
		libraryDataBean.setBookCategory("");
		setTableflag(false);
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
				studentIDList = controller.getStudentIDList1(name, personID);
				logger.debug(studentIDList);
			}

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());

		}
	}

	public void studentIDChange(ValueChangeEvent event) {
		logger.info("-----------Inside studentIDChange method()----------------");
		//String s = event.getNewValue().toString();
		SmsController controller = null;
		setTableflag(false);
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			String name = event.getNewValue().toString();
			logger.debug(name);
			if (personID != null && name != null) {
				BorrowerBookList = controller.getBoorowerBookView(personID, name);
				logger.debug("BorrowerBookList" + BorrowerBookList.size());
				setTableflag(true);
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());

		}
	}

	/**
	 * @return the tableFlag
	 */
	public boolean isTableFlag() {
		return tableFlag;
	}

	/**
	 * @param tableFlag
	 *            the tableFlag to set
	 */
	public void setTableFlag(boolean tableFlag) {
		this.tableFlag = tableFlag;
	}

	/**
	 * @return the borrowerfilterList
	 */
	public List<LibraryDataBean> getBorrowerfilterList() {
		return BorrowerfilterList;
	}

	/**
	 * @param borrowerfilterList
	 *            the borrowerfilterList to set
	 */
	public void setBorrowerfilterList(List<LibraryDataBean> borrowerfilterList) {
		BorrowerfilterList = borrowerfilterList;
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

	public List<String> getLangCategoryList() {
		return langCategoryList;
	}

	public void setLangCategoryList(List<String> langCategoryList) {
		this.langCategoryList = langCategoryList;
	}
	public String returnViewListPage() {
		logger.info("-----------Inside returnViewListPage method()----------------");
		SmsController controller = null;
		setTableflag(false);
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (personID != null)
			{
				returnBookList = controller.getreturnBookView1(personID);	
				logger.debug("returnBookList "+returnBookList.size());
			}
			return "returnbookview";
			
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());

		}
		return "";
	}

	
}
