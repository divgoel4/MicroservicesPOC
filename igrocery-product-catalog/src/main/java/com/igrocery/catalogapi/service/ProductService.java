package com.igrocery.catalogapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.igrocery.catalogapi.controller.ProductController;
import com.igrocery.catalogapi.domain.Product;
import com.igrocery.catalogapi.repository.ProductRepository;

//import com.sivalabs.catalogservice.entities.Product;
//import com.sivalabs.catalogservice.repositories.ProductRepository;
//import com.sivalabs.catalogservice.services.InventoryServiceClient;
//import com.sivalabs.catalogservice.utils.MyThreadLocalsHolder;
//import com.sivalabs.catalogservice.web.models.ProductInventoryResponse;

//import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
// @Slf4j
public class ProductService {
	
	private Logger logger = LoggerFactory.getLogger(ProductService.class);
	
	private final ProductRepository productRepository;
	// private final InventoryServiceClient inventoryServiceClient;

	@Autowired
	public ProductService(ProductRepository productRepository/* , InventoryServiceClient inventoryServiceClient */) {
		logger.info(" ProductService object created ");
		this.productRepository = productRepository;
		// this.inventoryServiceClient = inventoryServiceClient;
	}

	public List<Product> findAllProducts() {
		logger.info(" In findAllProducts() ");
		List<Product> products = productRepository.findAll();
		// //final Map<String, Integer> inventoryLevels =
		// getInventoryLevelsWithFeignClient();
		// final List<Product> availableProducts = products.stream()
		// .filter(p -> inventoryLevels.get(p.getCode()) != null &&
		// inventoryLevels.get(p.getCode()) > 0)
		// .collect(Collectors.toList());
		return products;
	}

	@Transactional
	public List<Product> processProductQuantity(List<Product> products) throws Exception {
		logger.info(" In processProductQuantity() ");
		boolean isValid = true;
		List<Product> processedProducts = new ArrayList<>();
		for (Product product : products) {

			Optional<Product> productOptional = productRepository.findByCode(product.getCode());

			if (productOptional.isPresent()) {

				Product tempProduct = productOptional.get();
				if (tempProduct.getQuantity() <= 0) {

					product.setMessage("This product is currently Out of Stock!");
					isValid = false;
				} else if (tempProduct.getQuantity() < product.getQuantity()) {

					product.setMessage("For this Product available quantity is " + tempProduct.getQuantity()
							+ "  and your ordered quantity is " + product.getQuantity());
					isValid = false;
				}
			} else {

				product.setMessage("Product Not Found!");
				isValid = false;
			}
			processedProducts.add(product);
		}
		if (isValid) {

			for (Product product : products) {
				Optional<Product> productOptional = productRepository.findByCode(product.getCode());
				if (productOptional.isPresent()) {

					Product tempProduct = productOptional.get();
					tempProduct.setQuantity(tempProduct.getQuantity() - product.getQuantity());
					product.setMessage("Successfully Processed!");
					productRepository.save(tempProduct);
				} else {

					isValid = false;
				}
			}
			Product p =new Product();
			p.setName("PROCESSED_SUCCESSFULLY");
			processedProducts.add(p);

		}
		System.out.println("Done with Processing quqntity");
		
		return processedProducts;
	}

	// private Map<String, Integer> getInventoryLevelsWithFeignClient() {
	// log.info("Fetching inventory levels using FeignClient");
	// Map<String, Integer> inventoryLevels = new HashMap<>();
	// List<ProductInventoryResponse> inventory =
	// inventoryServiceClient.getProductInventoryLevels();
	// for (ProductInventoryResponse item: inventory){
	// inventoryLevels.put(item.getProductCode(), item.getAvailableQuantity());
	// }
	// log.debug("InventoryLevels: {}", inventoryLevels);
	// return inventoryLevels;
	// }

	// public Optional<Product> findProductByCode(String code) {
	// Optional<Product> productOptional = productRepository.findByCode(code);
	// if (productOptional.isPresent()) {
	// String correlationId = UUID.randomUUID().toString();
	// MyThreadLocalsHolder.setCorrelationId(correlationId);
	// log.info("Before CorrelationID: "+ MyThreadLocalsHolder.getCorrelationId());
	// log.info("Fetching inventory level for product_code: " + code);
	// Optional<ProductInventoryResponse> itemResponseEntity =
	// this.inventoryServiceClient.getProductInventoryByCode(code);
	// if (itemResponseEntity.isPresent()) {
	// Integer quantity = itemResponseEntity.get().getAvailableQuantity();
	// productOptional.get().setInStock(quantity > 0);
	// }
	// log.info("After CorrelationID: "+ MyThreadLocalsHolder.getCorrelationId());
	// }
	// return productOptional;
	// }
}
