package com.family.tree.notification.service.dto;

import lombok.Data;

@Data
public class WhatsAppRequest {
    private String to;     // E.g. +1234567890
    private String message;
}
