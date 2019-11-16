package com.stumosys.util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class Validation {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/sms";
	static final String USER = "root";
	static final String PASS = "root";
	public static String status;
	final static Logger logger = Logger.getLogger(Validation.class);
	public static String zipvalidation(String myName12) {
		logger.debug("Calling Zip Validatoion" + myName12);
		String s = myName12;
		String output = null;
		Connection conn = null; // move
		PreparedStatement preparedStatement1 = null; // move
		PreparedStatement preparedStatement2 = null;
		List<String> postlist = new ArrayList<String>();
		List<String> statelist = new ArrayList<String>();

		try {
			Class.forName("com.mysql.jdbc.Driver"); // move
			logger.debug("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS); // move
			logger.debug("Creating statement...");
			String selectSQL1 = "SELECT * FROM Postcode where postcode=?";
			preparedStatement1 = conn.prepareStatement(selectSQL1);
			preparedStatement1.setString(1, s);
			ResultSet rs1 = preparedStatement1.executeQuery();
			while (rs1.next()) {
				String state = rs1.getString("state_code");
				postlist.add(state);
			}
			if (postlist.size() > 0) {
				String selectSQL2 = "SELECT * FROM State where state_code=?";
				preparedStatement2 = conn.prepareStatement(selectSQL2);
				preparedStatement2.setString(1, postlist.get(0));
				ResultSet rs2 = preparedStatement2.executeQuery();
				while (rs2.next()) {
					String state1 = rs2.getString("state_name");
					statelist.add(state1);
					logger.debug("--------state name2-----" + state1);
				}
				output = statelist.get(0);
				rs1.close();
				rs2.close();
				preparedStatement1.close();
				preparedStatement2.close();
				conn.close();
			}
		} catch (SQLException se) {
		} catch (Exception e) {
		} finally {
			try {
				if (preparedStatement1 != null)
					preparedStatement1.close();
				if (preparedStatement2 != null)
					preparedStatement2.close();
			} catch (SQLException se2) {

			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
			}
		}
		return output;
	}

	public static String rollValidation(String roll) {
		logger.debug("Calling roll Validation" + roll);
		String s = roll;
		String output = "no";
		Connection conn = null;
		PreparedStatement preparedStatement1 = null;
		List<String> perlist = new ArrayList<String>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			logger.debug("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			logger.debug("Creating statement...");
			String selectSQL1 = "SELECT * FROM Person where person_role_number=? and status='Active'";
			preparedStatement1 = conn.prepareStatement(selectSQL1);
			preparedStatement1.setString(1, s);
			ResultSet rs1 = preparedStatement1.executeQuery();
			while (rs1.next()) {
				String state = rs1.getString("person_role");
				perlist.add(state);
			}
			if (perlist.size() > 0) {
				output = "Exist";
				rs1.close();
				preparedStatement1.close();
				conn.close();
			}
		} catch (SQLException se) {
		} catch (Exception e) {
		} finally {
			try {
				if (preparedStatement1 != null)
					preparedStatement1.close();
			} catch (SQLException se2) {

			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
			}
		}
		return output;
	}
}
