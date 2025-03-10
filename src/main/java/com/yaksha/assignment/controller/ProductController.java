package com.yaksha.assignment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yaksha.assignment.entity.Product;
import com.yaksha.assignment.repository.ProductRepository;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	@Autowired
	private ProductRepository productRepository;

	// Create a product (POST)
	@PostMapping
	public Product createProduct(@RequestBody Product product) {
		return productRepository.save(product);
	}

	// Get products by price range (GET)
	@GetMapping("/price")
	public List<Product> getProductsByPriceRange(@RequestParam Double minPrice, @RequestParam Double maxPrice) {
		return productRepository.findByPriceBetween(minPrice, maxPrice);
	}

	// Get products by name containing a keyword (GET)
	@GetMapping("/name")
	public List<Product> getProductsByName(@RequestParam String keyword) {
		return productRepository.findByNameContaining(keyword);
	}
}
