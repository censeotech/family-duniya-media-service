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
@Table(name = "relation_desc")
public class RelationDesc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

 
 
   
       private Long id;

    private String info;
}