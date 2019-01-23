package com.lucas.pontoeletronico.api.utils;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

 
public class PasswordUtilsTest {
 
	private static final String SENHA = "ABCDEFG";
	private final BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();
	 
	@Test
	public void testPasswordNull() {		
		assertNull(PasswordUtils.generateBCrypt(null));
	}
	
	@Test
	public void testGeneratePassword() {
		String hash= PasswordUtils.generateBCrypt(SENHA);
		assertTrue(bcryptEncoder.matches(SENHA, hash));
	}
}

