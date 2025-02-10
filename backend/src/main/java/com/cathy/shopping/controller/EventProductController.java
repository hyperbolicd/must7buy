package com.cathy.shopping.controller;

import com.cathy.shopping.model.EventProduct;
import com.cathy.shopping.service.EventProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/eventProducts")
public class EventProductController {

    @Autowired
    EventProductService eventProductService;

    @GetMapping
    public ResponseEntity<List<EventProduct>> getAllEventProducts() {
        List<EventProduct> eventProducts = eventProductService.getAllEventProducts();
        return ResponseEntity.ok(eventProducts);
    }

    @PostMapping
    public ResponseEntity<EventProduct> createEventProduct(@RequestBody EventProduct eventProduct) {
        EventProduct createdEventProduct = eventProductService.createEventProduct(eventProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEventProduct);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventProduct> getEventProductById(@PathVariable int id) {
        EventProduct eventProduct = eventProductService.getEventProductById(id);
        return ResponseEntity.ok(eventProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventProduct> updateEventProduct(@PathVariable int id, @RequestBody EventProduct eventProduct) {
        EventProduct updatedEventProduct = eventProductService.updateEventProduct(id, eventProduct);
        return ResponseEntity.ok(updatedEventProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEventProduct(@PathVariable int id) {
        boolean isDeleted = eventProductService.deleteEventProduct(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

}