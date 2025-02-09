package com.cathy.shopping.service;

import com.cathy.shopping.exception.ResourceNotFountException;
import com.cathy.shopping.model.EventProduct;
import com.cathy.shopping.repository.EventProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventProductService {

    @Autowired
    private EventProductRepository eventProductRepository;

    // Add service methods here
    
    public List<EventProduct> getAllEventProducts() {
        return eventProductRepository.findAll();
    }

    public EventProduct createEventProduct(EventProduct eventProduct) {
        return eventProductRepository.save(eventProduct);
    }

    public EventProduct getEventProductById(Integer id) {
        return eventProductRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFountException("EventProduct not exist with id: " + id));
    }
    
    public EventProduct updateEventProduct(Integer id, EventProduct updatedEventProduct) {
        EventProduct existingEventProduct = getEventProductById(id);
        return eventProductRepository.save(existingEventProduct);
    }

    public boolean deleteEventProduct(Integer id) {
        EventProduct eventProduct = getEventProductById(id);
        eventProductRepository.delete(eventProduct);
        return true;
    }
}
