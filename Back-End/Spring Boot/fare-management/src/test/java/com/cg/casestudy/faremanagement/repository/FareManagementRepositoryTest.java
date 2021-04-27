package com.cg.casestudy.faremanagement.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.cg.casestudy.faremanagement.model.Fare;

@ExtendWith(SpringExtension.class)
@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
class FareManagementRepositoryTest {

	@Autowired
	private FareManagementRepository fareRepository;

	@BeforeEach
	public void setUp() throws Exception {
		Fare fare = new Fare("CD105", "ABCD", 100);
		fareRepository.save(fare);
	}
	
	@AfterEach
	public void setDown() throws Exception {
		fareRepository.deleteById("CD105");	
	}
	

	@Test
	public void getAllFaresTest() {

		assertThat(fareRepository.findAll()).isNotEmpty();
	}

	@Test
	public void getFareTest() throws Exception {

		Optional<Fare> response = fareRepository.findById("CD105");

		org.junit.jupiter.api.Assertions.assertEquals("CD105", response.get().getId());
	}

	@Test
	public void addFareTest() throws Exception {
		Fare fare = new Fare("CD106", "ABCD", 100);
				

		fareRepository.save(fare);

		Optional<Fare> response = fareRepository.findById("CD106");

		org.junit.jupiter.api.Assertions.assertEquals("CD106", response.get().getId());
		
		fareRepository.deleteById("CD106");	
	}

	@Test
	public void updateFareTest() throws Exception {
		Fare fare = new Fare("CD105", "ABCD", 100);

		fareRepository.save(fare);
		Optional<Fare> response = fareRepository.findById("CD105");

		org.junit.jupiter.api.Assertions.assertEquals(100, response.get().getFlightFare());
	}

	@Test
	public void deleteFareTest() throws Exception {

		fareRepository.deleteById("DYMMY3");
		Optional<Fare> response = fareRepository.findById("DUMMY3");

		org.junit.jupiter.api.Assertions.assertEquals(Optional.empty(), response);
	}
}
