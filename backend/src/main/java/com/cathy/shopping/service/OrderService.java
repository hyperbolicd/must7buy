package com.cathy.shopping.service;

import com.cathy.shopping.exception.ResourceNotFountException;
import com.cathy.shopping.model.Order;
import com.cathy.shopping.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    // Add service methods here
    
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order getOrderById(Integer id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFountException("Order not exist with id: " + id));
    }
    
    public Order updateOrder(Integer id, Order updatedOrder) {
        Order existingOrder = getOrderById(id);
        return orderRepository.save(existingOrder);
    }

    public boolean deleteOrder(Integer id) {
        Order order = getOrderById(id);
        orderRepository.delete(order);
        return true;
    }
       
}
