package com.tanx.expirit.video;

import java.util.List;

import rx.Observable;

public interface VideoRepository {
	Observable<Video> getVideo(String name);
}
