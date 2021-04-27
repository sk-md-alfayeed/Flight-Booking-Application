package com.cg.casestudy.flightmanagement.model;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Search {

	@NotEmpty(message = "departureAirport must not be empty")
	private String departureAirport;
	@NotEmpty(message = "destinationAirport must not be empty")
	private String destinationAirport;
	@NotEmpty(message = "departureDate must not be empty")
	private String departureDate;
}
