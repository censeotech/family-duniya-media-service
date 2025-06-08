package com.family.tree.notification.service.controller;


import com.family.tree.notification.service.dto.EmailRequest;
import com.family.tree.notification.service.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest request) {
        emailService.sendEmail(request.getTo(), request.getSubject(), request.getBody());
        return ResponseEntity.ok("Email sent successfully");
    }
}