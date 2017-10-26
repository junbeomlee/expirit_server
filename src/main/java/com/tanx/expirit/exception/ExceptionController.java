package com.tanx.expirit.exception;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
@RestController
public class ExceptionController {

	@ExceptionHandler(value = LoginFailException.class)
	public ExceptionDTO loginFailException(LoginFailException e, HttpServletResponse response) {
		response.setStatus(ExceptionStatus.LOGIN_FAIL);
		return new ExceptionDTO(e.getMessage(), ExceptionStatus.LOGIN_FAIL);
	}
	
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = CustomJPAxcetption.class)
	public ExceptionDTO customJPAxcetption(CustomJPAxcetption e, HttpServletResponse response) {
		return new ExceptionDTO(e.getMessage(), ExceptionStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(value = unAuthorizedException.class)
	public ExceptionDTO unAuthorizedException(unAuthorizedException e) {
		return new ExceptionDTO(e.getMessage(), ExceptionStatus.UNAUTHROIZED);
	}
	
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
	public ExceptionDTO unSupportedRequestMethodException(HttpRequestMethodNotSupportedException e) {
		return new ExceptionDTO(e.getMessage(), ExceptionStatus.INVAILD_REQUEST_METHOD);
	}
	
	@ExceptionHandler(value = NotFoundIDException.class)
	public ExceptionDTO NotFoundIDException(NotFoundIDException e, HttpServletResponse response) {
		response.setStatus(ExceptionStatus.NOT_FOUND_ID);
		return new ExceptionDTO(e.getMessage(), ExceptionStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = CustomRuntimeException.class)
	public ExceptionDTO runtimeException(CustomRuntimeException e, HttpServletResponse response) {
		response.setStatus(ExceptionStatus.BAD_REQUEST);
		return new ExceptionDTO(e.getMessage(), ExceptionStatus.BAD_REQUEST);
	}
}
