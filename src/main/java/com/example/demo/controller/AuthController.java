package com.example.demo.controller;

import com.example.demo.entity.ModelUser;
import com.example.demo.entity.Role;
import com.example.demo.repository.RoleRepository;
import com.example.demo.request.UserRequest;
import com.example.demo.security.JwtTokenGenerator;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import javax.transaction.Transactional;
import java.util.Optional;

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

    @PostMapping("/login")
    public String login(@RequestBody UserRequest loginRequest){
        UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword());
        Authentication auth=authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtToken = jwtTokenGenerator.generateJwtToken(auth);
        return  "Bearer "+jwtToken;
    }
    /*@PostMapping("/signin")
    public ResponseEntity<String> register(@RequestBody UserRequest signinRequest){
        if(userService.getOneUserByUserName(signinRequest.getEmail()) != null)
            return new ResponseEntity<>("Username already in use.", HttpStatus.BAD_REQUEST);
        ModelUser user=new ModelUser();
        user.setName(signinRequest.getName());
        user.setLastName(signinRequest.getLastName());
        user.setEmail(signinRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signinRequest.getPassword()));

        Role role=roleRepository.findByRoleName("USER");
        user.addUserRoles(role);
        userService.saveOneUser(user);
        return new ResponseEntity<>("User successfully registered ",HttpStatus.CREATED);
    }*/
    @PostMapping("/signin")
    public ResponseEntity<ModelUser> register(@RequestBody UserRequest signinRequest){
        ModelUser user=new ModelUser();
        user.setName(signinRequest.getName());
        user.setLastName(signinRequest.getLastName());
        user.setEmail(signinRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signinRequest.getPassword()));

        Role role=roleRepository.findByRoleName("USER");
        user.addUserRoles(role);
        userService.saveOneUser(user);
        return ResponseEntity.ok(user);
    }

}
