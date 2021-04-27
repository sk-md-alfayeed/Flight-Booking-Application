package com.cg.casestudy.bookingmanagement.model;

import java.util.List;

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
@Document(collection = "bookings")
public class Booking {

	@Id
	@NotEmpty(message = "id must not be empty")
	private String id;
	@NotEmpty(message = "pnrNo must not be empty")
	private String pnrNo;
	@Valid
	private Flight flight;
	@Valid
	private List<Passenger> passengerList;
	@NotEmpty(message = "date must not be empty")
	private String date;
	@NotNull
	private boolean active;
	@NotNull(message = "userId must not be empty")
	private String email;
	

}
