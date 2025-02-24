package com.cathy.shopping.dto.linepay;

public class RedirectUrlsDTO {
    private String confirmUrl;
    private String cancelUrl;

    public RedirectUrlsDTO() {
    }

    public RedirectUrlsDTO(String confirmUrl, String cancelUrl) {
        this.confirmUrl = confirmUrl;
        this.cancelUrl = cancelUrl;
    }

    public String getConfirmUrl() {
        return confirmUrl;
    }

    public void setConfirmUrl(String confirmUrl) {
        this.confirmUrl = confirmUrl;
    }

    public String getCancelUrl() {
        return cancelUrl;
    }

    public void setCancelUrl(String cancelUrl) {
        this.cancelUrl = cancelUrl;
    }

}
