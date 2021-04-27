package com.cg.casestudy.bookingmanagement.model;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckIn {
	@Id
	@NotEmpty(message = "id must not be empty")
	private String id;
	@NotEmpty(message = "pnrNo must not be empty")
	private String pnrNo;
	@NotEmpty(message = "email must not be empty")
	private String email;
	private List<Passenger> passengerList;

}
