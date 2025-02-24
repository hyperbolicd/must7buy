package com.cathy.shopping.controller;

import com.cathy.shopping.model.OrderProduct;
import com.cathy.shopping.service.OrderProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orderProducts")
public class OrderProductController {
    
    @Autowired
    OrderProductService orderProductService;
    
    @GetMapping
    public ResponseEntity<List<OrderProduct>> getAllOrderProducts() {
        List<OrderProduct> orderProducts = orderProductService.getAllOrderProducts();
        return ResponseEntity.ok(orderProducts);
    }
    
    @PostMapping
    public ResponseEntity<OrderProduct> createOrderProduct(@RequestBody OrderProduct orderProduct) {
        OrderProduct createdOrderProduct = orderProductService.createOrderProduct(orderProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrderProduct);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<OrderProduct> getOrderProductById(@PathVariable int id) {
        OrderProduct orderProduct = orderProductService.getOrderProductById(id);
        return ResponseEntity.ok(orderProduct);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<OrderProduct> updateOrderProduct(@PathVariable int id, @RequestBody OrderProduct orderProduct) {
        OrderProduct updatedOrderProduct = orderProductService.updateOrderProduct(id, orderProduct);
        return ResponseEntity.ok(updatedOrderProduct);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderProduct(@PathVariable int id) {
        boolean isDeleted = orderProductService.deleteOrderProduct(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

}
