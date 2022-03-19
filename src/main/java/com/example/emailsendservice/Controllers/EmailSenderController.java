package com.example.emailsendservice.Controllers;

import com.example.emailsendservice.Services.EmailSenderService;
import com.example.emailsendservice.Models.MailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class EmailSenderController {

    private EmailSenderService emailSenderService;

    @Autowired
    public EmailSenderController(EmailSenderService service) {
        this.emailSenderService = service;
    }

//    @GetMapping("/sendmail/{mail}")
//    public String sendEmail(@PathVariable String mail, @RequestBody MailMessage message) {
//        this.emailSenderService.sendSimpleMessage(mail,message);
//        return "Email sent";
//    }

    @GetMapping("/sendmail/{id}")
    public String sendEmail(@PathVariable Long id, @RequestBody MailMessage message) {
        this.emailSenderService.sendSimpleMessage(id,message);
        return "Email sent";
    }

    @GetMapping("/sendToAll")
    public String sendEmailToAll(@RequestBody MailMessage message) {
        this.emailSenderService.sendToAll(message);
        return "Email sent to all";
    }
}
