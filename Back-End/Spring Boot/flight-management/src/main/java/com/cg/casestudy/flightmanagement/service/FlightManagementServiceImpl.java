package com.cg.casestudy.flightmanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cg.casestudy.flightmanagement.config.RabbitMQConfig;
import com.cg.casestudy.flightmanagement.exception.FlightNotFoundException;
import com.cg.casestudy.flightmanagement.exception.IdNotFoundException;
import com.cg.casestudy.flightmanagement.model.AuthRequest;
import com.cg.casestudy.flightmanagement.model.Fare;
import com.cg.casestudy.flightmanagement.model.Flight;
import com.cg.casestudy.flightmanagement.model.Search;
import com.cg.casestudy.flightmanagement.repository.FlightManagementRepository;

@Service
@RefreshScope
public class FlightManagementServiceImpl implements FlightManagementService {

	@Autowired
	RabbitTemplate rabbitTemplate;

	@Autowired
	private FlightManagementRepository flightManagementRepository;

	@Autowired
	@Lazy
	private RestTemplate restTemplate;

	// Creating AuthRequest object
	AuthRequest auth = new AuthRequest("admin", "1234");

//	//(space between '$' and '{' is important)
//	@Value("$ {microservice.fare-management.endpoints.endpoint.uri}")
//    private String FARE_ENDPOINT_URL;

	// Get flights by custom search
	public List<Flight> getFlights(Search search) {

		// Custom search
		List<Flight> searchedList = flightManagementRepository
				.findByDepartureAirportAirportCodeAndDestinationAirportAirportCodeAndDepartureDate(
						search.getDepartureAirport(), search.getDestinationAirport(), search.getDepartureDate());
		if (searchedList.isEmpty()) {
			throw new FlightNotFoundException("No Flight available");
		}
		return searchedList;
	}

	// Getting All Flights
	@Override
	public List<Flight> getAllFlights() {

		List<Flight> flightList = flightManagementRepository.findAll();
		if (flightList.isEmpty()) {
			throw new FlightNotFoundException("No Flight available");
		}
		return flightList;
	}

	// Getting the Flight
	@Override
	public Optional<Flight> getFlight(String id) {
		Optional<Flight> flight = flightManagementRepository.findById(id);
		if (!flight.isPresent()) {
			throw new IdNotFoundException("Id not found");
		}
		return flight;
	}

	// Adding the Flight
	@Override
	public String addFlight(Flight flight) {

		Fare fare = new Fare(flight.getId(), flight.getAirline().getAirlineName(), 3000);
		flight.setFare(fare);

		// Producer
		rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.POST_ROUTING_KEY, flight);

//		flightManagementRepository.save(flight);
		try {

			// Request for authentication and getting the authorization token
			String token = restTemplate.postForObject("http://fare-management/fare/authenticate", auth, String.class);

			// Creating Header with token
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", "Bearer " + token);
			// Creating Http Entity
			HttpEntity<Fare> entity = new HttpEntity<Fare>(fare, headers);

			// Adding the default Fare to Fare MS by REST call
			restTemplate.exchange("http://fare-management/fare" + "/addFare/", HttpMethod.POST, entity, String.class);

			return "Flight and Fare added with id : " + flight.getId();
		} catch (Exception e) {
			return "Fare not added because of [" + e + "], but Flight added with Flight Id : " + flight.getId();
		}

	}

	// Updating the Flight
	@Override
	public String updateFlight(Flight flight) {

		// Producer
		rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.PUT_ROUTING_KEY, flight);

//		flightManagementRepository.save(flight);

		return "Flight updated with id : " + flight.getId();
	}

	// Deleting the Flight
	@Override
	public String deleteFlight(String flightId) {
		if (flightManagementRepository.existsById(flightId)) {

			// Producer
			rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.DELETE_ROUTING_KEY, flightId);

//			flightManagementRepository.deleteById(flightId);

			try {
				// Request for authentication and getting the authorization token
				String tokenDeleteFare = restTemplate.postForObject("http://fare-management/fare/authenticate", auth,
						String.class);

				// Creating Header with token
				HttpHeaders headersDeleteFare = new HttpHeaders();
				headersDeleteFare.setContentType(MediaType.APPLICATION_JSON);
				headersDeleteFare.set("Authorization", "Bearer " + tokenDeleteFare);
				// Creating Http Entity
				HttpEntity<?> entityDeleteFare = new HttpEntity<>(headersDeleteFare);

				restTemplate.exchange("http://fare-management/fare" + "/deleteFare/" + flightId, HttpMethod.DELETE,
						entityDeleteFare, String.class);

				return "Flight and Fare deleted with Flight Id : " + flightId;
			} catch (Exception e) {
				return "Fare not deleted because of " + e + ", but Flight deleted with Flight Id : " + flightId;
			}
		} else {
			throw new IdNotFoundException("Id not exist");
		}
	}
}