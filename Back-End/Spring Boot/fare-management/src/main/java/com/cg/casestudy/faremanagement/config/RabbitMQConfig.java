package com.cg.casestudy.faremanagement.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	public static final String EXCHANGE = "fare-management-exchange";

	public static final String POST_QUEUE = "fare-management-post-queue";
	public static final String PUT_QUEUE = "fare-management-put-queue";
	public static final String DELETE_QUEUE = "fare-management-delete-queue";

	public static final String POST_ROUTING_KEY = "fare-management-post-routingKey";
	public static final String PUT_ROUTING_KEY = "fare-management-put-routingKey";
	public static final String DELETE_ROUTING_KEY = "fare-management-delete-routingKey";

	@Bean
	public TopicExchange exchange() {
		return new TopicExchange(EXCHANGE);
	}

	@Bean
	public Queue postQueue() {
		return new Queue(POST_QUEUE, false);
	}

	@Bean
	public Queue putQueue() {
		return new Queue(PUT_QUEUE, false);
	}

	@Bean
	public Queue deleteQueue() {
		return new Queue(DELETE_QUEUE, false);
	}

	@Bean
	public Binding postBinding(Queue postQueue, TopicExchange exchange) {
		return BindingBuilder.bind(postQueue).to(exchange).with(POST_ROUTING_KEY);
	}

	@Bean
	public Binding putBinding(Queue putQueue, TopicExchange exchange) {
		return BindingBuilder.bind(putQueue).to(exchange).with(PUT_ROUTING_KEY);
	}

	@Bean
	public Binding deleteBinding(Queue deleteQueue, TopicExchange exchange) {
		return BindingBuilder.bind(deleteQueue).to(exchange).with(DELETE_ROUTING_KEY);
	}

	@Bean
	public MessageConverter converter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public AmqpTemplate template(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(converter());
		return rabbitTemplate;
	}
}
