package com.cg.casestudy.bookingmanagement.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fare {

	@Id
	@NotEmpty(message = "fareId must not be empty")
	private String id;
	@NotEmpty(message = "flightName must not be empty")
	private String flightName;
	@NotNull(message = "flightFare must not be empty")
	private Integer flightFare;


}
