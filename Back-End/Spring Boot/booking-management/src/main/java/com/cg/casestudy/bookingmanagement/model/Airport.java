package com.cg.casestudy.bookingmanagement.model;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Airport {
	
	@Id
	@NotEmpty(message = "airportId must not be empty")
	private String id;
	@NotEmpty(message = "airportCode must not be empty")
	private String airportCode;
	@NotEmpty(message = "airportName must not be empty")
	private String airportName;
	


}
