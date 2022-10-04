package com.example.demo.service;

import com.example.demo.controller.UserController;
import com.example.demo.entity.Email;
import com.example.demo.entity.ModelUser;
import com.example.demo.repository.EmailSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class EmailSenderServiceImpl implements EmailSender {


    private final JavaMailSender mailSender;
    private final UserController userController;
    @Value("${spring.mail.username}")
    private String sender;


    public EmailSenderServiceImpl(JavaMailSender mailSender, UserController userController) {
        this.userController = userController;
        this.mailSender=mailSender;
    }

    @Override
    public String sendSimpleMail(Long eventId, Email details) {
        ArrayList<ModelUser> usrs = userController.findUsrs(eventId);

        try {

            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();

            mailMessage.setFrom(sender);
            mailMessage.setTo(usrs.stream().map(a -> a.getEmail()).toArray(String[]::new));
            mailMessage.setText(usrs.get(0).getEvents().toString());
            mailMessage.setSubject("Event info");
            mailSender.send(mailMessage);
            return "Mail Sent Successfully...";
        } catch (Exception e) {
            return "Error while Sending Mail";
        }
    }
}

