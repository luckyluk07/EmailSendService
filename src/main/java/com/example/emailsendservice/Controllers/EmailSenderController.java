package com.example.emailsendservice.Controllers;

import com.example.emailsendservice.Services.EmailSenderService;
import com.example.emailsendservice.Models.MailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class EmailSenderController {

    private EmailSenderService emailSenderService;

    @Autowired
    public EmailSenderController(EmailSenderService service) {
        this.emailSenderService = service;
    }

    @GetMapping("/sendmail/{id}")
    public ResponseEntity<Void> sendEmail(@PathVariable Long id, @RequestBody MailMessage message) {
        if(!this.emailSenderService.sendSimpleMessage(id,message)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/sendToAll")
    public ResponseEntity<Void> sendEmailToAll(@RequestBody MailMessage message) {
        this.emailSenderService.sendToAll(message);
        return ResponseEntity.ok().build();
    }
}
