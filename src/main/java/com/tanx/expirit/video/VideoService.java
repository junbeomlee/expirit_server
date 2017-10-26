package com.tanx.expirit.video;

import java.util.List;

import rx.Observable;

public interface VideoService {
	Observable<Video> getVideo(String name);
}
