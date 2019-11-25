package com.igrocery.orderapi.service;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.igrocery.orderapi.domain.Product;

@FeignClient(name="igrocery-product-catalog", serviceId="igrocery-product-catalog")
public interface ProductCatalogProxyService {

	@PostMapping(value = "/api/processOrderQuantity", produces = APPLICATION_JSON_VALUE )
	public List<Product> processOrderQuantity(@RequestHeader("authorization") String userToken, @RequestHeader("transaction-token") String transactionToken, @RequestHeader("service-token") String serviceToken, @RequestBody List<Product> products);
}
