package com.cathy.shopping.service;

import com.cathy.shopping.dto.linepay.*;
import com.cathy.shopping.exception.ResourceNotFountException;
import com.cathy.shopping.model.*;
import com.cathy.shopping.repository.PaymentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Column;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Positive;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;

@Service("LINE_PAY")
public class LinePayService implements PaymentService {

    @Value("${line.pay.channelId}")
    private String CHANNEL_ID;

    @Value("${line.pay.secretKey}")
    private String CHANNEL_SECRET;

    @Value("${line.pay.baseUrl}")
    private String BASE_URL;

    private static final String REDIRECT_BASE_URL = "http://localhost:3000/me/orders";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderProductService orderProductService;

    @Autowired
    private ProductService productService;

    public void getInfo() {
        System.out.println("API URL: " + BASE_URL);
        System.out.println("Channel ID: " + CHANNEL_ID);
        System.out.println("Secret Key: " + CHANNEL_SECRET);
    }

    @Transactional
    @Override
    public PaymentResponse createPayment(Order order, Customer.Cart cart) {
        String apiPath = "/v3/payments/request";
        String nonce = getNonce();
        String jsonData = "";
        try {
            jsonData = mapper.writeValueAsString(getRequestForm(order, cart));
        } catch (JsonProcessingException e) {
            System.err.println(e.getMessage());
        }
        String signature = getSignKey(CHANNEL_SECRET, CHANNEL_SECRET + apiPath + jsonData + nonce);

        String responseStr = sentPost(apiPath, jsonData, nonce, signature);

        PaymentResponse response = null;
        try {
            response = mapper.readValue(responseStr, PaymentResponse.class);
        } catch (JsonProcessingException e) {
            System.err.println(e.getMessage());
        }

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaymentMethod("Line pay");
        payment.setTransactionId(String.valueOf(response.getInfo().getTransactionId()));
        payment.setAmount(order.getTotalPrice());
        paymentRepository.save(payment);

        return response;
    }

    @Override
    public PaymentResponse confirmPayment(String transactionId, Order order) {
        String apiPath = "/v3/payments/" + transactionId + "/confirm";
        String nonce = getNonce();
        String jsonData = "";
        try {
            jsonData = mapper.writeValueAsString(getConfirmFormDTO(order));
        } catch (JsonProcessingException e) {
            System.err.println(e.getMessage());
        }
        String signature = getSignKey(CHANNEL_SECRET, CHANNEL_SECRET + apiPath + jsonData + nonce);

        String responseStr = sentPost(apiPath, jsonData, nonce, signature);

        PaymentResponse response = null;
        try {
            response = mapper.readValue(responseStr, PaymentResponse.class);

            if ("0000".equals(response.getReturnCode())) { // Success
                order.setStatus("SUCCESS");
                order.setPaidAt(LocalDate.now());
                orderService.updateOrder(order.getId(), order);

                Payment existedPayment = paymentRepository.findPaymentByTransactionId(transactionId)
                        .orElseThrow(() -> new ResourceNotFountException("Transaction id: " + transactionId + " not found."));
                existedPayment.setStatus("SUCCESS");
                paymentRepository.save(existedPayment);
            } else {
                orderService.cancelOrder(order.getId());

                Payment existedPayment = paymentRepository.findPaymentByTransactionId(transactionId)
                        .orElseThrow(() -> new ResourceNotFountException("Transaction id: " + transactionId + " not found."));
                existedPayment.setStatus("EXPIRED");
                paymentRepository.save(existedPayment);
            }

        } catch (JsonProcessingException e) {
            System.err.println(e.getMessage());
        }

        return response;
    }

    private String sentPost(String apiPath, String mapperData, String nonce, String signature) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("X-LINE-ChannelId", CHANNEL_ID);
        headers.add("X-LINE-Authorization-Nonce", nonce);
        headers.add("X-LINE-Authorization", signature);
        HttpEntity<String> httpEntity = new HttpEntity<>(mapperData, headers);
        return restTemplate.postForObject(BASE_URL + apiPath, httpEntity, String.class);
    }

    private String getNonce() {
        return UUID.randomUUID().toString();
    }

    private String getSignKey(String clientKey, String msg) {
        byte[] hmacBytes = HmacUtils.
                getInitializedMac(HmacAlgorithms.HMAC_SHA_256, clientKey.getBytes())
                .doFinal(msg.getBytes());

        return Base64.getEncoder().encodeToString(hmacBytes);
    }

    private RequestFromDTO getRequestForm(Order order, Customer.Cart cart) {
        RequestFromDTO form = new RequestFromDTO();
        form.setAmount(order.getTotalPrice().intValue());
        form.setCurrency("TWD");
        form.setOrderId(String.valueOf(order.getId()));
        List<ProductDTO> products = new ArrayList<>();
        cart.getItems().forEach( cartItem -> {
            products.add(new ProductDTO(String.valueOf(cartItem.getId()), cartItem.getName(), cartItem.getImageUrl(), cartItem.getQuantity(), cartItem.getPrice().intValue()));
        });
        PackageDTO packageDTO = new PackageDTO("1", order.getTotalPrice().intValue(), products);
        form.setPackages(List.of(packageDTO));
        RedirectUrlsDTO redirectUrls = new RedirectUrlsDTO();
        // ex. http://localhost:3000/me/orders/payment/authorize?transactionId=2025022002273935810&orderId=7
        redirectUrls.setConfirmUrl(REDIRECT_BASE_URL + "/payment/authorize");
        redirectUrls.setCancelUrl(REDIRECT_BASE_URL + "/payment/cancel");
        form.setRedirectUrls(redirectUrls);
        return form;
    }

    private ConfirmFormDTO getConfirmFormDTO(Order order) {
        ConfirmFormDTO form = new ConfirmFormDTO();
        form.setAmount(order.getTotalPrice().intValue());
        form.setCurrency("TWD");
        return form;
    }

    @Override
    public PaymentResponse getPaymentStatus(String transactionId) {
        return null;
    }

    @Override
    public boolean cancelPayment(String transactionId) {
        return false;
    }

    @Override
    public PaymentResponse refundPayment(String transactionId) {
        return null;
    }

    @Override
    public List<Payment> getPaymentHistory(long userId) {
        return List.of();
    }
}
