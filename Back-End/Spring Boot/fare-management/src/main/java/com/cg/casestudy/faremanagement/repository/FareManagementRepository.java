package com.cg.casestudy.faremanagement.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cg.casestudy.faremanagement.model.Fare;

@Repository
public interface FareManagementRepository extends MongoRepository<Fare, String>{
	
	List<Fare> findByIdIn(List<String> id);

}
