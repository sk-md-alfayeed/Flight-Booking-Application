package com.cg.casestudy.bookingmanagement.controller;

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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.cg.casestudy.bookingmanagement.model.Booking;
import com.cg.casestudy.bookingmanagement.model.Flight;
import com.cg.casestudy.bookingmanagement.model.Passenger;
import com.cg.casestudy.bookingmanagement.service.BookingManagementService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(BookingManagementController.class)
class BookingManagementControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	@MockBean
	private BookingManagementService bookingService;;

	private List<Booking> bookings;

	private Booking booking;

	@BeforeEach
	void setUp() {

		List<Passenger> passengers = null;
		Flight flight = null;

		bookings = List.of(new Booking("CD105", "", flight, passengers, "", true, ""),
				new Booking("CD106", "", flight, passengers, "", true, ""),
				new Booking("CD107", "", flight, passengers, "", true, ""));
		booking = new Booking("CD108", "", flight, passengers,"", true, "");
	}

	@Test
	@WithMockUser(username = "admin", roles={"ADMIN"})
	void getBookingsTest() throws Exception {

		Mockito.when(bookingService.getBookingsByEmail(Mockito.any(String.class))).thenReturn(bookings);

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.post("/booking/bookings").contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString("").getBytes(StandardCharsets.UTF_8))
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();

		Assertions.assertThat(result).isNotNull();

	}

	@Test
	@WithMockUser(username = "admin", roles={"ADMIN"})
	void getAllBookingsTest() throws Exception {

		Mockito.when(bookingService.getAllBookings()).thenReturn(bookings);

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get("/booking/allBookings").contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		Assertions.assertThat(result).isNotNull();
		String bookingJson = result.getResponse().getContentAsString();
		Assertions.assertThat(bookingJson).isEqualToIgnoringCase(mapper.writeValueAsString(bookings));
	}

	@Test
	@WithMockUser(username = "admin", roles={"ADMIN"})
	void getBookingTest() throws Exception {

		Mockito.when(bookingService.getBooking(booking.getId())).thenReturn(Optional.of(booking));

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get("/booking/getBooking/" + booking.getId())
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		Assertions.assertThat(result).isNotNull();
		String bookingJson = result.getResponse().getContentAsString();
		Assertions.assertThat(bookingJson).isEqualToIgnoringCase(mapper.writeValueAsString(booking));
	}

	@Test
	@WithMockUser(username = "admin", roles={"ADMIN"})
	void addBookingTest() throws Exception {

		Mockito.when(bookingService.addBooking(Mockito.any(Booking.class)))
				.thenReturn("Booking Added with: " + booking.getId());

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.post("/booking/addBooking").contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(booking).getBytes(StandardCharsets.UTF_8))
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();

		Assertions.assertThat(result).isNotNull();
		String bookingJson = result.getResponse().getContentAsString();
		Assertions.assertThat(bookingJson).isNotEmpty();

	}

	@Test
	@WithMockUser(username = "admin", roles={"ADMIN"})
	void updateBookingTest() throws Exception {

		Mockito.when(bookingService.updateBooking(Mockito.any(Booking.class)))
				.thenReturn("Booking Updated with: " + booking.getId());

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.put("/booking/updateBooking").contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(booking).getBytes(StandardCharsets.UTF_8))
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();

		Assertions.assertThat(result).isNotNull();
		String bookingJson = result.getResponse().getContentAsString();
		Assertions.assertThat(bookingJson).isNotEmpty();

	}

	@Test
	@WithMockUser(username = "admin", roles={"ADMIN"})
	void deleteBookingTest() throws Exception {

		Mockito.when(bookingService.deleteBooking(booking.getId()))
				.thenReturn("Booking and CheckIn deleted with Flight Id : " + booking.getId());

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.delete("/booking/deleteBooking/" + booking.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(booking).getBytes(StandardCharsets.UTF_8))
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		Assertions.assertThat(result).isNotNull();
		String bookingJson = result.getResponse().getContentAsString();
		Assertions.assertThat(bookingJson).isNotEmpty();
		Assertions.assertThat(bookingJson)
				.isEqualToIgnoringCase("Booking and CheckIn deleted with Flight Id : " + booking.getId());
	}
}
