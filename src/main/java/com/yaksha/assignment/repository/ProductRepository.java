package com.yaksha.assignment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yaksha.assignment.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	// Find products by price range
	List<Product> findByPriceBetween(Double minPrice, Double maxPrice);

	// Find products by name containing a specific keyword (case-insensitive)
	List<Product> findByNameContaining(String keyword);
}
