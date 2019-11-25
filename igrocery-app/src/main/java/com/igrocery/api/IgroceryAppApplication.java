package com.igrocery.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import com.igrocery.api.security.AuthHeaderFilter;
import com.igrocery.api.security.JwtTokenProvider;

import feign.Request;


@SpringBootApplication
@EnableOAuth2Sso
@EnableZuulProxy
@EnableEurekaClient
@EnableFeignClients("com.igrocery.api.service")
public class IgroceryAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(IgroceryAppApplication.class, args);
	}
	
	@Bean
	AuthHeaderFilter authHeaderFilter() {
	    return new AuthHeaderFilter(new JwtTokenProvider());
	}
	
	@Bean 
	public Request.Options timeoutConfiguration(){
		
		return new Request.Options(5000, 30000);
	}

}
