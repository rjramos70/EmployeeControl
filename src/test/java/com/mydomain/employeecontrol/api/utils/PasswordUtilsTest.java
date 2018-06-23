package com.mydomain.employeecontrol.api.utils;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.mydomain.employeecontrol.api.utils.PasswordUtils;

public class PasswordUtilsTest {
	
	private static final String SENHA = "123456";
	private final BCryptPasswordEncoder bCryptEndorce = new BCryptPasswordEncoder();
	
	@Test
	public void testSenhaNula() throws Exception {
		assertNull(PasswordUtils.gerarBCrypt(null));
	}
	
	@Test
	public void testGerarHashSenha() throws Exception{
		String hash = PasswordUtils.gerarBCrypt(SENHA);
		assertTrue(bCryptEndorce.matches(SENHA, hash));
	}
	

}