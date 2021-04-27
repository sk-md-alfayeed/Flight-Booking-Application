package com.cg.casestudy.checkinmanagement.model;

import java.util.List;

import javax.validation.Valid;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassengerList {
	
	@Valid
	private List<Passenger> passengerList;
	

}
