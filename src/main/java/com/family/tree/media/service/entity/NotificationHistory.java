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
@Table(name = "notification_history")
public class NotificationHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
 

       private Long id;


    @ManyToOne
    @JoinColumn(name = "person_id")
    private PersonDetail person;

    private String subject;
    private String message;

    @Lob
    private String content;

    private String notificationType;
    private LocalDateTime sentAt;
    private String status;
}