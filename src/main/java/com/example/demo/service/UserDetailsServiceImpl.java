package com.example.demo.service;

import com.example.demo.entity.ModelUser;
import com.example.demo.entity.Role;
import com.example.demo.repository.ModelUserRepository;
import com.example.demo.security.JwtUserDetails;
import org.hibernate.Hibernate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final ModelUserRepository userRepository;

    public UserDetailsServiceImpl(ModelUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    //@Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ModelUser user = userRepository.findByEmail(username);
        //user.getUserRoles().size();
        /*Set<Role> authorities = user.getUserRoles();
        Hibernate.initialize(authorities);*/
        return JwtUserDetails.create(user);
    }
}
