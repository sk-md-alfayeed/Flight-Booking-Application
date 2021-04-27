package com.cg.casestudy.apigateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class FallbackController {

	@RequestMapping("/flightFallBack")
	public Mono<String> flightManagementFallBack() {
		return Mono.just("Flight Management Service is taking too long to respond or is down. Please try again later");
	}

	@RequestMapping("/fareFallback")
	public Mono<String> fareManagementFallBack() {
		return Mono.just("Fare Management Service is taking too long to respond or is down. Please try again later");
	}

	@RequestMapping("/bookingFallback")
	public Mono<String> bookingManagementFallBack() {
		return Mono.just("Booking Management Service is taking too long to respond or is down. Please try again later");
	}
	
	@RequestMapping("/checkInFallback")
	public Mono<String> checkInManagementFallBack() {
		return Mono.just("CheckIn Management Service is taking too long to respond or is down. Please try again later");
	}

}
