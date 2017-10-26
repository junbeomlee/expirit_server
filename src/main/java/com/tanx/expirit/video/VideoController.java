package com.tanx.expirit.video;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tanx.expirit.exception.DuplicatedVideoNameException;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;

@CrossOrigin
@Slf4j
@RestController
public class VideoController {

	@Autowired
	VideoService videoService;

	@RequestMapping(path = "/videos/{videoName}", method = RequestMethod.GET)
	public ResponseEntity<ByteArrayResource> download(@PathVariable("videoName") String videoName)
			throws IOException, DuplicatedVideoNameException {

		return videoService.getVideo(videoName).map(video -> {
			HttpHeaders httpHeaders = new HttpHeaders();
			return ResponseEntity.ok().headers(httpHeaders).contentLength(video.getContent().contentLength())
					.contentType(MediaType.parseMediaType("application/octet-stream")).body(video.getContent());
		}).toBlocking().single();
	}
}
