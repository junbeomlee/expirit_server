package com.tanx.expirit.bulk;

/*
*
* Copyright 2016 Wei-Ming Wu
*
* Licensed under the Apache License, Version 2.0 (the "License"); you may not
* use this file except in compliance with the License. You may obtain a copy of
* the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
* WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
* License for the specific language governing permissions and limitations under
* the License.
*
*/

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.RequestEntity.BodyBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.tanx.expirit.exception.ExceptionDTO;
import com.tanx.expirit.user.User;

@Service
public class BulkServiceImpl implements BulkService {

	@Autowired
	HttpSession httpSession;
	
	@Override
	public BulkResponse bulk(BulkRequest req, HttpServletRequest servReq) {
		// TODO Auto-generated method stub

		List<BulkResult> results = new ArrayList<BulkResult>();

		RestTemplate template = new RestTemplate();
		for (BulkOperation op : req.getOperations()) {
			BodyBuilder bodyBuilder = RequestEntity.method(//
					httpMethod(op.getMethod()), computeUri(servReq, op));
			
			op.getHeaders().put("email", ((User)httpSession.getAttribute("sessionId")).getEmail());
			
			ResponseEntity<Object> rawRes = null;
			try {
				rawRes = template.exchange(requestEntity(bodyBuilder, op), Object.class);
				
			}catch(HttpClientErrorException exception){
				
				ExceptionDTO exceptionDTO = new ExceptionDTO();
				exceptionDTO.setCode(exception.getStatusCode().value());
				exceptionDTO.setMessage(exception.getResponseBodyAsString());
				rawRes = new ResponseEntity<Object>(exceptionDTO,exception.getStatusCode());
			}
			
			if (!op.isSilent()&&rawRes!=null)
				results.add(buldResult(rawRes));
		}
		
		return new BulkResponse(results);
	}

	private RequestEntity<Object> requestEntity(BodyBuilder bodyBuilder, BulkOperation op) {
		for (Entry<String, String> header : op.getHeaders().entrySet()) {
			bodyBuilder.header(header.getKey(), header.getValue());
		}
		return bodyBuilder.body(op.getBody());
	}

	private BulkResult buldResult(ResponseEntity<Object> rawRes) {
		BulkResult res = new BulkResult();
		res.setStatus(Short.valueOf(rawRes.getStatusCode().toString()));
		res.setHeaders(rawRes.getHeaders().toSingleValueMap());
		res.setBody(rawRes.getBody());

		return res;
	}

	private static HttpMethod httpMethod(String method) {
		try {
			return HttpMethod.valueOf(method.toUpperCase());
		} catch (Exception e) {
			return HttpMethod.GET;
		}
	}

	private URI computeUri(HttpServletRequest servReq, BulkOperation op) {
		String rawUrl = servReq.getRequestURL().toString();
		String rawUri = servReq.getRequestURI().toString();

		if (op.getUrl() == null) {
			// throw new BulkApiException(UNPROCESSABLE_ENTITY, "Invalid URL(" +
			// rawUri + ") exists in this bulk request");
		}

		URI uri = null;
		try {
			String servletPath = rawUrl.substring(0, rawUrl.indexOf(rawUri));
			uri = new URI(servletPath + urlify(op.getUrl()));
		} catch (URISyntaxException e) {
			// throw new BulkApiException(UNPROCESSABLE_ENTITY,
			// "Invalid URL(" + urlify(op.getUrl()) + ") exists in this bulk
			// request");
		}

		return uri;
	}

	private String urlify(String url) {
		url = url.trim();
		return url.startsWith("/") ? url : "/" + url;
	}

}
