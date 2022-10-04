package com.example.demo.security;

import com.example.demo.entity.ModelUser;
import com.example.demo.entity.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class JwtUserDetails implements UserDetails {

    public Long id;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;


    public JwtUserDetails(Long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }
    public static JwtUserDetails create(ModelUser user){
        return new JwtUserDetails(user.getId(),user.getEmail(),user.getPassword(),getAuthorities(user));
    }

    private static Collection<GrantedAuthority> getAuthorities(ModelUser user){
        Set<Role> userRoles = user.getUserRoles();
        for(Role r:userRoles){
            System.out.println("-------------user roles "+r.getRoleName()+" user roles size: "+userRoles.size());
        }
        Collection<GrantedAuthority> authorities = new ArrayList<>(userRoles.size());
        System.out.println("*************authorities size "+authorities.size());
        for(Role r : userRoles){
            authorities.add(new SimpleGrantedAuthority(r.getRoleName().toUpperCase()));
            System.out.println("ttttttttt: "+r.getRoleName());
        }
        for(GrantedAuthority a:authorities){
            System.out.println("aaaaaaaa: "+a.getAuthority());
        }
        return authorities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
