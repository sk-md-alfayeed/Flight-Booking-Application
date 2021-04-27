package com.cg.casestudy.checkinmanagement.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorList {

	private LocalDateTime timestamp;
	private String message;
	private List<String> details;

}