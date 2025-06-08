package com.family.tree.notification.service.controller;

import com.family.tree.notification.service.dto.SmsRequest;
import com.family.tree.notification.service.service.SmsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sms")
public class SmsController {

    private final SmsService smsService;

    public SmsController(SmsService smsService) {
        this.smsService = smsService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendSms(@RequestBody SmsRequest request) {
        smsService.sendSms(request.getTo(), request.getMessage());
        return ResponseEntity.ok("SMS sent successfully");
    }
}
