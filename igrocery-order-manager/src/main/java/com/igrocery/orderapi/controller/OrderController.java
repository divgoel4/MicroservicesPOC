package com.igrocery.orderapi.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.igrocery.orderapi.domain.Order;
import com.igrocery.orderapi.service.OrderService;

@RestController
@RequestMapping("/api")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping("/orders")
	public ResponseEntity<?> createOrder(@RequestHeader("authorization") String userToken,
			@RequestHeader("transaction-token") String transactionToken,
			@RequestHeader("service-token") String serviceToken, @RequestBody Order order) {
		order.setUserToken(userToken);
		order.setServiceToken(serviceToken);
		order.setTransactionToken(transactionToken);
		order = orderService.processOrder(order);
		if (order.isOrderProcessedSuccessfully()) {
			return new ResponseEntity<Order>(order, HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<Order>(order, HttpStatus.NOT_ACCEPTABLE);
	}

	@GetMapping("/orders/{id}")
	public ResponseEntity<?> findOrderById(@RequestHeader("authorization") String userToken,
			@RequestHeader("transaction-token") String transactionToken,
			@RequestHeader("service-token") String serviceToken, @PathVariable Long orderId) {
		Order order= orderService.findOrderById(orderId);
		if(order!=null) {
			return new ResponseEntity<Order>(order,HttpStatus.FOUND);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
