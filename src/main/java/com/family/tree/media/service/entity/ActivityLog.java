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
@Table(name = "activity_log")
public class ActivityLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private PersonDetail user;

    private String action;

    @Lob
    private String description;

    private LocalDateTime activityTime;
}
