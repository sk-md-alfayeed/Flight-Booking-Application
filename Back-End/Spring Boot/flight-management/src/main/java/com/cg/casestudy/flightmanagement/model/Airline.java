package com.cg.casestudy.flightmanagement.model;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "airlines")
public class Airline {

	@Id
	@NotEmpty(message = "airlineId must not be empty")
	private String id;
	@NotEmpty(message = "airlineNo must not be empty")
	private String airlineNo;
	@NotEmpty(message = "airlineName must not be empty")
	private String airlineName;
}
