package com.cg.casestudy.checkinmanagement.model;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Search {

	@NotEmpty(message = "pnrNo must not be empty")
	private String pnrNo;
	@NotEmpty(message = "email must not be empty")
	private String email;

}
