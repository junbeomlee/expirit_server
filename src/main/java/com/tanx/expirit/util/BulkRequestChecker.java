package com.tanx.expirit.util;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;

@Component
public class BulkRequestChecker {

	public boolean check(HttpRequest httpRequest){
		HttpHeaders headers=httpRequest.getHeaders();
		List<String> header=headers.get("email");
		if(header.get(0)!=null){
			System.out.println(header.get(0));
		}
		return true;
	}
}
