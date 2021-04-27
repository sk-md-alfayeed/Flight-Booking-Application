package com.cg.casestudy.faremanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.cg.casestudy.faremanagement.exception.FareNotFoundException;
import com.cg.casestudy.faremanagement.exception.IdNotFoundException;
import com.cg.casestudy.faremanagement.model.Fare;

@Service
public interface FareManagementService {

	List<Fare> getAllFares() throws FareNotFoundException;

	Optional<Fare> getFare(String id) throws FareNotFoundException;

	String addFare(Fare fare);

	String updateFare(Fare fare);

	String deleteFare(String id) throws IdNotFoundException;

}
