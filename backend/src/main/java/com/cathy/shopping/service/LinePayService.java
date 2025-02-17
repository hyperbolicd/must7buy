package com.cathy.shopping.service;

import com.cathy.shopping.dto.linepay.*;
import com.cathy.shopping.exception.ResourceNotFountException;
import com.cathy.shopping.model.Customer;
import com.cathy.shopping.model.Order;
import com.cathy.shopping.model.OrderProduct;
import com.cathy.shopping.model.Payment;
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
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;

@Service
public class LinePayService {

    @Value("${line.pay.channelId}")
    private String CHANNEL_ID;

    @Value("${line.pay.secretKey}")
    private String CHANNEL_SECRET;

    @Value("${line.pay.baseUrl}")
    private String BASE_URL;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private PaymentRepository paymentRepository;

    public void getInfo() {
        System.out.println("API URL: " + BASE_URL);
        System.out.println("Channel ID: " + CHANNEL_ID);
        System.out.println("Secret Key: " + CHANNEL_SECRET);
    }

    @Transactional
    public Optional<PaymentResponse> createPayment(Order order, RequestFromDTO requestDTO) {
        String apiPath = "/v3/payments/request";
        String nonce = getNonce();
        String jsonData = "";
        try {
            jsonData = mapper.writeValueAsString(requestDTO);
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

        return Optional.ofNullable(response);
    }

    public Optional<PaymentResponse> confirmPayment(String transactionId, ConfirmFormDTO requestDTO) {
        String apiPath = "/v3/payments/" + transactionId + "/confirm";
        String nonce = getNonce();
        String jsonData = "";
        try {
            jsonData = mapper.writeValueAsString(requestDTO);
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

        return Optional.ofNullable(response);
    }

    public String sentPost(String apiPath, String mapperData, String nonce, String signature) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("X-LINE-ChannelId", CHANNEL_ID);
        headers.add("X-LINE-Authorization-Nonce", nonce);
        headers.add("X-LINE-Authorization", signature);
        HttpEntity<String> httpEntity = new HttpEntity<>(mapperData, headers);
        return restTemplate.postForObject(BASE_URL + apiPath, httpEntity, String.class);
    }

    public String getNonce() {
        return UUID.randomUUID().toString();
    }

    public String getSignKey(String clientKey, String msg) {
        byte[] hmacBytes = HmacUtils.
                getInitializedMac(HmacAlgorithms.HMAC_SHA_256, clientKey.getBytes())
                .doFinal(msg.getBytes());

        return Base64.getEncoder().encodeToString(hmacBytes);
    }

    public RequestFromDTO getRequestForm(Order order, Customer.Cart cart) {
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
        redirectUrls.setConfirmUrl("http://localhost:3000/me/orders/" + order.getId() + "/payment/authorize");
        redirectUrls.setCancelUrl("http://localhost:3000/me/orders/" + order.getId() + "/payment/cancel");
        form.setRedirectUrls(redirectUrls);
        return form;
    }
}
