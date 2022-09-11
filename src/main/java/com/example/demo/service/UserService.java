package com.example.demo.service;

import com.example.demo.entity.ModelUser;
import com.example.demo.repository.ModelUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    ModelUserRepository userRepository;
    public UserService(ModelUserRepository userRepository){
        this.userRepository=userRepository;
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

    /*public ModelUser updateOneUser(Long id, ModelUser user) {
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
    }*/

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public ModelUser getOneUserByUserName(String email) {
        return userRepository.findByEmail(email);
    }
}