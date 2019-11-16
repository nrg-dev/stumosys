package sms;

import java.security.SecureRandom;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;



public class PasswordEnc {
	final static Logger logger = Logger.getLogger(PasswordEnc.class);
	private static char[] VALID_CHARACTERS =
		    "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456879".toCharArray();
	 
public static void main(String args[]) {
	char[] VALID_CHARACTERS1="Arjun".toCharArray();
	StringBuilder sb=new StringBuilder().append(VALID_CHARACTERS1).append(VALID_CHARACTERS);
	logger.debug("----"+sb.toString());
	String s111=sb.toString();
	
	char[] v=s111.toCharArray();
	logger.debug(v);
	logger.debug(v.length);
	 SecureRandom srand = new SecureRandom();
	    Random rand = new Random();
	    char[] buff = new char[10];

	    for (int i = 0; i < 10; ++i) {
	      // reseed rand once you've used up all available entropy bits
	      if ((i % 10) == 0) {
	          rand.setSeed(srand.nextLong()); // 64 bits of random!
	      }
	      buff[i] = v[rand.nextInt(v.length)];
	    }
	    String s=new String(buff);
logger.debug(s);
	/*String password = "robert";
	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	String hashedPassword ="$2a$10$ih7avYUKv8QmM0KO5yrODehdHOOxgL7Qidvmra7eztaTv545Dagi6"; passwordEncoder.encode(password);
	String hashedPassword1 =passwordEncoder.encode(password);
	logger.debug(hashedPassword1);
	logger.debug(""+passwordEncoder.matches(password, hashedPassword));*/
	String password = s;
	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	String hashedPassword1 =passwordEncoder.encode(password);
	logger.debug(hashedPassword1);
	
	
	 
}

}
