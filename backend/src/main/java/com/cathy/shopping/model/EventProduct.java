package com.cathy.shopping.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;

@Entity
@Table(name = "event_product")
public class EventProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @DecimalMax("1.00")
    @DecimalMin("0.10")
    private BigDecimal discount;

    public EventProduct() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public @DecimalMax("1.00") @DecimalMin("0.10") BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(@DecimalMax("1.00") @DecimalMin("0.10") BigDecimal discount) {
        this.discount = discount;
    }
}
