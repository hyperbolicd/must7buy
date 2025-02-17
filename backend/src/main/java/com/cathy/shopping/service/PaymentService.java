package com.cathy.shopping.service;

import com.cathy.shopping.exception.ResourceNotFountException;
import com.cathy.shopping.model.Payment;
import com.cathy.shopping.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    // Add service methods here
    
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public Payment getPaymentById(Integer id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFountException("Payment not exist with id: " + id));
    }

    public Payment getPaymentByTransactionId(String transactionId) {
        return paymentRepository.findPaymentByTransactionId(transactionId)
                .orElseThrow(() -> new ResourceNotFountException("Payment not exist with transaction id: " + transactionId));
    }
    
    public Payment updatePayment(Integer id, Payment updatedPayment) {
        Payment existingPayment = getPaymentById(id);
        return paymentRepository.save(existingPayment);
    }

    public boolean deletePayment(Integer id) {
        Payment payment = getPaymentById(id);
        paymentRepository.delete(payment);
        return true;
    }
       
}
