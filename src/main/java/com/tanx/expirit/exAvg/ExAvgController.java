package com.tanx.expirit.exAvg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tanx.expirit.RestControllerTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/exAvgs")
public class ExAvgController extends RestControllerTemplate<ExAvg,ExAvg, Integer>{
	
	@Autowired
	public ExAvgController(ExAvgRepository exAvgRepository) {
		super(exAvgRepository, ExAvg.class, ExAvg.class);
	}
}
