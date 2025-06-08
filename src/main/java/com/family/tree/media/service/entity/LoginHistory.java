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
@Table(name = "login_history")
public class LoginHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private PersonDetail user;

    private LocalDateTime loginTime;
    private String ipAddress;

    @Lob
    private String deviceInfo;
    private String status;
}