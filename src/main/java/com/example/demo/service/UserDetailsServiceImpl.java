package com.example.demo.service;

import com.example.demo.entity.ModelUser;
import com.example.demo.repository.ModelUserRepository;
import com.example.demo.security.JwtUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final ModelUserRepository userRepository;

    public UserDetailsServiceImpl(ModelUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ModelUser user = userRepository.findByEmail(username);
        return JwtUserDetails.create(user);
    }
}
