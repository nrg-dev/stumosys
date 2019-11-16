package com.stumosys.managedBean;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.stumosys.controler.SmsController;
import com.stumosys.domain.BookSaleDataBean;
//import com.stumosys.domain.StudentDataBean;
import com.stumosys.shared.BookSalesRecord;
import com.stumosys.util.CommonValidate;

@ManagedBean(name = "bookDetailViewMB")
@RequestScoped
public class BookDetailViewMB {

	BookSaleDataBean bookSaleDataBean = new BookSaleDataBean();
	private boolean Boxflag = false;
	List<BookSaleDataBean> bookdetaillist = null;
	List<BookSalesRecord> viewlist = null;
	List<BookSalesRecord> editlist = null;
	private static Logger logger = Logger.getLogger(BookDetailViewMB.class);
	FacesContext context = FacesContext.getCurrentInstance();
	ResourceBundle paths = context.getApplication().evaluateExpressionGet(context, "#{paths}", ResourceBundle.class);
	Date now = new Date();
	SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
	public List<BookSalesRecord> getEditlist() {
		return editlist;
	}

	public void setEditlist(List<BookSalesRecord> editlist) {
		this.editlist = editlist;
	}

	private String status = "";
	private boolean DelBoxflag = false;
	List<BookSaleDataBean> filteredbook;

	public List<BookSalesRecord> getViewlist() {
		return viewlist;
	}

	public void setViewlist(List<BookSalesRecord> viewlist) {
		this.viewlist = viewlist;
	}

	
	public List<BookSaleDataBean> getFilteredbook() {
		return filteredbook;
	}

	public void setFilteredbook(List<BookSaleDataBean> filteredbook) {
		this.filteredbook = filteredbook;
	}

	public boolean isDelBoxflag() {
		return DelBoxflag;
	}

	public void setDelBoxflag(boolean delBoxflag) {
		DelBoxflag = delBoxflag;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<BookSaleDataBean> getBookdetaillist() {
		return bookdetaillist;
	}

	public void setBookdetaillist(List<BookSaleDataBean> bookdetaillist) {
		this.bookdetaillist = bookdetaillist;
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

	public String bookDetailViewPage() {
		logger.debug("---------inside bookDetailView------");
		SmsController controller = null;
		setBoxflag(false);
		setDelBoxflag(false);
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (personID != null) {
				bookdetaillist = controller.getBookInfo1(personID, bookSaleDataBean);
			}

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "BookDetailView";
	}

	public void bookView() {
		logger.debug("-----inside Book Delete--------");
		SmsController controller = null;
		try {
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			if (personID != null) {
				viewlist = controller.getBookDetailInfo(personID, bookSaleDataBean);
				if (viewlist.size() > 0) {
					bookSaleDataBean.setBookName(viewlist.get(0).getBookName());
					bookSaleDataBean.setBookPrice(viewlist.get(0).getPrice());
					bookSaleDataBean.setBookQuantity(viewlist.get(0).getQuantity());
					bookSaleDataBean.setBookfilePath(viewlist.get(0).getImagePath());
				}
			}

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
	}

	public String bookDelete1() {
		logger.debug("-----inside Book Delete--------");
		SmsController controller = null;
		try {
			if (bookSaleDataBean.getBookName() != null) {
				String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
						.get("LogID");
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				if (personID != null) {
					status = controller.getBookDelete(personID, bookSaleDataBean);
					if (status.equalsIgnoreCase("Success")) {
						setDelBoxflag(true);

					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "";
	}

	public String returnToHome() {
		logger.debug("-----------inside returntohome------");
		bookSaleDataBean.setBookName("");
		bookSaleDataBean.setBookQuantity("");
		bookSaleDataBean.setBookPrice("");
		setDelBoxflag(false);
		SmsController controller = null;

		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (personID != null) {
				bookdetaillist = controller.getBookInfo1(personID, bookSaleDataBean);
				return "BookView";
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "";
	}
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

	public String bookEdit() {
		logger.debug("---------inside bookDetailEdit------");
		SmsController controller = null;
		setBoxflag(false);
		setDelBoxflag(false);
		String status = "";
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("schoolID");
			int schoolid=Integer.parseInt(schoolID);
			if (personID != null) {
				if(schoolid==1 || schoolid==2 || schoolid==3 || schoolid==4 || schoolid==5 || schoolid==6){
					bookflag=true;bookflag1=false;
				}else if(schoolid==35 || schoolid==36 || schoolid==37 || schoolid==38 || schoolid==39 || schoolid==40 || schoolid==41 || schoolid==42 || schoolid==43){
					bookflag1=true;bookflag=false;
				}
				else{
					bookflag=true;bookflag1=false;
				}
				editlist = controller.getBookDetailInfo(personID, bookSaleDataBean);
			}
			bookSaleDataBean.setBookName(editlist.get(0).getBookName());
			bookSaleDataBean.setBookPrice(editlist.get(0).getPrice());
			bookSaleDataBean.setBookQuantity(editlist.get(0).getQuantity());
			bookSaleDataBean.setBookfilePath(editlist.get(0).getImagePath());
			status = "BookEditPage";

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return status;
	}

	public String bookUpdate() {
		logger.debug("-------inside bookUpdate--------");
		SmsController controller = null;
		String status = "";
		setDelBoxflag(false);
		setBoxflag(false);
		String schoolID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("schoolID");
		int school_ID=Integer.parseInt(schoolID);
		try {
			if(school_ID== 1|| school_ID == 2 || school_ID== 3 || school_ID==4 || school_ID==5 || school_ID==6){
				if (validate(true)) {
					
				}
			}else if(school_ID== 35|| school_ID == 36 || school_ID== 37 || school_ID==38 || school_ID==39 || school_ID==40 || school_ID==41 || school_ID==42 || school_ID==43){
				if (productvalidate(true)) {
					
				}
			}else{
				if (validate(true)) {
					
				}
			}
			
			if(validate(true) || productvalidate(true)){
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
						.get("LogID");
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
					status = controller.getBookUpdate(personID, bookSaleDataBean);
					if (status.equalsIgnoreCase("Success")) {
						setBoxflag(true);
					} else {
						setBoxflag(false);
					}
				}
			}

		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "";
	}
	
	private boolean productvalidate(boolean flag) {
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

				fieldName = CommonValidate.findComponentInRoot("productPhoto").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Choose the Photo"));
			}
			valid = false;
		}

		return valid;
	}

	private boolean validate(boolean flag) {
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
		

		return valid;
	}
	
	public void imageview(OutputStream out, Object data) throws IOException {
		String s = paths.getString("sms.addbook.photo");
		logger.debug("s---"+s+bookSaleDataBean.getBookfilePath());
		BufferedImage img = ImageIO
				.read(new File(s + bookSaleDataBean.getBookfilePath()));
		ImageIO.write(img, "png", out);
	}
	
	private void copyFile(String fileName, InputStream inputstream, String type) {
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
	
	public String stockinpage(){
		logger.debug("----- StockIn------");
		SmsController controller=null;
		setBoxflag(false);
		setDelBoxflag(false);
		try{
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			String personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("LogID");
			if (personID != null) {
				bookdetaillist = controller.getBookInfo1(personID, bookSaleDataBean);
				/*for(int i=0;i<bookdetaillist.size();i++){
					bookdetaillist.get(i).setBookQuantity("");					
				}
				*/
				
				
		}}
		catch(Exception e){
			logger.warn("Exception -->"+e.getMessage());
		
	}
		return "productstockin";
}
}

