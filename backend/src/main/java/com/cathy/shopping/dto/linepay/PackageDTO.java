package com.cathy.shopping.dto.linepay;

import java.util.List;

public class PackageDTO {
    private String id;
    private int amount;
    private List<ProductDTO> products;

    public PackageDTO() {
    }

    public PackageDTO(String id, int amount, List<ProductDTO> products) {
        this.id = id;
        this.amount = amount;
        this.products = products;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }

}
