package com.cathy.shopping.repository;

import com.cathy.shopping.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}