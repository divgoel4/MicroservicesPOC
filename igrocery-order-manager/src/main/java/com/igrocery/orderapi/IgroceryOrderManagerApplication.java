package com.igrocery.orderapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import feign.Request;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients("com.igrocery.orderapi.service")
public class IgroceryOrderManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(IgroceryOrderManagerApplication.class, args);
	}

	@Bean
	public Request.Options timeoutConfiguration() {

		return new Request.Options(5000, 30000);
	}

}
