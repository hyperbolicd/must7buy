package com.cathy.shopping.dto.linepay;

import java.util.List;

public class RequestFromDTO {
    private int amount;
    private String currency;
    private String orderId;
    private List<PackageDTO> packages;
    private RedirectUrlsDTO redirectUrls;

    public RequestFromDTO() {
    }

    public RequestFromDTO(int amount, String currency, String orderId, List<PackageDTO> packages, RedirectUrlsDTO redirectUrls) {
        this.amount = amount;
        this.currency = currency;
        this.orderId = orderId;
        this.packages = packages;
        this.redirectUrls = redirectUrls;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<PackageDTO> getPackages() {
        return packages;
    }

    public void setPackages(List<PackageDTO> packages) {
        this.packages = packages;
    }

    public RedirectUrlsDTO getRedirectUrls() {
        return redirectUrls;
    }

    public void setRedirectUrls(RedirectUrlsDTO redirectUrls) {
        this.redirectUrls = redirectUrls;
    }

}
