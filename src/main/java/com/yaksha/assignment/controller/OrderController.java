package com.yaksha.assignment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yaksha.assignment.entity.Order;
import com.yaksha.assignment.repository.OrderRepository;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	private OrderRepository orderRepository;

	// Create an order (POST)
	@PostMapping
	public Order createOrder(@RequestBody Order order) {
		return orderRepository.save(order);
	}

	// Get orders by customer ID (GET)
	@GetMapping("/customer/{customerId}")
	public List<Order> getOrdersByCustomerId(@PathVariable Long customerId) {
		return orderRepository.findByCustomerId(customerId);
	}

	// Get orders with total amount greater than the given value (GET)
	@GetMapping("/amount")
	public List<Order> getOrdersByAmount(@RequestParam Double amount) {
		return orderRepository.findByTotalAmountGreaterThan(amount);
	}
}
