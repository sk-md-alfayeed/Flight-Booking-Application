package com.cg.casestudy.flightmanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cg.casestudy.flightmanagement.model.Airport;

@Service
public interface AirportService {

	List<Airport> getAllAirports();

	Optional<Airport> getAirport(String id);

	String addAirport(Airport airport);

	String updateAirport(Airport airport);

	String deleteAirport(String id);

}
