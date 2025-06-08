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
@Table(name = "media")
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private PersonDetail person;

    private String mediaType;
    private String fileName;
    private String fileType;
    private Long fileSize;

    @Lob
    private String fileUrl;

    @Lob
    private String fileThumbnailUrl;

    private LocalDateTime uploadedAt;

    @Lob
    private String description;
}