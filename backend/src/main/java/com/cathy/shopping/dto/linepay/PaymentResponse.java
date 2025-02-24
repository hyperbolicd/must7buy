package com.cathy.shopping.dto.linepay;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentResponse {
    private String returnCode;
    private String returnMessage;
    private PaymentInfo info;

    public PaymentResponse() {
    }

    public PaymentResponse(String returnCode, String returnMessage, PaymentInfo info) {
        this.returnCode = returnCode;
        this.returnMessage = returnMessage;
        this.info = info;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    public PaymentInfo getInfo() {
        return info;
    }

    public void setInfo(PaymentInfo info) {
        this.info = info;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PaymentInfo {
        private PaymentUrl paymentUrl;
        private long transactionId;
        private String paymentAccessToken;

        public PaymentInfo() {
        }

        public PaymentInfo(PaymentUrl paymentUrl, long transactionId, String paymentAccessToken) {
            this.paymentUrl = paymentUrl;
            this.transactionId = transactionId;
            this.paymentAccessToken = paymentAccessToken;
        }

        public PaymentUrl getPaymentUrl() {
            return paymentUrl;
        }

        public void setPaymentUrl(PaymentUrl paymentUrl) {
            this.paymentUrl = paymentUrl;
        }

        public long getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(long transactionId) {
            this.transactionId = transactionId;
        }

        public String getPaymentAccessToken() {
            return paymentAccessToken;
        }

        public void setPaymentAccessToken(String paymentAccessToken) {
            this.paymentAccessToken = paymentAccessToken;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PaymentUrl {
        private String web;
        private String app;

        public PaymentUrl() {
        }

        public PaymentUrl(String web, String app) {
            this.web = web;
            this.app = app;
        }

        public String getWeb() {
            return web;
        }

        public void setWeb(String web) {
            this.web = web;
        }

        public String getApp() {
            return app;
        }

        public void setApp(String app) {
            this.app = app;
        }
    }
}

