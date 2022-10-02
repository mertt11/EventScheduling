package com.example.demo.controller;

import com.example.demo.entity.Event;
//import com.example.demo.repository.ModelUserRepository;
import com.example.demo.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {
    @Autowired
    private EventService eventService;

    //ModelUserRepository userRepository;

    @GetMapping("/get")
    List<Event> getEvent(){
        return eventService.getAll();
    }

    @PostMapping("/post")
    Event createEvent(@RequestBody Event event){
        return eventService.saveOneEvent(event);
    }

    @PutMapping("/{eventId}/users/{userId}")
    Event participateUserToEvent(@PathVariable Long eventId, @PathVariable Long userId){
        return eventService.partUserToEvent(eventId,userId);
    }
    /*@DeleteMapping("/{eventId}/users/{userId}")
    Event deleteUserFromEvent(@PathVariable Long eventId, @PathVariable Long userId){
        return eventService.delUserFromEvent(eventId,userId);
    }*/
    @DeleteMapping("/{eventId}")
    public void removeEvent(@PathVariable Long eventId){
        eventService.removeById(eventId);
    }


    @DeleteMapping("/del/{eventId}/users/{userId}")
    Event dellUserFromEvent(@PathVariable Long eventId, @PathVariable Long userId){
        return eventService.dellUserFromEvent(eventId,userId);
    }

}