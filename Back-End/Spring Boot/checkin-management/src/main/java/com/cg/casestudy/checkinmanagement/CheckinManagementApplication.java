package com.cg.casestudy.checkinmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CheckinManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(CheckinManagementApplication.class, args);
	}

}
