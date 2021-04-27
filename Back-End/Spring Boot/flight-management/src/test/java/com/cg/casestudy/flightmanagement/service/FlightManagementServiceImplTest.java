
package com.cg.casestudy.flightmanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.cg.casestudy.flightmanagement.FlightManagementApplication;
import com.cg.casestudy.flightmanagement.model.Airline;
import com.cg.casestudy.flightmanagement.model.Airport;
import com.cg.casestudy.flightmanagement.model.Fare;
import com.cg.casestudy.flightmanagement.model.Flight;
import com.cg.casestudy.flightmanagement.model.Search;
import com.cg.casestudy.flightmanagement.repository.FlightManagementRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = FlightManagementApplication.class)
class FlightManagementServiceImplTest {

	@Autowired
	private FlightManagementService service;

	@MockBean
	private FlightManagementRepository repository;

	public static Flight flight1 = new Flight("CDAA105", new Airline("1", "AA105", "AirAlpha"),
			new Airport("1", "CCU", "Kolkata"), new Airport("2", "DEL", "Delhi"), "21-04-2021", "21-04-2021", "22:20",
			"00:20",new Fare(), 100);
	public static Flight flight2 = new Flight("CBAB102", new Airline("2", "AB102", "AirBravo"),
			new Airport("1", "CCU", "Kolkata"), new Airport("3", "BOM", "Mumbai"), "22-05-2021", "21-05-2021", "21:20",
			"23:40",new Fare(), 150);

	@Test
	public void getFlightsTest() {

		when(repository.findByDepartureAirportAirportCodeAndDestinationAirportAirportCodeAndDepartureDate("DEL", "BOM", "2021-0-30"))
				.thenReturn(Stream.of(flight2).collect(Collectors.toList()));
		assertEquals(1, service.getFlights(new Search("DEL", "BOM", "2021-0-30")).size());
	}

	@Test
	public void getAllFlightsTest() {

		when(repository.findAll()).thenReturn(Stream.of(flight1, flight2).collect(Collectors.toList()));
		assertEquals(2, service.getAllFlights().size());
	}

	@Test
	public void getFlightTest() {

		when(repository.findById("CD105")).thenReturn(Optional.of(flight1));
		assertEquals(Optional.of(flight1), service.getFlight("CD105"));
	}

	@Test
	public void addFlightTest() {
		when(repository.save(flight1)).thenReturn(flight1);
		assertEquals("Flight and Fare added with id : " + flight1.getId(), service.addFlight(flight1));
	}
	@Test
	public void updateFlightTest() {
		when(repository.save(flight1)).thenReturn(flight1);
		assertEquals("Fare updated with id : " + flight1.getId(), service.updateFlight(flight1));
	}

	@Test
	public void deleteFlightTest() {
		service.deleteFlight(flight1.getId());
		verify(repository, times(1)).deleteById(flight1.getId());
	}

}
