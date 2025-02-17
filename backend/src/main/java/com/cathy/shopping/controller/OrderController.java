package com.cathy.shopping.controller;

import com.cathy.shopping.dto.linepay.PaymentResponse;
import com.cathy.shopping.model.Customer;
import com.cathy.shopping.model.Order;
import com.cathy.shopping.model.OrderProduct;
import com.cathy.shopping.service.CustomerService;
import com.cathy.shopping.service.LinePayService;
import com.cathy.shopping.service.OrderProductService;
import com.cathy.shopping.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private LinePayService linePayService;

    @Autowired
    private OrderProductService orderProductService;

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("my")
    public ResponseEntity<List<Order>> getMyOrders() {
        List<Order> orders = orderService.getMyOrders();
        return ResponseEntity.ok(orders);
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Customer.Cart cart) {
        Order order = orderService.createOrder(cart);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @GetMapping("/linepayInfo")
    public ResponseEntity<String> getLinePayInfo() {
        linePayService.getInfo();
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable int id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{orderId}/products")
    public ResponseEntity<List<OrderProduct>> getProductsByOrder(@PathVariable int orderId) {
        return ResponseEntity.ok(orderProductService.getOrderProductsByOrderId(orderId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable int id, @RequestBody Order order) {
        Order updatedOrder = orderService.updateOrder(id, order);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable int id) {
        boolean isDeleted = orderService.deleteOrder(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

}
