package com.tanx.expirit.util;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.tanx.expirit.exercise.Exercise;
import com.tanx.expirit.user.User;

@Aspect
@Component
public class MyLogger {
	private final Log log = LogFactory.getLog(getClass());
	
	@Before("execution(* com.tanx.expirit.*.*Controller.*(..))")
	public void logMethodAccessBeforeController(JoinPoint joinPoint) {
        log.info("***** Starting: " + joinPoint.getSignature().getName() + " *****");
        for(Object obj : joinPoint.getArgs()){
        	if(obj==null)
        		
        		continue;
        	if(obj instanceof User){
        		log.info("User:"+((User)obj).getEmail());
        		continue;
        	}
        	
        	if(obj instanceof Exercise[]){
        		log.info("exerciseList");
        		continue;
        	}
        	log.info(obj.toString());
	    }
    }
//	
//	@After("execution(* com.tanx.expirit.*.*Controller.*(..))")
//	public void logMethodAccessAfterController(JoinPoint joinPoint) {
//        log.info("***** Ending: " + joinPoint.getSignature().getName() + " *****");
//        for(Object obj : joinPoint.getArgs()){
//        	if(obj!=null)
//        		log.info(obj.toString());
//	    }
//    }
	
//	@Before("execution(* com.tanx.expirit.*.*Service*.*(..))")
//	public void logMethodAccessBeforeService(JoinPoint joinPoint) {
//        log.info("***** Starting: " + joinPoint.getSignature().getName() + " *****");
//        for(Object obj : joinPoint.getArgs()){
//        	if(obj!=null)
//        		log.info(obj.toString());
//	    }
//    }
//	
//	@After("execution(* com.tanx.expirit.*.*Service*.*(..))")
//	public void logMethodAccessAfterService(JoinPoint joinPoint) {
//        log.info("***** Ending: " + joinPoint.getSignature().getName() + " *****");
//        for(Object obj : joinPoint.getArgs()){
//        	if(obj!=null)
//        		log.info(obj.toString());
//	    }
//    }
}
