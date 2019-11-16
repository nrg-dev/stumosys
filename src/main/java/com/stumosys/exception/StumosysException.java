package com.stumosys.exception;

/**
 * This Java Class will communicate with Custom Exception
 * ------------------------------------------------------------
 * @author  |   Created Date	|	Changed Date	|	Module |
 * ------------------------------------------------------------
 * Alex        	02-July-2014     						Login Authorization
 * Sivaranjini
 *       
 */

public class StumosysException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public  String errorMsg;
	public  String errorCode;
	
	public  String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String message ,String errorCode) {
		this.errorCode = errorCode;
	}
	
	 public StumosysException(String message) {
	        super(message);
	        setErrorMsg(message);
	    }
	 public StumosysException(String message, Throwable throwable) {
	        super(message, throwable);
	    }
	

}
