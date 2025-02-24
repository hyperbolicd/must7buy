package com.cathy.shopping.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(min = 5, max = 20)
    @NotBlank(message = "Event name is mandatory")
    private String name;

    @Column(columnDefinition="TEXT")
    private String description;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Future(message = "結束日期必須在未來")
    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(nullable = false)
    private int type;

    @PrePersist
    public void setDefaultEvent() {
        this.createdDate = LocalDate.now();
    }

    public Event() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @Size(min = 5, max = 20) @NotBlank(message = "Event name is mandatory") String getName() {
        return name;
    }

    public void setName(@Size(min = 5, max = 20) @NotBlank(message = "Event name is mandatory") String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public @Future(message = "結束日期必須在未來") LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(@Future(message = "結束日期必須在未來") LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
