package com.tanx.expirit.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.tanx.expirit.exception.NotFoundIDException;
import com.tanx.expirit.video.Video;
import com.tanx.expirit.video.VideoRepository;
import com.tanx.expirit.video.VideoServiceImpl;

import rx.Observable;

@RunWith(MockitoJUnitRunner.class)
public class VideoServiceTest {
	
	@Mock
	VideoRepository videoRepository;
	
	@InjectMocks
	VideoServiceImpl videoService;

	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
	}
	
	@After
	public void tearDown(){
		
	}

	@Test(expected=NotFoundIDException.class)
	public void getVideoServiceNullExceptionTest(){
		
		//when
		Mockito.when(videoRepository.getVideo("null")).thenReturn(Observable.just(null));
		
		//then
		Video video=videoService.getVideo("null").toBlocking().single();
	}
}
