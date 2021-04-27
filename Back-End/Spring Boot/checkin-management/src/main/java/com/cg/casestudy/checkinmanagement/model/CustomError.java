package com.cg.casestudy.checkinmanagement.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomError {

	private LocalDateTime timestamp;
	private String message;
	private String details;


}