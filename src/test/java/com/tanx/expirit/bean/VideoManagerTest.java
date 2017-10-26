package com.tanx.expirit.bean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

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
import com.tanx.expirit.config.VideoConfig.VideoManager;
import com.tanx.expirit.exception.NotFoundIDException;
import com.tanx.expirit.video.Video;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=Expirit2Application.class)
@WebAppConfiguration
@Transactional
public class VideoManagerTest {

	@Autowired
	VideoManager videoManager;

	@Before
	public void setUp(){
		
	}
	
	@After
	public void tearDown(){
		
	}
	
	@Test
	public void getVideoTest(){
		Video video=videoManager.getVideo("air_squat");
		System.out.println(video.getContent().contentLength());
		assertEquals(video.getVideoName(),"air_squat.mp4");
	}
	
	@Test(expected=NotFoundIDException.class)
	public void getVideoExceptionTest(){
		Video video=videoManager.getVideo("asd");
	}
	
	@Test
	public void asd(){
		
	}
}
