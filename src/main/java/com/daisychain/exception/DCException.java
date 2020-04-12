package com.daisychain.exception;

public class DCException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2313930899818920058L;

	public DCException() {
		super();
	}

	public DCException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DCException(String message, Throwable cause) {
		super(message, cause);
	}

	public DCException(String message) {
		super(message);
	}

	public DCException(Throwable cause) {
		super(cause);
	}

}
