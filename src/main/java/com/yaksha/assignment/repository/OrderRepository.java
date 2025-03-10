package com.yaksha.assignment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yaksha.assignment.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	// Find orders by customer ID
	List<Order> findByCustomerId(Long customerId);

	// Find orders with total amount greater than the given value
	List<Order> findByTotalAmountGreaterThan(Double amount);
}
