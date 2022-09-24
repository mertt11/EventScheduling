package com.example.demo.controller;

import com.example.demo.entity.ModelUser;
import com.example.demo.request.UserRequest;
import com.example.demo.security.JwtTokenGenerator;
import com.example.demo.service.UserService;
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

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenGenerator jwtTokenGenerator;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenGenerator jwtTokenGenerator, PasswordEncoder passwordEncoder, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenGenerator = jwtTokenGenerator;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @PostMapping("/login")
    public String login(@RequestBody UserRequest loginRequest){
        UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword());
        Authentication auth=authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtToken = jwtTokenGenerator.generateJwtToken(auth);
        return  "Bearer "+jwtToken;
    }
    @PostMapping("/signin")
    public ResponseEntity<String> register(@RequestBody UserRequest signinRequest){
        if(userService.getOneUserByUserName(signinRequest.getEmail()) != null)
            return new ResponseEntity<>("Username already in use.", HttpStatus.BAD_REQUEST);
        ModelUser user=new ModelUser();
        user.setName(signinRequest.getName());
        user.setLastName(signinRequest.getLastName());
        user.setEmail(signinRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signinRequest.getPassword()));
        userService.saveOneUser(user);
        return new ResponseEntity<>("User successfully registered ",HttpStatus.CREATED);
    }
}
