package com.example.demo.service;

import com.example.demo.entity.Event;
import com.example.demo.entity.ModelUser;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.ModelUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;

    private final ModelUserRepository userRepository;


    public EventService(EventRepository eventRepository, ModelUserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    public List<Event> getAll() {
        return eventRepository.findAll();
    }

    public Event saveOneEvent(Event event) {
        
        return eventRepository.save(event);
    }

    public void removeById(Long eventId) {
        eventRepository.deleteById(eventId);
    }

    public Event partUserToEvent(Long eventId, Long userId) {
        Event event = eventRepository.findById(eventId).get();
        ModelUser user = userRepository.findById(userId).get();
        event.getEnrolledUsers().add(user);
        return eventRepository.save(event);
    }

    public Event dellUserFromEvent(Long eventId, Long userId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new ResourceAccessException("Not found with id = " + userId));
        ModelUser usr = event.getEnrolledUsers().stream().filter(t -> t.getId() == userId).findFirst().orElse(null);
        if (usr != null) {
            event.getEnrolledUsers().remove(usr);
            usr.getEvents().remove(this);
        }
        return eventRepository.save(event);
    }

}