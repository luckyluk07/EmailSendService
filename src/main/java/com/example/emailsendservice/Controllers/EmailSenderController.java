package com.example.emailsendservice.Controllers;

import com.example.emailsendservice.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EmailSenderController {

    private EmailSenderService emailSenderService;

    @Autowired
    public EmailSenderController(EmailSenderService service) {
        this.emailSenderService = service;
    }

    @GetMapping("/sendmail")
    public String sendEmail() {
        this.emailSenderService.sendSimpleMessage("L.Nojman11@wp.pl","Test", "Teest");
        return "Email sent";
    }

    @GetMapping("/sendToAll")
    public String sendEmailToAll() {
        this.emailSenderService.sendToAll();
        return "Email send";
    }
}
