package com.cg.casestudy.checkinmanagement.model;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "checkIns")
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
