package com.cg.casestudy.bookingmanagement.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.cg.casestudy.bookingmanagement.model.Booking;
import com.cg.casestudy.bookingmanagement.model.Flight;
import com.cg.casestudy.bookingmanagement.model.Passenger;

@ExtendWith(SpringExtension.class)
@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
class BookingManagementRepositoryTest {

	@Autowired
	private BookingManagementRepository bookingRepository;

	@BeforeEach
	public void setUp() throws Exception {

		List<Passenger> passengers = null;
		Flight flight = null;
		Booking booking = new Booking("CD105", "", flight, passengers, "", true, "");
		bookingRepository.save(booking);
	}

	@AfterEach
	public void setDown() throws Exception {
		bookingRepository.deleteById("CD105");
	}

	@Test
	public void getAllBookingsTest() {

		assertThat(bookingRepository.findAll()).isNotEmpty();
	}

	@Test
	public void getBookingTest() throws Exception {

		Optional<Booking> response = bookingRepository.findById("CD105");

		org.junit.jupiter.api.Assertions.assertEquals("CD105", response.get().getId());
	}

	@Test
	public void addBookingTest() throws Exception {
		List<Passenger> passengers = null;
		Flight flight = null;
		Booking booking = new Booking("CD106", "", flight, passengers, "", true, "");

		bookingRepository.save(booking);

		Optional<Booking> response = bookingRepository.findById("CD106");

		org.junit.jupiter.api.Assertions.assertEquals("CD106", response.get().getId());

		bookingRepository.deleteById("CD106");
	}

	@Test
	public void updateBookingTest() throws Exception {
		List<Passenger> passengers = null;
		Flight flight = null;
		Booking booking = new Booking("CD105", "", flight, passengers, "", true, "");

		bookingRepository.save(booking);
		Optional<Booking> response = bookingRepository.findById("CD105");

		org.junit.jupiter.api.Assertions.assertEquals("", response.get().getEmail());
	}

	@Test
	public void deleteBookingTest() throws Exception {

		bookingRepository.deleteById("DYMMY3");
		Optional<Booking> response = bookingRepository.findById("DUMMY3");

		org.junit.jupiter.api.Assertions.assertEquals(Optional.empty(), response);
	}

}
