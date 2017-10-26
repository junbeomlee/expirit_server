package com.tanx.expirit.domain.controller;

import static org.junit.Assert.assertNotNull;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.tanx.expirit.Expirit2Application;
import com.tanx.expirit.user.UserController;
import com.tanx.expirit.user.UserDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=Expirit2Application.class)
@WebAppConfiguration
@Transactional
public class RestControllerTest {

	@Autowired
	UserController userController;
	
	@Before
	public void setUp(){
		assertNotNull(userController);
	}
	
	@After
	public void tearDown(){
		
	}
	
	@Test
	public void RestInsertandFindTest(){
		
		//when
//		UserDTO userDTO = new UserDTO();
//		userDTO.setEmail("leebduk@gmail.com");
//		userDTO.setUserName("test");
//		userController.insert(userDTO).toObservable().toBlocking().single();
//	
//		UserDTO userDTO2=userController.findOne("leebduk@gmail.com").toObservable().toBlocking().single();
//		
//		assertEquals(userDTO,userDTO2);
	}
	
	@Test
	public void deleteTest(){
		//when 
		UserDTO userDTO = new UserDTO();
	}
}
