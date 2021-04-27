package com.cg.casestudy.flightmanagement.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cg.casestudy.flightmanagement.model.Airline;

@Repository
public interface AirlineRepository extends MongoRepository<Airline, String>{

}
