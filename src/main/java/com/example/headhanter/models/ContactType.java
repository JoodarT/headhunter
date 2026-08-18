package com.example.headhanter.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    @Entity
    @Table(name = "contact_types")
    public class ContactType {
        @Id private Long id;

        @Column(name = "type_name", nullable = false, unique = true)
        private String typeName;



    }