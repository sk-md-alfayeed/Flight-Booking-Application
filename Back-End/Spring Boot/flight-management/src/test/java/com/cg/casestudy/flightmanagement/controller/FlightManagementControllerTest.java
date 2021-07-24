package com.cg.casestudy.flightmanagement.controller;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.cg.casestudy.flightmanagement.config.SecurityConfiguration;
import com.cg.casestudy.flightmanagement.model.Airline;
import com.cg.casestudy.flightmanagement.model.Airport;
import com.cg.casestudy.flightmanagement.model.Fare;
import com.cg.casestudy.flightmanagement.model.Flight;
import com.cg.casestudy.flightmanagement.model.Search;
import com.cg.casestudy.flightmanagement.service.FlightManagementService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = FlightManagementController.class, excludeAutoConfiguration = SecurityConfiguration.class)
class FlightManagementControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	@MockBean
	private FlightManagementService flightService;

	private List<Flight> flights;

	private Flight flight;

	private Search search;

	@BeforeEach
	void setUp() {

		search = new Search("CCU", "DEL", "2021-04-30");

		flights = List.of(new Flight("CD105", new Airline("1", "AA105", "AirAlpha"), new Airport("1", "CCU", "Kolkata"),
				new Airport("2", "DEL", "Delhi"), "2021-04-30", "2021-04-30", "22:20", "00:20", new Fare(), 100),
				new Flight("CD106", new Airline("1", "AA105", "AirAlpha"), new Airport("1", "CCU", "Kolkata"),
						new Airport("2", "DEL", "Delhi"), "2021-04-30", "2021-04-30", "22:20", "00:20", new Fare(),
						100),
				new Flight("CD107", new Airline("1", "AA105", "AirAlpha"), new Airport("1", "CCU", "Kolkata"),
						new Airport("2", "DEL", "Delhi"), "2021-04-30", "2021-04-30", "22:20", "00:20", new Fare(),
						100));
		flight = new Flight("CD107", new Airline("2", "AB109", "AirBravo"), new Airport("1", "CCU", "Kolkata"),
				new Airport("2", "DEL", "Delhi"), "2021-04-30", "2021-04-30", "12:20", "02:20", new Fare(), 100);
	}

	@Test
	void getFlightsTest() throws Exception {

		Mockito.when(flightService.getFlights(Mockito.any(Search.class))).thenReturn(flights);

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.post("/flights").contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(search).getBytes(StandardCharsets.UTF_8))
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		Assertions.assertThat(result).isNotNull();
		String flightJson = result.getResponse().getContentAsString();
		Assertions.assertThat(flightJson).isEqualToIgnoringCase(mapper.writeValueAsString(flights));
	}

	@Test
	void getAllFlightsTest() throws Exception {

		Mockito.when(flightService.getAllFlights()).thenReturn(flights);

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get("/allFlights").contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		Assertions.assertThat(result).isNotNull();
		String flightJson = result.getResponse().getContentAsString();
		Assertions.assertThat(flightJson).isEqualToIgnoringCase(mapper.writeValueAsString(flights));
	}

	@Test
	void getFlightTest() throws Exception {

		Mockito.when(flightService.getFlight(flight.getId())).thenReturn(Optional.of(flight));

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get("/getFlight/" + flight.getId())
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		Assertions.assertThat(result).isNotNull();
		String flightJson = result.getResponse().getContentAsString();
		Assertions.assertThat(flightJson).isEqualToIgnoringCase(mapper.writeValueAsString(flight));
	}

	@Test
	void addFlightTest() throws Exception {

		Mockito.when(flightService.addFlight(Mockito.any(Flight.class)))
				.thenReturn("Flight and Fare added  with id : " + flight.getId());

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.post("/addFlight").contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(flight).getBytes(StandardCharsets.UTF_8))
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		Assertions.assertThat(result).isNotNull();
		String flightJson = result.getResponse().getContentAsString();
		Assertions.assertThat(flightJson).isNotEmpty();
		Assertions.assertThat(flightJson).isEqualToIgnoringCase("Flight and Fare added  with id : " + flight.getId());
	}

	@Test
	void updateFlightTest() throws Exception {

		Mockito.when(flightService.updateFlight(Mockito.any(Flight.class)))
				.thenReturn("Flight Updated with id : " + flight.getId());

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.put("/updateFlight").contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(flight).getBytes(StandardCharsets.UTF_8))
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		Assertions.assertThat(result).isNotNull();
		String flightJson = result.getResponse().getContentAsString();
		Assertions.assertThat(flightJson).isNotEmpty();
		Assertions.assertThat(flightJson).isEqualToIgnoringCase("Flight Updated with id : " + flight.getId());
	}

	@Test
	void deleteFlightTest() throws Exception {

		Mockito.when(flightService.deleteFlight(flight.getId()))
				.thenReturn("Flight Deleted with id : " + flight.getId());

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.delete("/deleteFlight/" + flight.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(flight).getBytes(StandardCharsets.UTF_8))
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		Assertions.assertThat(result).isNotNull();
		String flightJson = result.getResponse().getContentAsString();
		Assertions.assertThat(flightJson).isNotEmpty();
		Assertions.assertThat(flightJson).isEqualToIgnoringCase("Flight Deleted with id : " + flight.getId());
	}
}
