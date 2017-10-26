package com.tanx.expirit.exception;

public class CustomJPAxcetption extends CustomRuntimeException {

	private static final long serialVersionUID = 1L;
	
	public CustomJPAxcetption(String message){
		super(message);
	}

	@Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
