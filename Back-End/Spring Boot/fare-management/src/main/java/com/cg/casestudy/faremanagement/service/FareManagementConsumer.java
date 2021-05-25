package com.cg.casestudy.faremanagement.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cg.casestudy.faremanagement.config.RabbitMQConfig;
import com.cg.casestudy.faremanagement.model.Fare;
import com.cg.casestudy.faremanagement.repository.FareManagementRepository;

@Component
public class FareManagementConsumer {

	@Autowired
	private FareManagementRepository fareManagementRepository;

	@RabbitListener(queues = RabbitMQConfig.POST_QUEUE)
	public void consumeAddFlight(Fare fare) {
		fareManagementRepository.save(fare);
		System.out.println("Fare added to DB");
	}

	@RabbitListener(queues = RabbitMQConfig.PUT_QUEUE)
	public void consumeUpdateFlight(Fare fare) {
		fareManagementRepository.save(fare);
		System.out.println("Fare updated to DB");
	}

	@RabbitListener(queues = RabbitMQConfig.DELETE_QUEUE)
	public void consumeDeleteFlight(String id) {
		fareManagementRepository.deleteById(id);
		System.out.println("Fare deleted from DB");
	}
}
