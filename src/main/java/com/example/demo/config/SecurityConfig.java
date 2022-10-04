package com.example.demo.config;

import com.example.demo.security.JwtAuthEntryPoint;
import com.example.demo.security.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final JwtAuthEntryPoint handler;

    public SecurityConfig(UserDetailsService userDetailsService, JwtAuthEntryPoint handler) {
        this.userDetailsService = userDetailsService;
        this.handler = handler;
    }
    @Bean
    public JwtAuthFilter jwtAuthFilter(){
        return new JwtAuthFilter();
    }
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .cors()
                .and()
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(handler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                //.antMatchers("/users/giveadmin/{id}","/{eventId}/send-email","/events/{eventId}/users/{userId}","/events/del/{eventId}/users/{userId}","/events/post").hasAnyAuthority("ADMIN")
                .antMatchers("/users/giveadmin/{id}","/{eventId}/send-email","/events/{eventId}/users/{userId}","/events/del/{eventId}/users/{userId}","/events/post").hasRole("ADMIN")
                .antMatchers("/events/get","/users/get").hasAnyRole("USER","ADMIN")
                .antMatchers("/auth/**").permitAll()
                .anyRequest().authenticated();
        httpSecurity.addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
