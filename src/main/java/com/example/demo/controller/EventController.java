package com.example.demo.controller;

import com.example.demo.entity.Event;
import com.example.demo.entity.ModelUser;
import com.example.demo.repository.ModelUserRepository;
import com.example.demo.service.EventService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;
    private final ModelUserRepository modelUserRepository;


    public EventController(EventService eventService, ModelUserRepository modelUserRepository) {
        this.eventService = eventService;
        this.modelUserRepository = modelUserRepository;
    }

    @GetMapping("/get")
    List<Event> getEvent(){
        return eventService.getAll();
    }

    @PostMapping("/post")
    Event createEvent(@RequestBody Event event){
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        ModelUser admin=modelUserRepository.findByEmail(auth.getName());//get the current User that logged in.
        event.setCreatedBy(admin);
        return eventService.saveOneEvent(event);
    }

    @PutMapping("/{eventId}/users/{userId}")
    Event participateUserToEvent(@PathVariable Long eventId, @PathVariable Long userId){
        return eventService.partUserToEvent(eventId,userId);
    }

    @DeleteMapping("/{eventId}")
    public void removeEvent(@PathVariable Long eventId){
        eventService.removeById(eventId);
    }


    @DeleteMapping("/del/{eventId}/users/{userId}")
    Event dellUserFromEvent(@PathVariable Long eventId, @PathVariable Long userId){
        return eventService.dellUserFromEvent(eventId,userId);
    }

}