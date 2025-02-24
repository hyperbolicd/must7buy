package com.cathy.shopping.repository;

import com.cathy.shopping.model.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Integer> {
    List<OrderProduct> getOrderProductsByOrderId(int orderId);
}