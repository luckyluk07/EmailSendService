package com.example.emailsendservice;

import com.example.emailsendservice.Models.User;
import com.example.emailsendservice.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailSenderService{

    private JavaMailSender emailSender;
    private UserRepository userRepository;

    @Autowired
    public EmailSenderService(JavaMailSender emailSender, UserRepository userRepository) {
        this.emailSender = emailSender;
        this.userRepository = userRepository;
    }

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("lukasznojman65@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    public void sendToAll() {
        for (User user : userRepository.findAll()) {
            sendSimpleMessage(user.getEmail(), "Test zadania", "Teraz mozesz napisac na messengerze");
        }
    }
}