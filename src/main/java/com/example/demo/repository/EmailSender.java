package com.example.demo.repository;

import com.example.demo.entity.Email;

public interface EmailSender {
    String sendSimpleMail(Long eventId, Email details);
}
