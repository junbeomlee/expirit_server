package com.tanx.expirit.exception;

public class CustomRuntimeException extends RuntimeException{

	/**
	 * 
	 */
	public CustomRuntimeException(String message){
		super(message);
	}

	@Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
