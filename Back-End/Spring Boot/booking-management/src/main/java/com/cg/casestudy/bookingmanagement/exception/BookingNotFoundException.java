package com.cg.casestudy.bookingmanagement.exception;

public class BookingNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BookingNotFoundException() {
		super();
	}

	public BookingNotFoundException(String message) {
		super(message);

	}

}
