package com.example.demo.service;

import com.example.demo.entity.ModelUser;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.ModelUserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final ModelUserRepository userRepository;
    private final EventRepository eventRepository;
    public UserService(ModelUserRepository userRepository,EventRepository eventRepository){
        this.userRepository=userRepository;
        this.eventRepository=eventRepository;
    }

    public List<ModelUser> getAllUsers() {
        return userRepository.findAll();
    }

    public ModelUser saveOneUser(ModelUser newUser) {
        return userRepository.save(newUser);
    }

    public ModelUser getOneUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    public boolean contain(String name,String lastName){
        return userRepository.existsByNameAndLastNameIgnoreCase(name,lastName);
    }
    public ModelUser updateOneUser(Long id, ModelUser user) {
        Optional<ModelUser> usr=userRepository.findById(id);
        if(usr.isPresent()){
            ModelUser foundUser=usr.get();
            foundUser.setEmail(user.getEmail());
            foundUser.setPassword(user.getPassword());
            userRepository.save(foundUser);
            return foundUser;
        }else {
            return null;
        }
    }
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public ModelUser getOneUserByUserName(String email) {
        return userRepository.findByEmail(email);
    }

    public ArrayList<ModelUser> findUsersInSpecificEvent(Long eventId){
        return new ArrayList<>(eventRepository.findById(Long.valueOf(eventId)).get().getEnrolledUsers());
    }

}