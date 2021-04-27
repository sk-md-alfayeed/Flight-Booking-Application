package com.cg.casestudy.faremanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cg.casestudy.faremanagement.exception.FareNotFoundException;
import com.cg.casestudy.faremanagement.exception.IdNotFoundException;
import com.cg.casestudy.faremanagement.model.AuthRequest;
import com.cg.casestudy.faremanagement.model.Fare;
import com.cg.casestudy.faremanagement.model.Flight;
import com.cg.casestudy.faremanagement.repository.FareManagementRepository;

@Service
@RefreshScope
public class FareManagementServiceImpl implements FareManagementService {

	@Autowired
	FareManagementRepository fareManagementRepository;

	// Creating AuthRequest object
	AuthRequest auth = new AuthRequest("admin", "1234");

	@Autowired
	@Lazy
	private RestTemplate restTemplate;

	// Getting 'All Fare List' from FareReopsitory
	@Override
	public List<Fare> getAllFares() {

		List<Fare> fareList = fareManagementRepository.findAll();
		if (fareList.isEmpty()) {
			throw new FareNotFoundException("No Fare available");
		}
		return fareList;
	}

	// Getting 'Fare object/Optional' from FareReopsitory
	@Override
	public Optional<Fare> getFare(String id) {
		

		Optional<Fare> fare = fareManagementRepository.findById(id);
		if (!fare.isPresent()) {
			throw new IdNotFoundException("Id not found");
		}
		return fare;
	}

	// Adding 'Fare' to database using FareFareReopsitory
	@Override
	public String addFare(Fare fare) {

		fareManagementRepository.save(fare);
		return "Fare added with Flight Id : "+ fare.getId();
	}

	// Updating 'Fare' in database using FareReopsitory
	@Override
	public String updateFare(Fare fare) {

		fareManagementRepository.save(fare);
		try {
			Flight flight = restTemplate.getForObject("http://flight-management/flight" + "/getFlight/" + fare.getId(),
					Flight.class);
			System.out.println(flight);
			try {
				flight.setFare(fare);
				String tokenUpdateFlight = restTemplate.postForObject("http://flight-management/flight/authenticate", auth,
						String.class);

				// Creating Header with token
				HttpHeaders headersUpdateFlight = new HttpHeaders();
				headersUpdateFlight.setContentType(MediaType.APPLICATION_JSON);
				headersUpdateFlight.set("Authorization", "Bearer " + tokenUpdateFlight);
				// Creating Http Entity
				HttpEntity<Flight> entityUpdateFlight = new HttpEntity<Flight>(flight, headersUpdateFlight);
				System.out.println(entityUpdateFlight);
				
				try {
					// Adding the default Fare to Fare MS by REST call
				
					restTemplate.exchange("http://flight-management/flight" + "/updateFlight",
							HttpMethod.PUT, entityUpdateFlight, String.class);
					
					return "Fare and Flight updated with id : " + fare.getId();
				} catch (Exception e) {

					return "Flight not updated because of [" + e + "], but Fare added with Flight Id : " + fare.getId();
				}
			} catch (Exception e) {
				return "Fare updated but Unable to authenticate to Flight MS because of [" + e + "]";
			}
		} catch (Exception e) {
			
			return "Fare updated but, Unable to find Flight with flightId " + fare.getId() + ", because of [" + e + "]";
		}

	}

	// Deleting 'Fare' by Flight Id in database using FareReopsitory
	@Override
	public String deleteFare(String id) {

		if (fareManagementRepository.existsById(id)) {
			fareManagementRepository.deleteById(id);
			return "Fare deleted with Id : " + id;
		} else {
			throw new IdNotFoundException("Id not exist");
		}

	}

}
