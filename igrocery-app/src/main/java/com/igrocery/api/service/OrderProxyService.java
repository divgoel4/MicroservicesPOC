package com.igrocery.api.service;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.igrocery.api.domain.Order;
import com.igrocery.api.domain.Product;

@FeignClient(name="igrocery-order-manager")
public interface OrderProxyService {

	@PostMapping(value = "/api/orders", produces = APPLICATION_JSON_VALUE )
	public List<Product> allProducts(@RequestHeader("authorization") String userToken, @RequestHeader("transaction-token") String transactionToken, @RequestHeader("service-token") String serviceToken, @RequestBody Order order);
	@GetMapping(value = "/api/orders/{id}", produces = APPLICATION_JSON_VALUE )
	public Optional<Product> findOrderById(@RequestHeader("authorization") String userToken, @RequestHeader("transaction-token") String transactionToken, @RequestHeader("service-token") String serviceToken, @PathVariable Long orderId);
}
