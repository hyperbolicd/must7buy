package com.cathy.shopping.service;

import com.cathy.shopping.exception.ResourceNotFountException;
import com.cathy.shopping.model.Event;
import com.cathy.shopping.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    // Add service methods here
    
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event getEventById(Integer id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFountException("Event not exist with id: " + id));
    }
    
    public Event updateEvent(Integer id, Event updatedEvent) {
        Event existingEvent = getEventById(id);
        return eventRepository.save(existingEvent);
    }

    public boolean deleteEvent(Integer id) {
        Event event = getEventById(id);
        eventRepository.delete(event);
        return true;
    }
}
