package com.example.headhanter.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "contact_types")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String type;
}