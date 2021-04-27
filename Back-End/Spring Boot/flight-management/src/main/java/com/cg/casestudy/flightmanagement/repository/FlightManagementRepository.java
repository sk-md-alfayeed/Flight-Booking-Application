package com.cg.casestudy.flightmanagement.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cg.casestudy.flightmanagement.model.Flight;

@Repository
public interface FlightManagementRepository extends MongoRepository<Flight, String> {
	
	
	//Custom Search for fetching data from database
	List<Flight> findByDepartureAirportAirportCodeAndDestinationAirportAirportCodeAndDepartureDate(String departureAirportAirportCode, String destinationAirportAirportCode, String departureDate);
}
