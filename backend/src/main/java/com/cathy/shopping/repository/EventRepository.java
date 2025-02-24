package com.cathy.shopping.repository;

import com.cathy.shopping.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {
}