package com.family.tree.notification.service.controller;

import com.family.tree.notification.service.dto.WhatsAppRequest;
import com.family.tree.notification.service.service.WhatsAppService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/whatsapp")
public class WhatsAppController {

    private final WhatsAppService whatsAppService;

    public WhatsAppController(WhatsAppService whatsAppService) {
        this.whatsAppService = whatsAppService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendWhatsApp(@RequestBody WhatsAppRequest request) {
        whatsAppService.sendWhatsAppMessage(request.getTo(), request.getMessage());
        return ResponseEntity.ok("WhatsApp message sent successfully");
    }
}
