package com.igrocery.serviceregistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class IgroceryServiceRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(IgroceryServiceRegistryApplication.class, args);
	}

}
