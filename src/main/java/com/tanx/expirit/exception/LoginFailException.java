package com.tanx.expirit.exception;

public class LoginFailException extends CustomRuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public LoginFailException(String message) {
		super(message);
	}
}
