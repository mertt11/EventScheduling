package com.example.demo.controller;

import com.example.demo.entity.ModelUser;
import com.example.demo.entity.Role;
import com.example.demo.repository.RoleRepository;

import com.example.demo.security.JwtTokenGenerator;
import com.example.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenGenerator jwtTokenGenerator;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final RoleRepository roleRepository;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenGenerator jwtTokenGenerator, PasswordEncoder passwordEncoder, UserService userService, RoleRepository roleRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenGenerator = jwtTokenGenerator;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/signin")
    public ResponseEntity<ModelUser> register(@RequestBody ModelUser signinRequest){
        signinRequest.setId(signinRequest.getId());
        signinRequest.setName(signinRequest.getName());
        signinRequest.setLastName(signinRequest.getLastName());
        signinRequest.setEmail(signinRequest.getEmail());
        signinRequest.setPassword(passwordEncoder.encode(signinRequest.getPassword()));

        Role role=roleRepository.findByRoleName("ROLE_USER");
        signinRequest.getUserRoles().add(role);
        userService.saveOneUser(signinRequest);
        return ResponseEntity.ok(signinRequest);
    }

    @PostMapping("/login")
    public String login(@RequestBody ModelUser loginRequest){
        UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword());
        Authentication auth=authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtToken = jwtTokenGenerator.generateJwtToken(auth);
        return  "Bearer "+jwtToken;
    }



}
