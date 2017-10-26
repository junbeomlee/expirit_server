package com.tanx.expirit.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tanx.expirit.config.VideoConfig.VideoManager;

import rx.Observable;

@Repository
public class VideoRepositoryImpl implements VideoRepository{
	

	@Autowired
	VideoManager videoManager;
	
	@Override
	public Observable<Video> getVideo(String videoName) {
		// TODO Auto-generated method stub
		return Observable
				.defer(()->Observable.just(videoManager.getVideo(videoName)));
	}

}
