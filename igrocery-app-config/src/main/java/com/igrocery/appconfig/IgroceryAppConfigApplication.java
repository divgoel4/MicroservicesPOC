package com.igrocery.appconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class IgroceryAppConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(IgroceryAppConfigApplication.class, args);
	}

}
