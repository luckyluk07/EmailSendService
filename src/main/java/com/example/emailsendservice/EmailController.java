package com.example.emailsendservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/emails")
public class EmailController {

    private EmailService service;

    @Autowired
    public EmailController(EmailService emailService) {
        this.service = emailService;
    }

    @GetMapping
    public ResponseEntity<List<EmailDto>> findAll() {
        List<EmailDto> emails =  this.service.findAll();
        return ResponseEntity.ok(emails);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmailDto> findById(@PathVariable Long id) {
        EmailDto email = this.service.findById(id);
        if (email != null) {
            return ResponseEntity.ok(email);
        }
        return ResponseEntity.notFound().build();
    }
}
