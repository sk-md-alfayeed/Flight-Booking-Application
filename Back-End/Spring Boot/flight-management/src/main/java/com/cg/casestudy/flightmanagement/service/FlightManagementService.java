package com.cg.casestudy.flightmanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cg.casestudy.flightmanagement.exception.FlightNotFoundException;
import com.cg.casestudy.flightmanagement.exception.IdNotFoundException;
import com.cg.casestudy.flightmanagement.model.Flight;
import com.cg.casestudy.flightmanagement.model.Search;

@Service
public interface FlightManagementService {

	List<Flight> getFlights(Search search) throws FlightNotFoundException;

	List<Flight> getAllFlights() throws FlightNotFoundException;

	Optional<Flight> getFlight(String id) throws FlightNotFoundException;

	String addFlight(Flight flight);

	String updateFlight(Flight flight);

	String deleteFlight(String flightId) throws IdNotFoundException;

}
