package com.lucas.pontoeletronico.api.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {

	private static final Logger log = LoggerFactory.getLogger(PasswordUtils.class);
	
	public PasswordUtils() {
		
	}
	
	public static String generateBCrypt(String password) {
		if(password ==null) {
			return password;
		}
		
		log.info("Generating Hash com BCrypt");
		
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		return bcrypt.encode(password);	
	}
}
