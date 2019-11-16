package com.stumosys.util;

import java.security.SecureRandom;
import java.util.Random;

public class RandomPasswordGenerator {

	private static char[] VALID_CHAR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890".toCharArray();

	public static String GenerateSecurePassword(String name) {

		char[] VALID_CHAR1 = name.toCharArray();
		StringBuilder sb = new StringBuilder().append(VALID_CHAR).append(VALID_CHAR1);
		String temp = sb.toString();
		char[] Final_Char = temp.toCharArray();
		SecureRandom secureRan = new SecureRandom();
		Random random = new Random();
		char[] pwd = new char[10];

		for (int i = 0; i < 10; ++i) {
			if ((i % 10) == 0) {
				random.setSeed(secureRan.nextLong());
			}
			pwd[i] = Final_Char[random.nextInt(Final_Char.length)];
		}
		return new String(pwd);

	}

	public static String GenerateSecureOTP() {

		StringBuilder ot = new StringBuilder().append(VALID_CHAR);
		String temp = ot.toString();
		char[] Final_Char = temp.toCharArray();
		SecureRandom secureotp = new SecureRandom();
		Random random = new Random();
		char[] onetimepass = new char[4];
		for (int i = 0; i < 4; ++i) {
			if ((i % 4) == 0) {
				random.setSeed(secureotp.nextLong());
			}
			onetimepass[i] = Final_Char[random.nextInt(Final_Char.length)];
		}
		return new String(onetimepass);
	}

}
