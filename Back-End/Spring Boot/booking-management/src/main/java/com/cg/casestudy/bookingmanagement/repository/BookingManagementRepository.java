package com.cg.casestudy.bookingmanagement.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cg.casestudy.bookingmanagement.model.Booking;

@Repository
public interface BookingManagementRepository extends MongoRepository<Booking, String>{

	List<Booking> findByEmail(String email);

}
