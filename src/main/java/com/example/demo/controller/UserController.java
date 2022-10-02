package com.example.demo.controller;

import com.example.demo.entity.ModelUser;
import com.example.demo.entity.Role;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;
    private RoleRepository roleRepository;

    public UserController (UserService userService, RoleRepository roleRepository){
        this.userService=userService;
        this.roleRepository = roleRepository;
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

    @PostMapping("/giveadmin/{id}")
    public void beAdmin(@PathVariable Long id){
        ModelUser user=userService.getOneUser(id);
        Role role=roleRepository.findByRoleName("ADMIN");
        user.addUserRoles(role);
        userService.saveOneUser(user);
    }
}