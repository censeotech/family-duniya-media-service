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
@Table(name = "search_history")
public class SearchHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
 

       private Long id;


    @ManyToOne
    @JoinColumn(name = "person_id")
    private PersonDetail person;

    @Lob
    private String searchTerm;

    @Lob
    private byte[] searchFilters;

    private LocalDateTime searchedAt;
    private String ipAddress;

    @Lob
    private String deviceInfo;
}
