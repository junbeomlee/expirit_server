package com.tanx.expirit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.tanx.expirit.exception.unAuthorizedException;
import com.tanx.expirit.user.User;
import com.tanx.expirit.user.UserRepository;

@Component
public class UserResolver implements HandlerMethodArgumentResolver {

	private UserRepository userRepository;

	public UserResolver(UserRepository userRepository) {
		this.userRepository=userRepository;
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		// TODO Auto-generated method stub
		return parameter.getParameterType().equals(User.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

		HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
		HttpSession httpSession = request.getSession();
		User user = (User) httpSession.getAttribute("sessionId");
		
		
		if (user == null) {
			String email = request.getHeader("email");
			if(email==null)
				throw new unAuthorizedException("except on user resolver");
			else
				return userRepository.getFromSession(email);
		} else {
			return userRepository.getFromSession(user.getEmail());
		}

	}

}
