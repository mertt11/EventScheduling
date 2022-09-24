package com.example.demo.controller;

import com.example.demo.entity.ModelUser;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    @Autowired
    private final UserService userService;

    public UserController (UserService userService){
        this.userService=userService;
    }

    @GetMapping("/get")
    public List<ModelUser> getAllUser(){
        return userService.getAllUsers();
    }
    /*@PostMapping("/create")
    public ModelUser createUser(@RequestBody ModelUser newuser){
        return userService.saveOneUser(newuser);
    }*/
    @GetMapping("/{id}")
    public ModelUser getUserById(@PathVariable Long id){
        return userService.getOneUser(id);
    }
    /*@PutMapping("/{id}")
    public ModelUser updateOneUser(@PathVariable Long id, @RequestBody ModelUser user){
        return userService.updateOneUser(id,user);
    }*/
    @DeleteMapping("/{id}")
    public void deleteOneUser(@PathVariable Long id){
        userService.deleteById(id);
    }

    /*@DeleteMapping("/del/{id}")
    public void deleteOneeUser(@PathVariable Long id){
        userService.deleteByyId(id);
    }*/

    public ArrayList<ModelUser> findUsrs(@PathVariable Long eventId){
        return userService.findUsersInSpecificEvent(eventId);
    }
}