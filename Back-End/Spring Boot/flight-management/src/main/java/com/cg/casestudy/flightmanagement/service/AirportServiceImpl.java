package com.cg.casestudy.flightmanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.casestudy.flightmanagement.model.Airport;
import com.cg.casestudy.flightmanagement.repository.AirportRepository;

@Service
public class AirportServiceImpl implements AirportService {

	@Autowired
	private AirportRepository airportRepo;

	// Getting All Airports
	@Override
	public List<Airport> getAllAirports() {
		return airportRepo.findAll();
	}

	// Getting the Airport
	@Override
	public Optional<Airport> getAirport(String id) {
		return airportRepo.findById(id);
	}

	// Adding the Airport
	@Override
	public String addAirport(Airport airport) {
		airportRepo.save(airport);
		return "Airport Added  with id : " + airport.getId();
	}

	// Updating the Airport
	@Override
	public String updateAirport(Airport airport) {
		airportRepo.save(airport);
		return "Airport Updated  with id : " + airport.getId();
	}

	// Deleting the Airport
	@Override
	public String deleteAirport(String id) {
		airportRepo.deleteById(id);
		return "Airport Deleted  with id : " + id;
	}

}
