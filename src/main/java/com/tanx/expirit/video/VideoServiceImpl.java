package com.tanx.expirit.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tanx.expirit.exception.NotFoundIDException;

import rx.Observable;

@Service
public class VideoServiceImpl implements VideoService{

	@Autowired
	VideoRepository videoRepositry;
	
	@Override
	public Observable<Video> getVideo(String name) {
		// TODO Auto-generated method stub
		return videoRepositry
				.getVideo(name)
				.doOnNext(video->{
					if(video==null)
						throw new NotFoundIDException("No matched video");
				});
	}

}
