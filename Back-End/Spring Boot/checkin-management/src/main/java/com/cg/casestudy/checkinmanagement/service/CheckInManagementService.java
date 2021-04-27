package com.cg.casestudy.checkinmanagement.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.cg.casestudy.checkinmanagement.exception.CheckInNotFoundException;
import com.cg.casestudy.checkinmanagement.exception.IdNotFoundException;
import com.cg.casestudy.checkinmanagement.model.CheckIn;
import com.cg.casestudy.checkinmanagement.model.Search;

@Service
public interface CheckInManagementService {
	
	Optional<CheckIn> getCheckIn(@Valid Search search) throws CheckInNotFoundException;
	
	List<CheckIn> getAllCheckIns() throws CheckInNotFoundException;

	Optional<CheckIn> getCheckInById(String id) throws CheckInNotFoundException;

	void addCheckIn(CheckIn checkIn);

	void updateCheckIn(CheckIn checkIn);

	String deleteCheckIn(String id) throws IdNotFoundException;

	


}
