package com.tanx.expirit.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ByteArrayResource;

import com.tanx.expirit.exception.NotFoundIDException;
import com.tanx.expirit.video.Video;

@Configuration
public class VideoConfig {
	
	@Value("${video.path}")
	public String videoHomePath;
	
	@Value("${video.format")
	public String videoFormat;
	
	@Bean
	public VideoManager videoManager(){

		/*
		 * url or filebased
		 */
		if(isValidURL(videoHomePath))
			return new VideoManager(videoHomePath,videoFormat);
		else
			return new VideoManager(System.getProperty("user.dir")+videoHomePath,videoFormat);

	}
	/*
	 * issue static resource server로 video file 옮기기
	 */
	public class VideoManager{
		
		private String videoHomePath;
		private String videoFormat;

		public VideoManager(String path,String videoFormat){
			this.videoHomePath=path;
			this.videoFormat=videoFormat;
		}
		
		public Video getVideo(String videoName){
			URL url = null;
			InputStream in;
			ByteArrayResource resource = null;
			try {
				url = new URL(videoHomePath+"/"+nameExtension(videoName));
				in = url.openStream();
				byte[] imageBytes=IOUtils.toByteArray(in);
				resource =new ByteArrayResource(imageBytes);
				return new Video(videoName,resource);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new NotFoundIDException("no available video");
			}
		}
	}
	
	static public boolean isValidURL(String urlStr) {
	    try {
	      URI uri = new URI(urlStr);
	      return uri.getScheme().equals("http") || uri.getScheme().equals("https");
	    }
	    catch (Exception e) {
	        return false;
	    }
	}
	
	static public String nameExtension(String videoName){
		if(videoName.endsWith(".mp4"))
			return videoName;
		else
			return videoName.concat(".mp4");
	}
}
