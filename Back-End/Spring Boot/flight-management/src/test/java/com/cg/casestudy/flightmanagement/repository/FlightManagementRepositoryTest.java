package com.cg.casestudy.flightmanagement.repository;

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

import com.cg.casestudy.flightmanagement.model.Airline;
import com.cg.casestudy.flightmanagement.model.Airport;
import com.cg.casestudy.flightmanagement.model.Fare;
import com.cg.casestudy.flightmanagement.model.Flight;

@ExtendWith(SpringExtension.class)
@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
class FlightManagementRepositoryTest {

	@Autowired
	private FlightManagementRepository flightRepository;

	@BeforeEach
	public void setUp() throws Exception {
		Flight flight = new Flight("DUMMY1", new Airline("", "", ""), new Airport("1", "XXX", ""),
				new Airport("2", "YYY", ""), "2021-04-30", "2021-04-30", "", "",new Fare(), 0);
		flightRepository.save(flight);
	}
	
	@AfterEach
	public void setDown() throws Exception {
		flightRepository.deleteById("DUMMY1");	
	}
	

	@Test
	public void getFlightsTest() throws Exception {

		List<Flight> flightList = flightRepository
				.findByDepartureAirportAirportCodeAndDestinationAirportAirportCodeAndDepartureDate("XXX", "YYY","2021-04-30");

		org.junit.jupiter.api.Assertions.assertEquals(1, flightList.size());
	}

	@Test
	public void getAllFlightsTest() {

		assertThat(flightRepository.findAll()).isNotEmpty();
	}

	@Test
	public void getFlightTest() throws Exception {

		Optional<Flight> response = flightRepository.findById("DUMMY1");

		org.junit.jupiter.api.Assertions.assertEquals("DUMMY1", response.get().getId());
	}

	@Test
	public void addFlightTest() throws Exception {
		Flight flight = new Flight("DUMMY2", new Airline("", "", ""), new Airport("1", "XXX", ""),
				new Airport("2", "YYY", ""), "", "", "", "",new Fare(), 0);

		flightRepository.save(flight);

		Optional<Flight> response = flightRepository.findById("DUMMY2");

		org.junit.jupiter.api.Assertions.assertEquals("DUMMY2", response.get().getId());
		
		flightRepository.deleteById("DUMMY2");	
	}

	@Test
	public void updateFlightTest() throws Exception {
		Flight flight = new Flight("DUMMY1", new Airline("", "", ""), new Airport("1", "XXX", ""),
				new Airport("2", "YYY", ""), "", "", "", "",new Fare(), 100);

		flightRepository.save(flight);
		Optional<Flight> response = flightRepository.findById("DUMMY1");

		org.junit.jupiter.api.Assertions.assertEquals(100, response.get().getSeats());
	}

	@Test
	public void deleteFlightTest() throws Exception {

		flightRepository.deleteById("DYMMY3");
		Optional<Flight> response = flightRepository.findById("DUMMY3");

		org.junit.jupiter.api.Assertions.assertEquals(Optional.empty(), response);
	}

}
