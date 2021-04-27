package com.cg.casestudy.faremanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.cg.casestudy.faremanagement.FareManagementApplication;
import com.cg.casestudy.faremanagement.model.Fare;
import com.cg.casestudy.faremanagement.repository.FareManagementRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = FareManagementApplication.class)
class FareManagementServiceTest {

	@Autowired
	private FareManagementService service;

	@MockBean
	private FareManagementRepository repository;

	public static Fare fare1 = new Fare("CD105", "ABCD", 100);
	public static Fare fare2 = new Fare("CD106", "ABCD", 100);

	

	@Test
	public void getAllFaresTest() {

		when(repository.findAll()).thenReturn(Stream.of(fare1, fare2).collect(Collectors.toList()));
		assertEquals(2, service.getAllFares().size());
	}

	@Test
	public void getFareTest() {

		when(repository.findById("CD105")).thenReturn(Optional.of(fare1));
		assertEquals(Optional.of(fare1), service.getFare("CD105"));
	}

	@Test
	public void addFareTest() {
		when(repository.save(fare1)).thenReturn(fare1);
		assertEquals("Fare added with id : " + fare1.getId(), service.addFare(fare1));
	}
	@Test
	public void updateFareTest() {
		when(repository.save(fare1)).thenReturn(fare1);
		assertEquals("Fare updated with id : " + fare1.getId(), service.updateFare(fare1));
	}
}
