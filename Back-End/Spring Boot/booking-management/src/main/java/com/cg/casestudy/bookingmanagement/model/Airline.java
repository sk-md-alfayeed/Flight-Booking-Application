package com.cg.casestudy.bookingmanagement.model;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Airline {

	@Id
	@NotEmpty(message = "airlinId must not be empty")
	private String id;
	@NotEmpty(message = "airlineNo must not be empty")
	private String airlineNo;
	@NotEmpty(message = "airlineName must not be empty")
	private String airlineName;

}
