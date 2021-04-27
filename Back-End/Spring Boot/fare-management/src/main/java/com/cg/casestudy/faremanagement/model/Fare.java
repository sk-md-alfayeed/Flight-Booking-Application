package com.cg.casestudy.faremanagement.model;

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
@Document(collection = "fares")
public class Fare {

	@Id
	@NotEmpty(message = "fairId must not be empty")
	String id;
	@NotEmpty(message = "flightName must not be empty")
	String flightName;
	@NotNull(message = "flightFare must not be empty")
	Integer flightFare;

}
