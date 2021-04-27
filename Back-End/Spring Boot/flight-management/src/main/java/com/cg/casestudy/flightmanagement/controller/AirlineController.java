package com.cg.casestudy.flightmanagement.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.casestudy.flightmanagement.model.Airline;
import com.cg.casestudy.flightmanagement.service.AirlineService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/flight/airline")
public class AirlineController {

	@Autowired
	private AirlineService airlineService;

	// Getting REST GET request and returning 'all flights list' from
	// AirlineService
	@GetMapping(value = "/allAirlines")
	public List<Airline> getAllAirlines() {
		return airlineService.getAllAirlines();
	}

	// Getting REST GET request and returning 'one flight' from AirlineService
	@GetMapping("/getAirline/{id}")
	public Optional<Airline> getAirline(@PathVariable String id) {
		return airlineService.getAirline(id);
	}

	// Getting REST POST request and returning response from AirlineService
	@PostMapping("/addAirline")
	public String addAirline(@RequestBody Airline flight) {
		return airlineService.addAirline(flight);
	}

	// Getting REST PUT request and returning response from AirlineService
	@PutMapping("/updateAirline")
	public String updateAirline(@RequestBody Airline flight) {
		return airlineService.updateAirline(flight);
	}

	// Getting REST DELETE request and returning response from AirlineService
	@DeleteMapping("/deleteAirline/{id}")
	public String deleteAirline(@PathVariable String id) {
		return airlineService.deleteAirline(id);
	}

}
