package com.cg.casestudy.checkinmanagement.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cg.casestudy.checkinmanagement.config.RabbitMQConfig;
import com.cg.casestudy.checkinmanagement.model.CheckIn;
import com.cg.casestudy.checkinmanagement.repository.CheckInManagementRepository;



@Component
public class CheckInManagementConsumer {

	@Autowired
	RabbitTemplate rabbitTemplate;

	@Autowired
	private CheckInManagementRepository checkInManagementRepository;

	@RabbitListener(queues = RabbitMQConfig.POST_QUEUE)
	public void consumeAddCheckIn(CheckIn checkIn) {
		checkInManagementRepository.save(checkIn);
		System.out.println("CheckIn added to DB");
	}

	@RabbitListener(queues = RabbitMQConfig.PUT_QUEUE)
	public void consumeUpdateCheckIn(CheckIn checkIn) {
		checkInManagementRepository.save(checkIn);
		System.out.println("CheckIn updated to DB");
	}

	@RabbitListener(queues = RabbitMQConfig.DELETE_QUEUE)
	public void consumeDeleteCheckIn(String id) {
		checkInManagementRepository.deleteById(id);
		System.out.println("CheckIn deleted from DB");
	}

}
