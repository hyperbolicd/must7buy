package com.cathy.shopping.controller;

import com.cathy.shopping.config.PaymentFactory;
import com.cathy.shopping.dto.linepay.PaymentResponse;
import com.cathy.shopping.exception.ResourceNotFountException;
import com.cathy.shopping.model.Customer;
import com.cathy.shopping.model.Order;
import com.cathy.shopping.model.Payment;
import com.cathy.shopping.service.LinePayService;
import com.cathy.shopping.service.OrderService;
import com.cathy.shopping.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    @Autowired
    private PaymentFactory paymentFactory;

    @Autowired
    private OrderService orderService;

//    @GetMapping
//    public ResponseEntity<List<Payment>> getAllPayments() {
//        List<Payment> payments = paymentService.getAllPayments();
//        return ResponseEntity.ok(payments);
//    }

    @PostMapping("/{orderId}")
    public ResponseEntity<PaymentResponse> createPayment(@PathVariable int orderId, @RequestBody Customer.Cart cart, @RequestParam String type) {
        Order order = orderService.getOrderById(orderId);
        PaymentResponse response = paymentFactory.getPaymentService(type).createPayment(order, cart);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/confirm/{orderId}")
    public ResponseEntity<PaymentResponse> confirmPayment(@PathVariable int orderId, @RequestParam String transactionId) {
        Order order = orderService.getOrderById(orderId);
        PaymentResponse response = paymentFactory.getPaymentService(order.getPaymentType()).confirmPayment(transactionId, order);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Payment> getPaymentById(@PathVariable int id) {
//        Payment payment = paymentService.getPaymentById(id);
//        return ResponseEntity.ok(payment);
//    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Payment> updatePayment(@PathVariable int id, @RequestBody Payment payment) {
//        Payment updatedPayment = paymentService.updatePayment(id, payment);
//        return ResponseEntity.ok(updatedPayment);
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deletePayment(@PathVariable int id) {
//        boolean isDeleted = paymentService.deletePayment(id);
//        if (isDeleted) {
//            return ResponseEntity.noContent().build(); // 204 No Content
//        } else {
//            return ResponseEntity.notFound().build(); // 404 Not Found
//        }
//    }

}
