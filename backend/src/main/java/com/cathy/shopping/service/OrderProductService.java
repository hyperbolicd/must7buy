package com.cathy.shopping.service;

import com.cathy.shopping.exception.ResourceNotFountException;
import com.cathy.shopping.model.OrderProduct;
import com.cathy.shopping.repository.OrderProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderProductService {

    @Autowired
    private OrderProductRepository orderProductRepository;

    // Add service methods here
    
    public List<OrderProduct> getAllOrderProducts() {
        return orderProductRepository.findAll();
    }

    public OrderProduct createOrderProduct(OrderProduct orderProduct) {
        return orderProductRepository.save(orderProduct);
    }

    public OrderProduct getOrderProductById(Integer id) {
        return orderProductRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFountException("OrderProduct not exist with id: " + id));
    }
    
    public OrderProduct updateOrderProduct(Integer id, OrderProduct updatedOrderProduct) {
        OrderProduct existingOrderProduct = getOrderProductById(id);
        return orderProductRepository.save(existingOrderProduct);
    }

    public boolean deleteOrderProduct(Integer id) {
        OrderProduct orderProduct = getOrderProductById(id);
        orderProductRepository.delete(orderProduct);
        return true;
    }
}
