package com.tanx.expirit.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.tanx.expirit.exception.unAuthorizedException;

@Component
public class ExpiritSessionInterceptor implements AsyncHandlerInterceptor {

	static final String LOCALHOST = "127.0.0.1";
	
	@Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //System.out.println("afterConcurrentHandlingStarted " + request.getRequestURI());
    }
	/*
	 * (non-Javadoc)
	 * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 * "/login ->인경우를 제외하고 다 session검사를 한다.
	 */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
    	
    	if(BulkRequestChecker.check(request))
    		return true;
    	
    	if(SessionChecker.check(request))
    		return true;
    	
    	throw new unAuthorizedException("no auth");
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //System.out.println("postHandle " + request.getRequestURI());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //System.out.println("afterCompletion " + request.getRequestURI());
    }
    
    public static class BulkRequestChecker {

    	public static boolean check(HttpServletRequest httpRequest){
    		
    		if(httpRequest.getRemoteAddr().equals(LOCALHOST)){
    			String email=httpRequest.getHeader("email");
        		if(email!=null){
        			return true;
        		}else{
        			return false;
        		}
    		}
    		
    		return true;
    	}
    }
    
    public static class SessionChecker {

    	public static boolean check(HttpServletRequest httpRequest){
    		
    		HttpSession httpSession=httpRequest.getSession();
    		
    	   	if (httpSession.getAttribute("sessionId") != null) {
        		return true;
    	    }
    		return false;
    	}
    }

	

}
