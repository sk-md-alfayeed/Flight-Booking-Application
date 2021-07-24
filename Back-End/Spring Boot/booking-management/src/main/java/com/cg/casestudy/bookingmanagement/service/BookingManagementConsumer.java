package com.cg.casestudy.bookingmanagement.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.cg.casestudy.bookingmanagement.model.Booking;
import com.cg.casestudy.bookingmanagement.repository.BookingManagementRepository;

@Component
public class BookingManagementConsumer {

	@Autowired
	private BookingManagementRepository bookingManagementRepository;

//	@RabbitListener(queues = RabbitMQConfig.POST_QUEUE)
//	public void consumeAddBooking(Booking booking) {
//		bookingManagementRepository.save(booking);
//		System.out.println("Booking added to DB");
//	}

//	@RabbitListener(queues = RabbitMQConfig.PUT_QUEUE)
//	public void consumeUpdateBooking(Booking booking) {
//		bookingManagementRepository.save(booking);
//		System.out.println("Booking updated to DB");
//	}

//	@RabbitListener(queues = RabbitMQConfig.DELETE_QUEUE)
//	public void consumeDeleteBooking(String bookingId) {
//		bookingManagementRepository.deleteById(bookingId);
//		System.out.println("Booking deleted from DB");
//	}
	
	
	@KafkaListener(groupId = "booking-json", topics = "booking-topic-post", containerFactory = "bookingKafkaListenerContainerFactory")
	public void consumeAddBooking(Booking booking) {
		bookingManagementRepository.save(booking);
		System.out.println("Booking added to DB");
	}
	
	@KafkaListener(groupId = "booking-json", topics = "booking-topic-put", containerFactory = "bookingKafkaListenerContainerFactory")
	public void consumeUpdateBooking(Booking booking) {
		bookingManagementRepository.save(booking);
		System.out.println("Booking updated to DB");
	}
	
	@KafkaListener(groupId = "booking-string", topics = "booking-topic-delete", containerFactory = "kafkaListenerContainerFactory")
	public void consumeDeleteBooking(String bookingId) {
		bookingManagementRepository.deleteById(bookingId.substring(1, bookingId.length()-1));
		System.out.println("Booking deleted from DB");
	}


}
