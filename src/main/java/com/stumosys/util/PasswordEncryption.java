package com.stumosys.util;

import org.apache.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncryption {
	 final static Logger logger = Logger.getLogger(PasswordEncryption.class);
	
	 public static String GeneratePaswword(String value) {
		String result = "";
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword1 = passwordEncoder.encode(value);
		logger.debug(hashedPassword1);
		result = hashedPassword1;
		return result;

	}
	 
	
	 
	
/*	public static String encrypt(String key, String initVector, String value) {
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
	*/

	public static boolean MatchPaswword(String rawPassword, String encodedPassword) throws IllegalArgumentException {
		boolean valid = false;
		if (rawPassword != null && encodedPassword != null) {
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			if (passwordEncoder.matches(rawPassword, encodedPassword) == true) {
				valid = true;
			} else {
				valid = false;
			}
		}
		return valid;
	}
	/*
	 * public static String GeneratePaswword(String value) { String result = "";
	 * try{ if((value !=null) || (value.equalsIgnoreCase(""))){ MessageDigest md
	 * = MessageDigest.getInstance("SHA-256"); md.update(value.getBytes());
	 * 
	 * byte byteData[] = md.digest();
	 * 
	 * //convert the byte to hex format method 1 StringBuffer sb = new
	 * StringBuffer(); for (int i = 0; i < byteData.length; i++) {
	 * sb.append(Integer.toString((byteData[i] & 0xff) + 0x100,
	 * 16).substring(1)); }
	 * 
	 * // logger.debug("Hex format : " + sb.toString());
	 * result=sb.toString(); } }catch(Exception e){ logger.warn("Inside Exception",e); }
	 * return result;
	 * 
	 * }
	 */

}
