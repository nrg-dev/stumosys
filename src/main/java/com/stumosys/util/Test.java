package com.stumosys.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import org.apache.log4j.Logger;

public class Test {
	 final static Logger logger = Logger.getLogger(Test.class);
	 
	 
	 public static String encrypt(String key, String initVector, String value) {
	        try {
	            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
	            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

	            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

	            byte[] encrypted = cipher.doFinal(value.getBytes());
	            System.out.println("encrypted string: "
	                    + Base64.encodeBase64String(encrypted));

	            return Base64.encodeBase64String(encrypted);
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }

	        return null;
	    }
	 
	 public static String decrypt(String key, String initVector, String encrypted) {
	        try {
	            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
	            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

	            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

	            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

	            return new String(original);
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }

	        return null;
	    }
	 
	 public static String decrypt1(String key, String initVector, String encrypted) {
	        try {
	            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-16"));
	            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-16"), "AES");

	            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

	            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

	            return new String(original);
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }

	        return null;
	    }
	 
	public static void main(String[] args) throws NoSuchAlgorithmException {
		
		 String key = "Bar12345Bar12345"; // 128 bit key
	        String initVector = "RandomInitVector"; // 16 bytes IV
	        System.out.println(decrypt(key, initVector,"$2a$10$oOC3tUKFZMSrZaqaBwyHhuSA6uuFGDOxYaXmwh6ni1Z1.S3Xqh8gG"));
//9MU7vSBqfzPnj7iWvvfsEw==
	        
	        /*System.out.println(decrypt(key, initVector,
	                encrypt(key, initVector, "Hello World")));
	   */ }
		
		/*
		Date d = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm:ss a");
		logger.debug("date--" + d);
		logger.debug("date--" + dateFormat.format(d));
		 logger.debug("date--"+ new Timestamp(sdf. d.getTime())); 
		String password = "other";

		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(password.getBytes());

		byte byteData[] = md.digest();

		// convert the byte to hex format method 1
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		}

		logger.debug("Hex format : " + sb.toString());

		
		 * //convert the byte to hex format method 2 StringBuffer hexString =
		 * new StringBuffer(); for (int i=0;i<byteData.length;i++) { String
		 * hex=Integer.toHexString(0xff & byteData[i]); if(hex.length()==1)
		 * hexString.append('0'); hexString.append(hex); } logger.debug(
		 * "Hex format : " + hexString.toString());
		 
		
		
	*/}

