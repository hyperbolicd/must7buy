package com.cathy.shopping.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

//@Entity
//@Table(name = "events")
public class Event {

    private LocalDateTime startDate;

    private LocalDateTime endDate;
}
