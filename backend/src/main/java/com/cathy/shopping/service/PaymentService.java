package com.cathy.shopping.service;

import com.cathy.shopping.dto.linepay.PaymentResponse;
import com.cathy.shopping.model.Customer;
import com.cathy.shopping.model.Order;
import com.cathy.shopping.model.Payment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PaymentService {

    PaymentResponse createPayment(Order order, Customer.Cart cart);

    PaymentResponse getPaymentStatus(String transactionId);

    PaymentResponse confirmPayment(String transactionId, Order order);

    boolean cancelPayment(String transactionId);

    PaymentResponse refundPayment(String transactionId);

    List<Payment> getPaymentHistory(long userId);
}
