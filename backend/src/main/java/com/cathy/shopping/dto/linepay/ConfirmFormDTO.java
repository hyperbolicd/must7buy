package com.cathy.shopping.dto.linepay;

public class ConfirmFormDTO {
    private int amount;
    private String currency;

    public ConfirmFormDTO() {
    }

    public ConfirmFormDTO(int amount, String currency) {
        this.amount = amount;
        this.currency = currency;
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
}
