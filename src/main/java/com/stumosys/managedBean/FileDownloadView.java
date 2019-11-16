package com.stumosys.managedBean;

import java.io.InputStream;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
 
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
 
//@ManagedBean
@ManagedBean(name = "fileDownloadView")
public class FileDownloadView {
     
    private StreamedContent file;
     
    public  FileDownloadView() { 
    	try {
    	System.out.println("------------ Inside FileDownloadView----------------");
        InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/download/boromir.jpg");
        file = new DefaultStreamedContent(stream, "application/xls", "teacher-register-form.xlsx");
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	
    }
 
    public StreamedContent getFile() {
        return file;
        //return "fileupload";
    }
    }
    