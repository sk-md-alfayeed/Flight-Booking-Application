package com.cg.casestudy.faremanagement.exception;

public class FareNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FareNotFoundException() {
		super();
	}

	public FareNotFoundException(String message) {
		super(message);

	}

}
