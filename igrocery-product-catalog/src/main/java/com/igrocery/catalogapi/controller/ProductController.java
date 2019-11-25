package com.igrocery.catalogapi.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.igrocery.catalogapi.domain.Product;
import com.igrocery.catalogapi.service.ProductService;

//import com.sivalabs.catalogservice.entities.Product;
//import com.sivalabs.catalogservice.exceptions.ProductNotFoundException;
//import com.sivalabs.catalogservice.services.ProductService;
//
//import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
//@Slf4j
public class ProductController {

	private Logger logger = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/products", produces = APPLICATION_JSON_VALUE)
    public List<Product> allProducts(@RequestHeader("authorization") String userToken, @RequestHeader("transaction-token") String transactionToken, @RequestHeader("service-token") String serviceToken) {
    	logger.info("{}","Service-Token:"+serviceToken+" : In allProducts() ");
    	//Identify validate the request header.
    	return productService.findAllProducts();
    }

    @PostMapping(value = "/processOrderQuantity", produces = APPLICATION_JSON_VALUE )
	public List<Product> processOrderQuantity(@RequestHeader("authorization") String userToken, @RequestHeader("transaction-token") String transactionToken, @RequestHeader("service-token") String serviceToken, @RequestBody List<Product> products) throws Exception{
    	logger.info("{}","Service-Token:"+serviceToken+" : In processOrderQuantity() ");
    	return productService.processProductQuantity(products);
    	
    }
//    @GetMapping("/{code}")
//    public Product productByCode(@PathVariable String code) {
//        log.info("Finding product by code :"+code);
//        return productService.findProductByCode(code)
//                .orElseThrow(() -> new ProductNotFoundException("Product with code ["+code+"] doesn't exist"));
//    }
}
