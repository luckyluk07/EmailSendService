package com.example.emailsendservice.Services;

import com.example.emailsendservice.Models.MailMessage;
import com.example.emailsendservice.Models.User;
import com.example.emailsendservice.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EmailSenderService {

    private JavaMailSender emailSender;
    private UserRepository userRepository;
    private Environment environment;

    @Autowired
    public EmailSenderService(JavaMailSender emailSender, UserRepository userRepository, Environment environment) {
        this.emailSender = emailSender;
        this.userRepository = userRepository;
        this.environment = environment;
    }

    public void sendSimpleMessage(String to, MailMessage messageToSend) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(environment.getProperty("spring.mail.username"));
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
        sendSimpleMessage(user.get().getEmail(), messageToSend);
        return true;
    }

    public void sendToAll(MailMessage messageToSend) {
        for (User user : userRepository.findAll()) {
            sendSimpleMessage(user.getEmail(), messageToSend);
        }
    }
}