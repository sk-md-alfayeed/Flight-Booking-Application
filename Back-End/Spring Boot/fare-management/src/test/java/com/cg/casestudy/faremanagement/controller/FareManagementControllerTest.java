package com.cg.casestudy.faremanagement.controller;

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

import com.cg.casestudy.faremanagement.model.Fare;
import com.cg.casestudy.faremanagement.service.FareManagementService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(FareManagementController.class)
class FareManagementControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	@MockBean
	private FareManagementService fareService;

	private List<Fare> fares;

	private Fare fare;


	@BeforeEach
	void setUp() {

		fares = List.of(
				new Fare("CD105", "ABCD", 100),
				new Fare("CD106", "ABCD", 100),
				new Fare("CD107", "ABCD", 100));
		fare = new Fare("CD108", "ABCD", 100);
	}



	@Test
	void getAllFaresTest() throws Exception {

		Mockito.when(fareService.getAllFares()).thenReturn(fares);

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get("/fare/allFares").contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		Assertions.assertThat(result).isNotNull();
		String fareJson = result.getResponse().getContentAsString();
		Assertions.assertThat(fareJson).isEqualToIgnoringCase(mapper.writeValueAsString(fares));
	}

	@Test
	void getFareTest() throws Exception {

		Mockito.when(fareService.getFare(fare.getId())).thenReturn(Optional.of(fare));

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get("/fare/getFare/" + fare.getId())
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		Assertions.assertThat(result).isNotNull();
		String fareJson = result.getResponse().getContentAsString();
		Assertions.assertThat(fareJson).isEqualToIgnoringCase(mapper.writeValueAsString(fare));
	}

	@Test
	void addFareTest() throws Exception {


		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.post("/fare/addFare").contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(fare).getBytes(StandardCharsets.UTF_8))
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		Assertions.assertThat(result).isNotNull();
		String fareJson = result.getResponse().getContentAsString();
		Assertions.assertThat(fareJson).isNotEmpty();
	}

	@Test
	void updateFareTest() throws Exception {


		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.put("/fare/updateFare").contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(fare).getBytes(StandardCharsets.UTF_8))
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		Assertions.assertThat(result).isNotNull();
		String fareJson = result.getResponse().getContentAsString();
		Assertions.assertThat(fareJson).isNotEmpty();
	}

	@Test
	void deleteFareTest() throws Exception {

		Mockito.when(fareService.deleteFare(fare.getId()))
				.thenReturn("Fare Deleted with id : " + fare.getId());

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.delete("/fare/deleteFare/" + fare.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(fare).getBytes(StandardCharsets.UTF_8))
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		Assertions.assertThat(result).isNotNull();
		String fareJson = result.getResponse().getContentAsString();
		Assertions.assertThat(fareJson).isNotEmpty();
		Assertions.assertThat(fareJson).isEqualToIgnoringCase("Fare Deleted with id : " + fare.getId());
	}

}
