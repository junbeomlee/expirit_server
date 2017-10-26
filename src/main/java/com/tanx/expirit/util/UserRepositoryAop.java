package com.tanx.expirit.util;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tanx.expirit.user.User;
import com.tanx.expirit.user.UserRepository;

@Aspect
@Component
public class UserRepositoryAop {

	@Autowired
	HttpSession httpSession;

	@Autowired
	UserRepository userRepository;

	/*
	 * 세션에부착 되어있는 user를 가져온다. 그리고 session에 저장한 user를 업데이트
	 */
	@Around("execution(* com.tanx.expirit.user.UserRepository.save(..))")
	public Object writeFormAspect(ProceedingJoinPoint joinPoint) throws Throwable {

		Object retVal = null;
		// User updateUser=null;
		User user = null;
		for (Object obj : joinPoint.getArgs()) {
			if (obj instanceof User) {
				user = (User) obj;
				break;
			}
		}
		
		retVal = joinPoint.proceed();
		//userRepository.flush();
		System.out.println("session");
		httpSession.setAttribute("sessionId", user);

		return retVal;
	}
}
