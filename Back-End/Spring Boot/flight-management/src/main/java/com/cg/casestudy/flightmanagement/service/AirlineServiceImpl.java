package com.cg.casestudy.flightmanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.casestudy.flightmanagement.model.Airline;
import com.cg.casestudy.flightmanagement.repository.AirlineRepository;

@Service
public class AirlineServiceImpl implements AirlineService {

	@Autowired
	private AirlineRepository airlineRepo;

	// Getting All Airlines
	@Override
	public List<Airline> getAllAirlines() {
		return airlineRepo.findAll();
	}

	// Getting the Airline
	@Override
	public Optional<Airline> getAirline(String id) {
		return airlineRepo.findById(id);
	}

	// Adding the Airline
	@Override
	public String addAirline(Airline airline) {
		airlineRepo.save(airline);
		return "Airline Added  with id : " + airline.getId();
	}

	// Updating the Airline
	@Override
	public String updateAirline(Airline airline) {
		airlineRepo.save(airline);
		return "Airline Updated  with id : " + airline.getId();
	}
	
	// Deleting the Airline
	@Override
	public String deleteAirline(String id) {
		airlineRepo.deleteById(id);
		return "Airline Deleted with id : " + id;
	}

}
