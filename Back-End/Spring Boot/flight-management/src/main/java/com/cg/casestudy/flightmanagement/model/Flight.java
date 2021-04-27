package com.cg.casestudy.flightmanagement.model;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "flights")
public class Flight {

	@Id
	@NotEmpty(message = "flightId must not be empty")
	private String id;
	@Valid
	private Airline airline;
	@Valid
	private Airport departureAirport;
	@Valid
	private Airport destinationAirport;
	private String departureDate;
	private String arrivalDate;
	private String departureTime;
	private String arrivalTime;
	private Fare fare;
	@NotNull(message = "seats must not be empty")
	private Integer seats;
}
