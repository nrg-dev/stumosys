package com.stumosys.util;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.log4j.Logger;

import com.stumosys.managedBean.NavigationBean;

public class OrderNumber {

	public static final int interval = 1000;
	public static final String suffixID = "@nrg.com";
	public static NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
	 final static Logger logger = Logger.getLogger(OrderNumber.class);
	public static String getSalesOrderNo(int count) {
		/*
		 * int randomNo=count; String refId="SON"+randomNo;
		 */
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		logger.debug("Current Year is : " + year);
		String month = new SimpleDateFormat("MMM").format(now.getTime());
		logger.debug("Month" + month);
		String s1 = String.format("%05d", count);

		String refId = "SO" + s1 + "/" + month + "/" + year;

		return refId;
	}

	public static String getPurRefNum(int count) {
		int randomNo = count + 1;
		String refId = null;
		if (randomNo <= 9) {
			refId = "PO000" + randomNo;
		} else if (randomNo > 9 && randomNo <= 99) {
			refId = "PO00" + randomNo;
		} else if (randomNo > 99 && randomNo <= 999) {
			refId = "PO0" + randomNo;
		} else {
			refId = "PO" + randomNo;
		}
		logger.debug("-->> PurRefNum " + refId);
		return refId;
	}

}
