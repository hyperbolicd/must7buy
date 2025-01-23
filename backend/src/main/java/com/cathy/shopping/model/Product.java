package com.cathy.shopping.model;

import jakarta.persistence.*;

import java.util.List;

//@Entity
//@Table(name = "products")
public class Product {

    @ManyToMany
    @JoinTable(
        name = "products_events",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private List<Event> events;
}
