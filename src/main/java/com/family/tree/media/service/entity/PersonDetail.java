package com.family.tree.media.service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "person_detail")
public class PersonDetail {
    @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
 
 
 
       private Long id;


    @Lob
    private String info;
    private LocalDateTime dateCreated;
    private String firstName;
    private String lastName;
    private String relationType;
    private String gender;
    private Boolean memberAlive;
    private LocalDate birthDate;
    private LocalDate marriageDate;
    private LocalDate deathDate;
    private Long linkThisMemberToAUser;

    @Lob
    private String photo;
    private String avatar;
    private String facebook;
    private String twitter;
    private String instagram;
    private String email;
    private String homeTel;
    private String website;
    private String birthPlace;
    private String deathPlace;
    private String profession;
    private String company;

    @Lob
    private String bioNotes;
}
