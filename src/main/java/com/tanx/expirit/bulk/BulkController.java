package com.tanx.expirit.bulk;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tanx.expirit.user.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class BulkController {
	
	@Autowired
	BulkService bulkService;
	
	
	@RequestMapping(value="/bulk",method=RequestMethod.POST)
	BulkResponse bulk(@RequestBody BulkRequest req, HttpServletRequest servReq,User user) {
	    return bulkService.bulk(req, servReq);
	}
}