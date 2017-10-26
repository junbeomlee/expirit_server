package com.tanx.expirit.video;

import java.io.File;

import org.springframework.core.io.ByteArrayResource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Video {

	public String videoName;
	public ByteArrayResource content;
}
