package com.example.emailsendservice;

import com.example.emailsendservice.Models.MailMessage;
import com.example.emailsendservice.Models.User;
import com.example.emailsendservice.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EmailSenderService{

    private JavaMailSender emailSender;
    private UserRepository userRepository;

    @Autowired
    public EmailSenderService(JavaMailSender emailSender, UserRepository userRepository) {
        this.emailSender = emailSender;
        this.userRepository = userRepository;
    }

    public void sendSimpleMessage(String to, MailMessage messageToSend) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("lukasznojman65@gmail.com");
        message.setTo(to);
        message.setSubject(messageToSend.getSubject());
        message.setText(messageToSend.getMainContent());
        emailSender.send(message);
    }

    public boolean sendSimpleMessage(Long id, MailMessage messageToSend) {
        Optional<User> user = this.userRepository.findById(id);
        if (user.isEmpty()) {
            return false;
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("lukasznojman65@gmail.com");
        message.setTo(user.get().getEmail());
        message.setSubject(messageToSend.getSubject());
        message.setText(messageToSend.getMainContent());
        emailSender.send(message);
        return true;
    }

    public void sendToAll(MailMessage messageToSend) {
        for (User user : userRepository.findAll()) {
            sendSimpleMessage(user.getEmail(), messageToSend);
        }
    }
}