package com.family.tree.media.service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "otp_verification")
public class OtpVerification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
 

       private Long id;


    @ManyToOne
    @JoinColumn(name = "person_id")
    private PersonDetail person;

    private String otpCode;
    private String purpose;
    private String contactType;
    private String contactValue;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private LocalDateTime verifiedAt;
    private String status;
}