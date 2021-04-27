package com.cg.casestudy.checkinmanagement.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Passenger {
	
	@Id
	@NotEmpty(message = "passengerId must not be empty")
	private String id;
	@NotEmpty(message = "firstName must not be empty")
	private String firstName;
	private String middleName;
	@NotEmpty(message = "lastName must not be empty")
	private String lastName;
	@NotNull(message = "age must not be null")
	@Range(min=5, max=120, message="age must be 5 to 120")
	private Integer age;
	private String gender;
	private String seatNo;
}
