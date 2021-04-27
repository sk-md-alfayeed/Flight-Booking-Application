package com.cg.casestudy.faremanagement.model;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "airports")
public class Airport {

	@Id
	@NotEmpty(message = "airportId must not be empty")
	private String id;
	@NotEmpty(message = "airportCode must not be empty")
	private String airportCode;
	@NotEmpty(message = "airportName must not be empty")
	private String airportName;

}
