package com.cathy.shopping.model;

import com.cathy.shopping.config.CartConverter;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer extends User {

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = CartConverter.class)
    private Cart cart;

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

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Embeddable
    public static class Cart {
        @JsonProperty("cartItems")
        private List<CartItem> items = new ArrayList<>();
        private Integer totalPrice;

        public Cart() {
        }

        public Cart(List<CartItem> items, Integer totalPrice) {
            this.items = items;
            this.totalPrice = totalPrice;
        }

        public List<CartItem> getItems() {
            return items;
        }

        public void setItems(List<CartItem> items) {
            this.items = items;
        }

        public Integer getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(Integer totalPrice) {
            this.totalPrice = totalPrice;
        }
    }

    @Embeddable
    public static class CartItem {
        private int id;
        private String name;
        private String style;
        private Integer quantity;
        private BigDecimal price; // unit price
        private String imageUrl;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStyle() {
            return style;
        }

        public void setStyle(String style) {
            this.style = style;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }
}
