package com.example.demo.controller;

import com.example.demo.entity.Email;
import com.example.demo.service.EmailSenderServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmailController {


    private final EmailSenderServiceImpl emailSenderService;

    public EmailController(EmailSenderServiceImpl emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    @PostMapping("/{eventId}/send-email")
    public void sendEmail(@PathVariable Long eventId,Email details){
        this.emailSenderService.sendSimpleMail(eventId,details);
    }

}
