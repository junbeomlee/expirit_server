package com.tanx.expirit.util;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class EncoderTest {

	@Before
	public void setUp(){
		
	}
	
	@Test
	public void encodeTest(){
		PasswordEncoder asd=new BCryptPasswordEncoder();
		System.out.println(asd.encode("1q1q1q1q"));
		asd.matches("1q1q1q1q", "$2a$10$oFUTRBfKtgDxmsugB8Ks2eiuUNpBQctvWdrPNKwJeV/W3tmF5k28K");
	}
}
