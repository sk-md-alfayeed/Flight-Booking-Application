package com.cg.casestudy.bookingmanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cg.casestudy.bookingmanagement.config.RabbitMQConfig;
import com.cg.casestudy.bookingmanagement.exception.BookingNotFoundException;
import com.cg.casestudy.bookingmanagement.exception.IdNotFoundException;
import com.cg.casestudy.bookingmanagement.model.AuthRequest;
import com.cg.casestudy.bookingmanagement.model.Booking;
import com.cg.casestudy.bookingmanagement.model.CheckIn;
import com.cg.casestudy.bookingmanagement.model.Flight;
import com.cg.casestudy.bookingmanagement.repository.BookingManagementRepository;

@Service
public class BookingManagementServiceImpl implements BookingManagementService {

	// Creating AuthRequest object
	AuthRequest auth = new AuthRequest("admin", "1234");

	@Autowired
	RabbitTemplate rabbitTemplate;

	@Autowired
	private BookingManagementRepository bookingManagementRepository;

	@Autowired
	@Lazy
	private RestTemplate restTemplate;

//	// (space between '$' and '{' is important)
//	@Value("$ {microservice.flight-management.endpoints.endpoint.uri}")
//	private String FLIGHT_ENDPOINT_URL;
//
//	// (space between '$' and '{' is important)
//	@Value("$ {microservice.fare-management.endpoints.endpoint.uri}")
//	private String FARE_ENDPOINT_URL;

	// Getting 'Flight & Fare object combined ' from 'flight-search' & 'flight-fare'
	// Microservices
	@Override
	public Optional<Flight> getFlight(String flightId) {

		// Getting the Flight from Flight Search Microservice using RestTemplate
		try {
			Flight flight = restTemplate.getForObject("http://flight-management/flight" + "/getFlight/" + flightId,
					Flight.class);
			return Optional.of(flight);
		} catch (Exception e) {
			return Optional.empty();

		}

	}

	// Getting 'Booking List related to the userID from BookingManagementReopsitory
	@Override
	public List<Booking> getBookingsByEmail(String email) {
		List<Booking> bookingList = bookingManagementRepository.findByEmail(email);
		if (bookingList.isEmpty()) {
			throw new BookingNotFoundException("No Booking available with email : " + email);
		}
		return bookingList;

	}

	// Getting 'All Booking List' from BookingManagementReopsitory
	@Override
	public List<Booking> getAllBookings() {
		List<Booking> allBookingList = bookingManagementRepository.findAll();
		if (allBookingList.isEmpty()) {
			throw new BookingNotFoundException("No Booking available");
		}
		return allBookingList;
	}

	// Getting 'Booking object/Optional' from BookingManagementReopsitory
	@Override
	public Optional<Booking> getBooking(String bookingId) {
		Optional<Booking> booking = bookingManagementRepository.findById(bookingId);
		if (!booking.isPresent()) {
			throw new BookingNotFoundException("No Booking available with bookingId : " + bookingId);
		}
		return booking;
	}

	// Adding 'Booking' to database using BookingManagementReopsitory
	@Override
	public String addBooking(Booking booking) {

		CheckIn checkIn = new CheckIn(booking.getId(), booking.getPnrNo(), booking.getEmail(),
				booking.getPassengerList());

		Flight flight = booking.getFlight();
		flight.setSeats(flight.getSeats() - booking.getPassengerList().size());
		System.out.println(flight);

		booking.setFlight(flight);

		// Producer
		rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.POST_ROUTING_KEY, booking);

//		bookingManagementRepository.save(booking);

		try {
			// Request for authentication and getting the authorization token
			String tokenFlight = restTemplate.postForObject("http://flight-management/flight/authenticate", auth,
					String.class);

			// Creating Header with token
			HttpHeaders headersUpdateFlight = new HttpHeaders();
			headersUpdateFlight.setContentType(MediaType.APPLICATION_JSON);
			headersUpdateFlight.set("Authorization", "Bearer " + tokenFlight);
			// Creating Http Entity
			HttpEntity<Flight> entityUpdateFlight = new HttpEntity<Flight>(flight, headersUpdateFlight);

			// Updating Flight Seats

			restTemplate.exchange("http://flight-management/flight" + "/updateFlight", HttpMethod.PUT,
					entityUpdateFlight, String.class);
		} catch (Exception e) {
			System.out.println("Unable to add flight because of [" + e + "]");
		}

		try {

			// Request for authentication and getting the authorization token
			String token = restTemplate.postForObject("http://checkin-management/checkin/authenticate", auth,
					String.class);

			// Creating Header with token
			HttpHeaders headersAddCheckIn = new HttpHeaders();
			headersAddCheckIn.setContentType(MediaType.APPLICATION_JSON);
			headersAddCheckIn.set("Authorization", "Bearer " + token);
			// Creating Http Entity
			HttpEntity<CheckIn> entityAddCheckIn = new HttpEntity<CheckIn>(checkIn, headersAddCheckIn);

			// Adding the default Fare to Fare MS by REST call
			restTemplate.postForObject("http://checkin-management/checkin" + "/addCheckIn", entityAddCheckIn,
					String.class);

//			restTemplate.postForObject("http://checkin-management/checkin" + "/addCheckIn", checkIn, CheckIn.class);
			return "Booking added with id : " + booking.getId();
		} catch (Exception e) {
			return "CheckIn not added because of [" + e + "], but Flight booked with Booking Id : " + booking.getId();
		}

	}

	// Updating 'Booking' to database using BookingManagementReopsitory
	@Override
	public String updateBooking(Booking booking) {
		
		// Producer
				rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.PUT_ROUTING_KEY, booking);
				
//		bookingManagementRepository.save(booking);

		CheckIn checkIn = new CheckIn(booking.getId(), booking.getPnrNo(), booking.getEmail(),
				booking.getPassengerList());
		try {
			// Request for authentication and getting the authorization token
			String tokenUpdateCheckIn = restTemplate.postForObject("http://checkin-management/checkin/authenticate",
					auth, String.class);

			// Creating Header with token
			HttpHeaders headersUpdateCheckIn = new HttpHeaders();
			headersUpdateCheckIn.setContentType(MediaType.APPLICATION_JSON);
			headersUpdateCheckIn.set("Authorization", "Bearer " + tokenUpdateCheckIn);
			// Creating Http Entity
			HttpEntity<CheckIn> entityUpdateCheckIn = new HttpEntity<CheckIn>(checkIn, headersUpdateCheckIn);

			restTemplate.exchange("http://checkin-management/checkin" + "/updateCheckIn", HttpMethod.PUT,
					entityUpdateCheckIn, String.class);

			// Updating the default Fare to Fare MS by REST call
//			restTemplate.postForObject("http://checkin-management/checkin" + "/updateCheckIn", entityUpdateCheckIn,
//					String.class);

			return "Booking updated with id : " + booking.getId();
		} catch (Exception e) {
			return "CheckIn not added because of [" + e + "], but Flight booked with Booking Id : " + booking.getId();
		}

	}

	// Deleting 'Booking' by Booking Flight Id in database using FareReopsitory
	@Override
	public String deleteBooking(String bookingId) {
		if (bookingManagementRepository.existsById(bookingId)) {
			
			// Producer
			rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.DELETE_ROUTING_KEY, bookingId);
			
//			bookingManagementRepository.deleteById(bookingId);
			try {

				// Request for authentication and getting the authorization token
				String tokenDeleteCheckIn = restTemplate.postForObject("http://checkin-management/checkin/authenticate",
						auth, String.class);

				// Creating Header with token
				HttpHeaders headersDeleteCheckIn = new HttpHeaders();
				headersDeleteCheckIn.setContentType(MediaType.APPLICATION_JSON);
				headersDeleteCheckIn.set("Authorization", "Bearer " + tokenDeleteCheckIn);
				// Creating Http Entity
				HttpEntity<?> entityDeleteCheckIn = new HttpEntity<>(headersDeleteCheckIn);

				restTemplate.exchange("http://checkin-management/checkin" + "/deleteCheckIn/" + bookingId,
						HttpMethod.DELETE, entityDeleteCheckIn, String.class);

				// Deleting the CheckIn from Booking Microservice while this deleteFlight
				// method is called
//				restTemplate.delete("http://checkin-management/checkin" + "/deleteCheckIn/" + bookingId);
				return "Booking and CheckIn deleted with Flight Id : " + bookingId;
			} catch (Exception e) {
				return "CheckIn not delete because of " + e + ", but Booking deleted with booking Id : " + bookingId;
			}
		} else {
			throw new IdNotFoundException("Id not exist");
		}

	}
}
