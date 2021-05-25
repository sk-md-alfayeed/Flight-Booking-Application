package com.cg.casestudy.bookingmanagement.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cg.casestudy.bookingmanagement.config.RabbitMQConfig;
import com.cg.casestudy.bookingmanagement.model.Booking;
import com.cg.casestudy.bookingmanagement.repository.BookingManagementRepository;

@Component
public class BookingManagementConsumer {

	@Autowired
	RabbitTemplate rabbitTemplate;

	@Autowired
	private BookingManagementRepository bookingManagementRepository;

	@RabbitListener(queues = RabbitMQConfig.POST_QUEUE)
	public void consumeAddBooking(Booking booking) {
		bookingManagementRepository.save(booking);
		System.out.println("Booking added to DB");
	}

	@RabbitListener(queues = RabbitMQConfig.PUT_QUEUE)
	public void consumeUpdateBooking(Booking booking) {
		bookingManagementRepository.save(booking);
		System.out.println("Booking updated to DB");
	}

	@RabbitListener(queues = RabbitMQConfig.DELETE_QUEUE)
	public void consumeDeleteBooking(String bookingId) {
		bookingManagementRepository.deleteById(bookingId);
		System.out.println("Booking deleted from DB");
	}

}
