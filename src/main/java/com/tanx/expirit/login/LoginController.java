package com.tanx.expirit.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tanx.expirit.user.UserDTO;
import com.tanx.expirit.user.UserService;
import com.tanx.expirit.util.DTOMapper;

import lombok.extern.slf4j.Slf4j;
import rx.Single;

@CrossOrigin
@Slf4j
@RestController
public class LoginController {
	@Autowired
	private UserService userService;

	@Autowired
	private FacebookService facebookService;

	/*
	 * '유저 리스트 조회' 'GET' '/users' 'Observable<List<User>>'
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Single<UserDTO> login(HttpServletRequest request, HttpServletResponse response,
			@RequestBody LoginDataDTO loginData) throws Exception {
		
		if (loginData.getLoginType() == null)
			throw new IllegalArgumentException("NO join Type");

		switch (loginData.getLoginType()) {
			case "012001":
				return userService.login(loginData.getEmail(), loginData.getPw(), request)
				.toSingle();
			case "012002":
				return userService.loginByKey(loginData.getEmail(), loginData.getKey(), request)
				.toSingle();
			case "012003":
				return facebookService.getEmail(loginData.getToken())
						.flatMap(user -> userService.loginByToken(user.getEmail(), loginData.getToken(), request))
						.toSingle();
			default:
				throw new RuntimeException("invaild login code");
		}
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().removeAttribute("sessionId");
	}

	@RequestMapping(value = "/signUp", method = RequestMethod.POST)
	public Single<SignUpUserDTO> signUp(@RequestBody SignUpDataDTO signUpDataDTO) {

		switch (signUpDataDTO.getJoinType()) {
		case "002001":
			return userService.signUp(signUpDataDTO.getEmail(), signUpDataDTO.getPw(), signUpDataDTO.getName(), signUpDataDTO.getJoinType())
					.toSingle();
		case "002002":
			return facebookService.getEmail(signUpDataDTO.getToken())
					.flatMap(user->userService.signUpByFaceBook(user.getEmail(), signUpDataDTO.getName(), signUpDataDTO.getJoinType(),signUpDataDTO.getToken()))
					.map(user->DTOMapper.mapper.map(user, SignUpUserDTO.class))
					.toSingle();
			
		default:
			throw new RuntimeException("invaild login code");
		}
	}
}
