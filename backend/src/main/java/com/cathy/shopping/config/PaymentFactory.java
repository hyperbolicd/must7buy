package com.cathy.shopping.config;

import com.cathy.shopping.exception.ResourceNotFountException;
import com.cathy.shopping.service.LinePayService;
import com.cathy.shopping.service.PaymentService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PaymentFactory {

    @Autowired
    private Map<String, PaymentService> paymentServices;

    public PaymentService getPaymentService(String type) {
        if (paymentServices.get(type) == null) throw new ResourceNotFountException("Payment method " + type + " not found.");
        return paymentServices.get(type);
    }
}
