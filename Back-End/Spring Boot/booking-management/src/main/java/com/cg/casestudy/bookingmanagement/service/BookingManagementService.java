package com.cg.casestudy.bookingmanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cg.casestudy.bookingmanagement.exception.BookingNotFoundException;
import com.cg.casestudy.bookingmanagement.exception.IdNotFoundException;
import com.cg.casestudy.bookingmanagement.model.Booking;
import com.cg.casestudy.bookingmanagement.model.Discount;
import com.cg.casestudy.bookingmanagement.model.Flight;

@Service
public interface BookingManagementService {

	public Optional<Flight> getFlight(String flightId);

	public List<Booking> getBookingsByEmail(String email) throws BookingNotFoundException;

	public List<Booking> getAllBookings() throws BookingNotFoundException;

	public Optional<Booking> getBooking(String bookingId) throws IdNotFoundException;

	public String addBooking(Booking booking);

	public String updateBooking(Booking booking);

	public String deleteBooking(String bookingId) throws IdNotFoundException;

	public Discount discount(Discount discount);

}
