package com.tanx.expirit.domain;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.core.io.ByteArrayResource;

public class VideoManagerTest {
	
	@Test
	public void getFileTest(){
		URL url = null;
		InputStream in;
		ByteArrayResource resource = null;
		try {
			url = new URL("http://expirit.co.kr/videos"+"/"+"air_squat.mp4");
			in = url.openStream();
			byte[] imageBytes=IOUtils.toByteArray(in);
			resource =new ByteArrayResource(imageBytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(resource);
//		File file = new File(url.getFile());
//		Path path = Paths.get(file.getAbsolutePath());
//		System.out.println(path);

//		try {
//			resource = new ByteArrayResource(Files.readAllBytes(path));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			throw new NotFoundIDException("No matched video");
//		}
//		System.out.println(resource);
//		assertNotNull(file);
		//System.out.println(new ArrayList<File>(Arrays.asList(new File("http://expirit.co.kr/videos").listFiles())));
	}
	
	@Test
	public void nameExtensionTest(){
		assertEquals("asd.mp4",nameExtension("asd.mp4"));
	}
	
	static public String nameExtension(String videoName){
		if(videoName.endsWith(".mp4"))
			return videoName;
		else
			return videoName.concat(".mp4");
	}
}
