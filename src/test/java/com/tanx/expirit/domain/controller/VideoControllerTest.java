package com.tanx.expirit.domain.controller;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import com.tanx.expirit.exception.DuplicatedVideoNameException;
import com.tanx.expirit.video.Video;
import com.tanx.expirit.video.VideoController;
import com.tanx.expirit.video.VideoService;

import rx.Observable;

@RunWith(MockitoJUnitRunner.class)
public class VideoControllerTest {

	@Mock
	VideoService videoService;
	
	@InjectMocks
	VideoController videoController;

	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
	}
	
	@After
	public void tearDown(){
		
	}
	
	@Test
	public void downloadVideoTest(){
		ByteArrayResource byteArrayRe=new ByteArrayResource("asd".getBytes());
		Mockito.when(videoService.getVideo("test")).thenReturn(Observable.just(new Video("test.mp4",byteArrayRe)));
		try {
			ResponseEntity<ByteArrayResource> response=videoController.download("test");
			assertEquals(response.getBody(),byteArrayRe);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DuplicatedVideoNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
