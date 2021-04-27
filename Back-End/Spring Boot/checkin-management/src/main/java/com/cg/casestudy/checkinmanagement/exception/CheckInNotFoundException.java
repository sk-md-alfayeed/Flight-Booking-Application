package com.cg.casestudy.checkinmanagement.exception;

public class CheckInNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CheckInNotFoundException() {
		super();
	}

	public CheckInNotFoundException(String message) {
		super(message);

	}

}
