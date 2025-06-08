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
@Table(name = "person_relation")
public class PersonRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
 

       private Long id;


    @ManyToOne
    @JoinColumn(name = "rel1")
    private PersonDetail rel1;

    @ManyToOne
    @JoinColumn(name = "rel2")
    private PersonDetail rel2;

    private String prop;
    private LocalDateTime dateCreated;
}
