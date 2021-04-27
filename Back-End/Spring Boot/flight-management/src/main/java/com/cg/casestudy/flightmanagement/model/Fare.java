package com.cg.casestudy.flightmanagement.model;

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
	String id;
	@NotEmpty(message = "flightName must not be empty")
	String flightName;
	@NotNull(message = "flightFare must not be empty")
	Integer flightFare;

}
