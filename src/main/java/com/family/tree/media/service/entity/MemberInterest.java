package com.family.tree.media.service.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member_interest")
public class MemberInterest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;


    private String interest;

    @ManyToOne
    @JoinColumn(name = "personid")
    private PersonDetail person;
}