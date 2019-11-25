package com.igrocery.catalogapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.igrocery.catalogapi.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	Optional<Product> findByCode(String code);
}
