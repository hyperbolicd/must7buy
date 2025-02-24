package com.cathy.shopping.repository;

import com.cathy.shopping.model.EventProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventProductRepository extends JpaRepository<EventProduct, Integer> {
}