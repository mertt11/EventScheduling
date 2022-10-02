package com.example.demo;

import com.example.demo.entity.ModelUser;
import com.example.demo.entity.Role;
import com.example.demo.repository.ModelUserRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.UserService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private final RoleRepository roleRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(RoleRepository roleRepository, UserService userService, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        createRoles();
        createAdmin();
    }

    public void createAdmin(){
        if(userService.contain("admin","admin")) return;

        Role role=roleRepository.findByRoleName("ADMIN");
        ModelUser usr=new ModelUser(Long.valueOf(1),"admin","admin","noreply.anka1@gmail.com",passwordEncoder().encode("123"));
        usr.addUserRoles(role);
        userService.saveOneUser(usr);
    }

    public void createRoles(){
        Role userRole = roleRepository.findByRoleName("USER");
        if(userRole==null) roleRepository.save(new Role("USER"));

        Role adminRole=roleRepository.findByRoleName("ADMIN");
        if(adminRole==null) roleRepository.save(new Role("ADMIN"));
    }

    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
