package com.cathy.shopping.service;

import com.cathy.shopping.dto.linepay.ConfirmFormDTO;
import com.cathy.shopping.dto.linepay.RequestFromDTO;
import com.cathy.shopping.dto.linepay.PaymentResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

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

    public void getInfo() {
        System.out.println("API URL: " + BASE_URL);
        System.out.println("Channel ID: " + CHANNEL_ID);
        System.out.println("Secret Key: " + CHANNEL_SECRET);
    }

    public Optional<PaymentResponse> createPayment(RequestFromDTO requestDTO) {
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

}
