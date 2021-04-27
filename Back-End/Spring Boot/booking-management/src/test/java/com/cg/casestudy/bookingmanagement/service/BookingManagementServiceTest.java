package com.cg.casestudy.bookingmanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.cg.casestudy.bookingmanagement.BookingManagementApplication;
import com.cg.casestudy.bookingmanagement.model.Airline;
import com.cg.casestudy.bookingmanagement.model.Airport;
import com.cg.casestudy.bookingmanagement.model.Booking;
import com.cg.casestudy.bookingmanagement.model.Fare;
import com.cg.casestudy.bookingmanagement.model.Flight;
import com.cg.casestudy.bookingmanagement.model.Passenger;
import com.cg.casestudy.bookingmanagement.repository.BookingManagementRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BookingManagementApplication.class)
class BookingManagementServiceTest {

	@Autowired
	private BookingManagementService service;

	@MockBean
	private BookingManagementRepository repository;
	
	

	public static List<Passenger> passengers = List.of(
			new Passenger("123", "", "","",25,"",""),
			new Passenger("4444", "", "","",25,"",""));
	
	public static Flight flight = new Flight("CDAA105", new Airline("1", "AA105", "AirAlpha"),
			new Airport("1", "CCU", "Kolkata"), new Airport("2", "DEL", "Delhi"), "21-04-2021", "21-04-2021", "22:20",
			"00:20",new Fare(), 100);;

	public static Booking booking1 = new Booking("CD105", "", flight, passengers, "", true, "");
	public static Booking booking2 = new Booking("CD106", "", flight, passengers, "", true, "");

	@Test
	public void getAllBookingsTest() {

		when(repository.findAll()).thenReturn(Stream.of(booking1, booking2).collect(Collectors.toList()));
		assertEquals(2, service.getAllBookings().size());
	}

	@Test
	public void getBookingTest() {

		when(repository.findById("CD105")).thenReturn(Optional.of(booking1));
		assertEquals(Optional.of(booking1), service.getBooking("CD105"));
	}

	@Test
	public void addBookingTest() {
		when(repository.save(booking1)).thenReturn(booking1);
		assertEquals("Booking added with id : " + booking1.getId(), service.addBooking(booking1));
	}

	@Test
	public void updateBookingTest() {
		when(repository.save(booking1)).thenReturn(booking1);
		assertEquals("Booking updated with id : " + booking1.getId(), service.updateBooking(booking1));
	}

}
