package com.igrocery.orderapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.igrocery.orderapi.domain.Order;


public interface OrderRepository extends JpaRepository<Order, Long> {
}
