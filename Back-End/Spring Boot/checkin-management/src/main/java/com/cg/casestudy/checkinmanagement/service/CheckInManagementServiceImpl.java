package com.cg.casestudy.checkinmanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.casestudy.checkinmanagement.config.RabbitMQConfig;
import com.cg.casestudy.checkinmanagement.exception.CheckInNotFoundException;
import com.cg.casestudy.checkinmanagement.exception.IdNotFoundException;
import com.cg.casestudy.checkinmanagement.model.CheckIn;
import com.cg.casestudy.checkinmanagement.model.Search;
import com.cg.casestudy.checkinmanagement.repository.CheckInManagementRepository;

@Service
public class CheckInManagementServiceImpl implements CheckInManagementService {
	
	@Autowired
	RabbitTemplate rabbitTemplate;

	@Autowired
	CheckInManagementRepository checkInManagementRepository;

	// Get flights by custom search
	public Optional<CheckIn> getCheckIn(Search search) {


		return checkInManagementRepository.findByPnrNoAndEmail(search.getPnrNo(), search.getEmail());
	}

	// Getting 'All CheckIn List' from CheckInReopsitory
	@Override
	public List<CheckIn> getAllCheckIns() {
	
		List<CheckIn> checkInList = checkInManagementRepository.findAll();
		if (checkInList.isEmpty()) {
			throw new CheckInNotFoundException("No CheckIn available");
		}
		return checkInList;
	}

	// Getting 'CheckIn object/Optional' from CheckInReopsitory
	@Override
	public Optional<CheckIn> getCheckInById(String id) {

		Optional<CheckIn> checkIn = checkInManagementRepository.findById(id);
		if (!checkIn.isPresent()) {
			throw new IdNotFoundException("Id not found");
		}
		return checkIn;
	}

	// Adding 'CheckIn' to database using CheckInCheckInReopsitory
	@Override
	public void addCheckIn(CheckIn checkIn) {
		// Producer
		rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.POST_ROUTING_KEY, checkIn);
	
//		checkInManagementRepository.save(checkIn);
	}

	// Updating 'CheckIn' in database using CheckInReopsitory
	@Override
	public void updateCheckIn(CheckIn checkIn) {
		
		// Producer
		rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.PUT_ROUTING_KEY, checkIn);
	
//		checkInManagementRepository.save(checkIn);
	}

	// Deleting 'CheckIn' by Id in database using CheckInReopsitory
	@Override
	public String deleteCheckIn(String id) {

		if (checkInManagementRepository.existsById(id)) {
			
			// Producer
			rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.DELETE_ROUTING_KEY, id);
			
//			checkInManagementRepository.deleteById(id);
			return "CheckIn Deleted with Id : " + id;
		} else {
			throw new IdNotFoundException("Id not exist");
		}

	}

}
