package com.tanx.expirit;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tanx.expirit.user.User;
import com.tanx.expirit.user.UserController;
import com.tanx.expirit.user.program.ProgramDTO;
import com.tanx.expirit.util.DTOMapper;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Single;

@Slf4j
@RestController
public class ExpiritController {

	@Value("${expirit.app.version}")
	public String appVersion;
	
	@Value("${expirit.db.version}")
	public String dbVersion;
	
	@RequestMapping(value="/version",method=RequestMethod.GET)
	public Single<Version> getVersion(){
		return Observable.defer(()->{
			return Observable.just(new Version(appVersion,dbVersion));
		}).toSingle();
	}
}
