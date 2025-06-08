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
@Table(name = "family_person_map")
public class FamilyPersonMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;


    @ManyToOne
    @JoinColumn(name = "family_id")
    private FamilyInfo family;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private PersonDetail person;

    private LocalDateTime dateAdded;
}