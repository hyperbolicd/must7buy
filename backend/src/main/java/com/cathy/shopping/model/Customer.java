package com.cathy.shopping.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;

import java.time.LocalDate;

@Entity
public class Customer extends User {

    @Column(name = "created_date")
    private LocalDate createdDate;

    private String cart;

    @PrePersist
    public void setDefaultRole() {
        setRole(Role.CUSTOMER);
        this.createdDate = LocalDate.now();
    }

    public Customer() {
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getCart() {
        return cart;
    }

    public void setCart(String cart) {
        this.cart = cart;
    }
}
