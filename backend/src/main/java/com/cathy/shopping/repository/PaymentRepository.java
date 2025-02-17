package com.cathy.shopping.repository;

import com.cathy.shopping.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    Optional<Payment> findPaymentByTransactionId(String transactionId);
}