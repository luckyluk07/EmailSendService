package com.example.emailsendservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emails")
public class EmailController {

    private EmailService emailService;
    private UserService userService;

    @Autowired
    public EmailController(EmailService emailService, UserService userService) {
        this.emailService = emailService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<EmailDto>> findAll() {
        List<EmailDto> emails =  this.emailService.findAll();
        return ResponseEntity.ok(emails);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmailDto> findById(@PathVariable Long id) {
        EmailDto email = this.emailService.findById(id);
        if (email != null) {
            return ResponseEntity.ok(email);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateEmail(@PathVariable Long id, @RequestBody EmailDto dto) {
        User user = this.userService.findById(id);
        if (user != null) {
            this.emailService.updateEmail(id, dto);
            return ResponseEntity.accepted().build();
        }
        return ResponseEntity.notFound().build();
    }

}
