package com.cg.casestudy.flightmanagement.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cg.casestudy.flightmanagement.config.RabbitMQConfig;
import com.cg.casestudy.flightmanagement.model.Flight;
import com.cg.casestudy.flightmanagement.repository.FlightManagementRepository;

@Component
public class FlightManagementConsumer {

	@Autowired
	RabbitTemplate rabbitTemplate;

	@Autowired
	private FlightManagementRepository flightManagementRepository;

	@RabbitListener(queues = RabbitMQConfig.POST_QUEUE)
	public void consumeAddFlight(Flight flight) {
		flightManagementRepository.save(flight);
		System.out.println("Flight added to DB");
	}

	@RabbitListener(queues = RabbitMQConfig.PUT_QUEUE)
	public void consumeUpdateFlight(Flight flight) {
		flightManagementRepository.save(flight);
		System.out.println("Flight updated to DB");
	}

	@RabbitListener(queues = RabbitMQConfig.DELETE_QUEUE)
	public void consumeDeleteFlight(String flightId){
		flightManagementRepository.deleteById(flightId);
		System.out.println("Flight deleted from DB");
	}

}
