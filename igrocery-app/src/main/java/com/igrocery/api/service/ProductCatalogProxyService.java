package com.igrocery.api.service;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.igrocery.api.domain.Product;

@FeignClient(name="igrocery-product-catalog", serviceId="igrocery-product-catalog")
public interface ProductCatalogProxyService {

	@GetMapping(value = "/api/products", produces = APPLICATION_JSON_VALUE )
	public List<Product> allProducts(@RequestHeader("authorization") String userToken, @RequestHeader("transaction-token") String transactionToken, @RequestHeader("service-token") String serviceToken);
}
