package com.igrocery.orderapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.igrocery.orderapi.domain.Order;
import com.igrocery.orderapi.domain.Product;
import com.igrocery.orderapi.repository.OrderRepository;

@Service
@Transactional
public class OrderService {

	private OrderRepository orderRepository;

	@Autowired
	private ProductCatalogProxyService productCatalogProxyService;

	@Autowired
	public OrderService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	public Order processOrder(Order order) {
		order.setOrderProcessedSuccessfully(false);
		System.out.println(order.getProducts().size());
		List<Product> processedProducts = productCatalogProxyService.processOrderQuantity(order.getUserToken(),
				order.getTransactionToken(), order.getServiceToken(), order.getProducts());
		if (processedProducts != null && order.getProducts() != null
				&& processedProducts.size() > order.getProducts().size()) {
			processedProducts.remove(processedProducts.size() - 1);
			order = orderRepository.save(order);
			order.setOrderProcessedSuccessfully(true);
		}
		order.setProducts(processedProducts);
		return order;
	}
	
	public Order findOrderById(Long orderId) {
		Optional<Order> order = orderRepository.findById(orderId);
		if(order.isPresent()) {
			return order.get();
		}
		return null;
	}

}
